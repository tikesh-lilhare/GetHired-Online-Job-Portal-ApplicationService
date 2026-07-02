package com.example.ApplicationService.Service;

import com.example.ApplicationService.DTO.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {
    ApplicationRes applyJob(
            ApplicationReq request);

    List<ApplicationRes> getAllApplications();

    ApplicationRes getApplicationById(
            Long id);
    ApplicationRes applyJob(
            Long userId,
            Long jobId,
            MultipartFile resume)
            throws Exception;

    List<ApplicationRes> getApplicationsByJob(Long jobId);

    List<ApplicationRes> getApplicationsByUser(Long userId);

    ApplicationRes updateStatus(
            Long applicationId,
            ApplicationStatusUpdateReq request
    );

    ResumeSkillResponse
    extractSkillsFromResume(
            Long applicationId)
            throws Exception;

    MatchScoreResponse
    calculateMatchScore(
            Long applicationId)
            throws Exception;

    List<RecommendedJobResponse>
    recommendJobs(
            Long applicationId
    ) throws Exception;
    CandidateDetailsResponse
    getCandidateDetails(
            Long applicationId
    ) throws Exception;
    ResponseEntity<Resource> downloadResume(
            Long applicationId
    ) throws Exception;
    SkillGapResponse analyseSkillGap(
            Long applicationId
    );
    RecruiterDashboardResponse
    getRecruiterDashboard(
            Long recruiterId
    );
}
