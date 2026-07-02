package com.example.ApplicationService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="JOBSERVICE")
public interface JobClient {
    @GetMapping("/jobs/{id}")
     JobDTO getJob(
            @PathVariable Long id);

    @GetMapping("/jobs/all")
    List<JobDTO> getAllJob();

    @GetMapping("/jobs/recruiter/{id}")
    List<JobDTO> getJobsByRecruiter(
            @PathVariable Long id
    );
}
