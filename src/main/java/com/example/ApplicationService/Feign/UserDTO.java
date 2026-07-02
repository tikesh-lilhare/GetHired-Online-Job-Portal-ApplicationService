package com.example.ApplicationService.Feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String role;

    private String phone;

    private String location;

    private String education;

    private String experience;

    private String skills;

    private String about;

    private String profileImage;

    private String resumeUrl;
}
