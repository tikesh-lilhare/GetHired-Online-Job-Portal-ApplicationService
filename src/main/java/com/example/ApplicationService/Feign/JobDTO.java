package com.example.ApplicationService.Feign;

import lombok.Data;

@Data
public class JobDTO {
    private Long id;

    private String title;

    private String description;

    private String location;

    private Double salary;
    private  String skills;
    private String companyName;
}
