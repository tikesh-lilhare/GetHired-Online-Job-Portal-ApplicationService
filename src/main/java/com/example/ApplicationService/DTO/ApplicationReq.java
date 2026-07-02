package com.example.ApplicationService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationReq {
    @NotNull
    private Long userId;

    @NotNull
    private Long jobId;

    private String resumeUrl;
}
