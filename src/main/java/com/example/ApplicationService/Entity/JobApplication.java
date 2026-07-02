package com.example.ApplicationService.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Audited;

@Entity
@Audited.Table(name="applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long jobId;

    private String resumeUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
