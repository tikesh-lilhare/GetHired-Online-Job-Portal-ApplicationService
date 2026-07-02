package com.example.ApplicationService.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillGapResponse {
    private List<String> requiredSkills;

    private List<String> resumeSkills;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private double matchPercentage;
}
