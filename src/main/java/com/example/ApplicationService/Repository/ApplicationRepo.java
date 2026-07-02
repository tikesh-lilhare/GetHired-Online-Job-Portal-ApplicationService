package com.example.ApplicationService.Repository;

import com.example.ApplicationService.Entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<JobApplication,Long> {
    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByUserId(Long userId);

    List<JobApplication> findByJobIdIn(
            List<Long> jobIds
    );
}
