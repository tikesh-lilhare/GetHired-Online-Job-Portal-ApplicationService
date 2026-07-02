package com.example.ApplicationService.DTO;

import com.example.ApplicationService.Entity.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationStatusUpdateReq {
    private ApplicationStatus status;
}
