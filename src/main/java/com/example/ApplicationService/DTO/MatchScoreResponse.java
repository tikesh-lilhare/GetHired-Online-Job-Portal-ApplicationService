package com.example.ApplicationService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchScoreResponse {
    private Long applicationId;

    private Long jobId;

    private List<String> resumeSkills;

    private List<String> jobSkills;

    private double matchScore;
}
