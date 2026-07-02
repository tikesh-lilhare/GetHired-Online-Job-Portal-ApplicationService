package com.example.ApplicationService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResumeSkillResponse {
    private Long applicationId;

    private List<String> skills;
}
