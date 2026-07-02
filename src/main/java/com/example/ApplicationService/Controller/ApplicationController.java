package com.example.ApplicationService.Controller;

import com.example.ApplicationService.DTO.*;
import com.example.ApplicationService.Service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ApplicationRes applyJob(
            @Valid
            @RequestBody
            ApplicationReq request) {

        return applicationService.applyJob(
                request);
    }
    @GetMapping
    public List<ApplicationRes>
    getAllApplications() {

        return applicationService.getAllApplications();
    }
    @GetMapping("/{id}")
    public ApplicationRes getApplication(
            @PathVariable Long id) {

        return applicationService.getApplicationById(
                id);
    }
    @PostMapping(value = "/apply",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApplicationRes applyJob(
            @RequestParam Long userId,
            @RequestParam Long jobId,
            @RequestParam MultipartFile resume)
            throws Exception {

        return applicationService
                .applyJob(
                        userId,
                        jobId,
                        resume
                );
    }
    @GetMapping("/job/{jobId}")
    public List<ApplicationRes>
    getApplicationsByJob(
            @PathVariable Long jobId) {

        return applicationService
                .getApplicationsByJob(jobId);
    }
    @GetMapping("/user/{userId}")
    public List<ApplicationRes>
    getApplicationsByUser(
            @PathVariable Long userId) {

        return applicationService
                .getApplicationsByUser(userId);
    }
    @PutMapping("/{id}/status")
    public ApplicationRes updateStatus(

            @PathVariable Long id,

            @RequestBody
            ApplicationStatusUpdateReq request) {

        return applicationService
                .updateStatus(
                        id,
                        request
                );
    }
    @GetMapping("/{applicationId}/skills")
    public ResumeSkillResponse extractSkills(
            @PathVariable Long applicationId)
            throws Exception {

        return applicationService
                .extractSkillsFromResume(
                        applicationId
                );
    }
    @GetMapping(
            "/{applicationId}/match-score"
    )
    public MatchScoreResponse
    getMatchScore(

            @PathVariable
            Long applicationId)

            throws Exception {

        return applicationService
                .calculateMatchScore(
                        applicationId
                );
    }
    @GetMapping(
            "/recommend-jobs/{applicationId}"
    )
    public List<RecommendedJobResponse>
    recommendJobs(

            @PathVariable
            Long applicationId)

            throws Exception {

        return applicationService
                .recommendJobs(
                        applicationId
                );
    }
    @GetMapping("/candidate/{applicationId}")
    public CandidateDetailsResponse getCandidateDetails(
            @PathVariable Long applicationId)
            throws Exception {

        return applicationService
                .getCandidateDetails(applicationId);
    }
    @GetMapping("/{applicationId}/download-resume")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long applicationId)
            throws Exception {

        return applicationService.downloadResume(
                applicationId
        );

    }
    @GetMapping("/{applicationId}/skill-gap")
    public SkillGapResponse getSkillGap(

            @PathVariable
            Long applicationId){

        return applicationService
                .analyseSkillGap(
                        applicationId
                );

    }
    @GetMapping("/dashboard/{recruiterId}")
    public RecruiterDashboardResponse dashboard(

            @PathVariable
            Long recruiterId){

        return applicationService
                .getRecruiterDashboard(
                        recruiterId
                );

    }
}
