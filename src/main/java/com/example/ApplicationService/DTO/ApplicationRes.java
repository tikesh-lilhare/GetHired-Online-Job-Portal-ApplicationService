package com.example.ApplicationService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRes {
    private Long id;

    private Long userId;

    private Long jobId;

    private String resumeUrl;

    private String status;
}
