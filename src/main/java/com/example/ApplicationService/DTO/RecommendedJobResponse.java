package com.example.ApplicationService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedJobResponse {
    private Long jobId;

    private String title;

    private String companyName;

    private Double matchScore;
}
