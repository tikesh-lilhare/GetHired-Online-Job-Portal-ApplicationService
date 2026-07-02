package com.example.ApplicationService.DTO;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterDashboardResponse {
    private int totalJobs;

    private int totalApplications;

    private int shortlisted;

    private int interviewScheduled;

    private int hired;
}
