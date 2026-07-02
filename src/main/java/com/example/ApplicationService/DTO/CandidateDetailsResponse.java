package com.example.ApplicationService.DTO;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDetailsResponse {
    private Long applicationId;

    private Long userId;

    private String name;

    private String email;

    private String phone;

    private String location;

    private String education;

    private String experience;

    private String skills;

    private String about;

    private String status;

    private Double matchScore;

    private List<String> resumeSkills;
    private String resumeUrl;
}
