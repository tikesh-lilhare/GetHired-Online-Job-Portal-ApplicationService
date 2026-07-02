package com.example.ApplicationService.Service;

import com.example.ApplicationService.AI.ResumeParserService;
import com.example.ApplicationService.AI.SkillExtractionService;
import com.example.ApplicationService.AI.SkillMatchingService;
import com.example.ApplicationService.DTO.*;
import com.example.ApplicationService.Entity.ApplicationStatus;
import com.example.ApplicationService.Entity.JobApplication;
import com.example.ApplicationService.Exception.ApplicationNotFoundException;
import com.example.ApplicationService.Feign.JobClient;
import com.example.ApplicationService.Feign.JobDTO;
import com.example.ApplicationService.Feign.UserClient;
import com.example.ApplicationService.Feign.UserDTO;
import com.example.ApplicationService.Repository.ApplicationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepo repo;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final FileStorageService fileStorageService;
    private final ResumeParserService resumeParserService;
    private final SkillMatchingService skillMatchingService;
    private final SkillExtractionService skillExtractorService;

    @Override
    public ApplicationRes applyJob(ApplicationReq request) {
        UserDTO user =
                userClient.getUser(
                        request.getUserId());

        JobDTO job =
                jobClient.getJob(
                        request.getJobId());

        JobApplication application =
                JobApplication.builder()
                        .userId(
                                user.getId())
                        .jobId(
                                job.getId())
                        .resumeUrl(
                                request.getResumeUrl())
                        .status(
                                ApplicationStatus.APPLIED)
                        .build();

        JobApplication saved =
                repo.save(application);

        return mapToResponse(saved);

    }

    @Override
    public List<ApplicationRes>
    getAllApplications() {

        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ApplicationRes
    getApplicationById(Long id) {

        JobApplication application =
                repo.findById(id)
                        .orElseThrow(
                                () ->
                                        new ApplicationNotFoundException(
                                                "Application not found"));

        return mapToResponse(
                application);
    }

    private ApplicationRes mapToResponse(JobApplication application) {

        return new ApplicationRes(
                application.getId(),
                application.getUserId(),
                application.getJobId(),
                application.getResumeUrl(),
                application.getStatus().name()
        );
    }

    @Override
    public ApplicationRes applyJob(
            Long userId,
            Long jobId,
            MultipartFile resume)
            throws Exception {

        UserDTO user =
                userClient.getUser(
                        userId
                );

        JobDTO job =
                jobClient.getJob(
                        jobId
                );

        String filePath =
                fileStorageService
                        .saveFile(
                                resume
                        );

        JobApplication application =
                JobApplication.builder()
                        .userId(
                                user.getId()
                        )
                        .jobId(
                                job.getId()
                        )
                        .resumeUrl(
                                filePath
                        )
                        .status(
                                ApplicationStatus.APPLIED
                        )
                        .build();

        JobApplication saved =
                repo.save(application);

        return mapToResponse(saved);
    }

    @Override
    public List<ApplicationRes> getApplicationsByJob(
            Long jobId) {

        return repo.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }
    @Override
    public List<ApplicationRes> getApplicationsByUser(
            Long userId) {

        return repo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public ApplicationRes updateStatus(
            Long applicationId,
            ApplicationStatusUpdateReq request) {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Application not found"
                                ));

        application.setStatus(
                request.getStatus()
        );

        JobApplication saved =
                repo.save(application);

        return mapToResponse(saved);
    }


    @Override
    public ResumeSkillResponse
    extractSkillsFromResume(
            Long applicationId)
            throws Exception {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Application not found"
                                        )
                        );

        String resumeText =
                resumeParserService
                        .extractText(
                                application
                                        .getResumeUrl()
                        );

        List<String> skills =
                skillExtractorService
                        .extractSkills(
                                resumeText
                        );

        return new ResumeSkillResponse(
                applicationId,
                skills
        );
    }
    @Override
    public MatchScoreResponse
    calculateMatchScore(
            Long applicationId)
            throws Exception {

        JobApplication application =
                repo.findById(applicationId)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Application not found"
                                        )
                        );

        String resumeText =
                resumeParserService
                        .extractText(
                                application
                                        .getResumeUrl()
                        );

        List<String> resumeSkills =

                skillExtractorService
                        .extractSkills(
                                resumeText
                        );

        JobDTO job =
                jobClient.getJob(
                        application.getJobId()
                );

        List<String> jobSkills =

                Arrays.stream(
                                job.getSkills()
                                        .split(",")
                        )
                        .map(String::trim)
                        .toList();

        double score =

                skillMatchingService
                        .calculateMatchScore(

                                resumeSkills,

                                jobSkills
                        );

        return new MatchScoreResponse(

                application.getId(),

                job.getId(),

                resumeSkills,

                jobSkills,

                score
        );
    }
    public List<RecommendedJobResponse>
    recommendJobs(
            Long applicationId)
            throws Exception {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Application not found"
                                        )
                        );

        String resumeText =
                resumeParserService
                        .extractText(
                                application
                                        .getResumeUrl()
                        );

        List<String> resumeSkills =
                skillExtractorService
                        .extractSkills(
                                resumeText
                        );

        List<JobDTO> jobs =
                jobClient.getAllJob();

        List<RecommendedJobResponse>
                recommendations =
                new ArrayList<>();

        for(JobDTO job : jobs) {

            if(job.getSkills() == null ||
                    job.getSkills().isBlank()) {

                continue;
            }

            List<String> jobSkills =

                    Arrays.stream(
                                    job.getSkills()
                                            .split(",")
                            )
                            .map(String::trim)
                            .toList();

            double score =

                    skillMatchingService
                            .calculateMatchScore(

                                    resumeSkills,

                                    jobSkills
                            );

            recommendations.add(

                    new RecommendedJobResponse(

                            job.getId(),

                            job.getTitle(),

                            job.getCompanyName(),

                            score
                    )
            );
        }

        recommendations.sort(

                Comparator.comparing(
                        RecommendedJobResponse::
                                getMatchScore
                ).reversed()
        );

        return recommendations;
    }
    @Override
    public CandidateDetailsResponse
    getCandidateDetails(Long applicationId)
            throws Exception {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Application not found"
                                )
                        );

        UserDTO user =
                userClient.getUser(
                        application.getUserId()
                );

        Double matchScore = 0.0;

        List<String> resumeSkills =
                new ArrayList<>();

        try {

            MatchScoreResponse response =
                    calculateMatchScore(applicationId);

            matchScore =
                    response.getMatchScore();

            resumeSkills =
                    response.getResumeSkills();

        }

        catch (Exception e){

            System.out.println(
                    "Resume parsing skipped."
            );

        }

        return CandidateDetailsResponse
                .builder()

                .applicationId(
                        application.getId()
                )

                .userId(
                        user.getId()
                )

                .name(
                        user.getName()
                )

                .email(
                        user.getEmail()
                )

                .phone(
                        user.getPhone()
                )

                .location(
                        user.getLocation()
                )

                .education(
                        user.getEducation()
                )

                .experience(
                        user.getExperience()
                )

                .skills(
                        user.getSkills()
                )

                .about(
                        user.getAbout()
                )

                .status(
                        application.getStatus().name()
                )

                .matchScore(
                        matchScore
                )

                .resumeSkills(
                        resumeSkills
                )
                .resumeUrl(
                        application.getResumeUrl()
                )

                .build();
    }
    @Override
    public ResponseEntity<Resource> downloadResume(
            Long applicationId)
            throws Exception {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                ));

        Path path =
                Paths.get(
                        application.getResumeUrl()
                );

        Resource resource =
                new UrlResource(
                        path.toUri()
                );

        if(!resource.exists()) {

            throw new RuntimeException(
                    "Resume not found"
            );

        }

        return ResponseEntity.ok()

                .contentType(
                        MediaType.APPLICATION_PDF
                )

                .header(

                        HttpHeaders.CONTENT_DISPOSITION,

                        "attachment; filename=\""

                                + path.getFileName()

                                + "\""

                )

                .body((Resource) resource);

    }
    @Override
    public SkillGapResponse analyseSkillGap(
            Long applicationId) {

        JobApplication application =
                repo.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        JobDTO job =
                jobClient.getJob(
                        application.getJobId());
        System.out.println("========== FEIGN RESPONSE ==========");
        System.out.println("Application Job ID : " + application.getJobId());

        System.out.println("Job Object         : " + job);
        System.out.println("Job ID             : " + job.getId());
        System.out.println("Title              : " + job.getTitle());
        System.out.println("Company            : " + job.getCompanyName());
        System.out.println("Skills             : " + job.getSkills());
        System.out.println("====================================");

        List<String> resumeSkills;

        try {

            String resumeText =
                    resumeParserService.extractText(
                            application.getResumeUrl());

            resumeSkills =
                    skillExtractorService.extractSkills(
                            resumeText);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to parse resume");

        }

        List<String> requiredSkills =
                Arrays.stream(job.getSkills().split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList();

        List<String> resumeSkillList =
                resumeSkills.stream()
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList();

        List<String> matchedSkills =
                new ArrayList<>();

        List<String> missingSkills =
                new ArrayList<>();

        for(String skill : requiredSkills){

            if(resumeSkillList.contains(skill)){

                matchedSkills.add(skill);

            }

            else{

                missingSkills.add(skill);

            }

        }

        double percentage = 0;

        if(!requiredSkills.isEmpty()){

            percentage =
                    ((double) matchedSkills.size()
                            / requiredSkills.size()) * 100;

        }

        return SkillGapResponse.builder()

                .requiredSkills(requiredSkills)

                .resumeSkills(resumeSkillList)

                .matchedSkills(matchedSkills)

                .missingSkills(missingSkills)

                .matchPercentage(percentage)

                .build();

    }
    @Override
    public RecruiterDashboardResponse
    getRecruiterDashboard(
            Long recruiterId){

        List<JobDTO> jobs =
                jobClient.getJobsByRecruiter(
                        recruiterId
                );

        int totalJobs =
                jobs.size();

        List<Long> jobIds =
                jobs.stream()

                        .map(JobDTO::getId)

                        .toList();

        List<JobApplication> applications =
                repo.findAll()

                        .stream()

                        .filter(a ->

                                jobIds.contains(
                                        a.getJobId()
                                )

                        )

                        .toList();

        int totalApplications =
                applications.size();

        int shortlisted =
                (int) applications.stream()

                        .filter(a ->

                                a.getStatus() ==
                                        ApplicationStatus.SHORTLISTED

                        )

                        .count();

        int interview =
                (int) applications.stream()

                        .filter(a ->

                                a.getStatus() ==
                                        ApplicationStatus.INTERVIEW_SCHEDULED

                        )

                        .count();

        int hired =
                (int) applications.stream()

                        .filter(a ->

                                a.getStatus() ==
                                        ApplicationStatus.HIRED

                        )

                        .count();

        return RecruiterDashboardResponse

                .builder()

                .totalJobs(totalJobs)

                .totalApplications(totalApplications)

                .shortlisted(shortlisted)

                .interviewScheduled(interview)

                .hired(hired)

                .build();

    }
}

