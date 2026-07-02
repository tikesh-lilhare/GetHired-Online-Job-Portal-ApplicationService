package com.example.ApplicationService.AI;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillExtractionService {
    private static final List<String> SKILLS =
            List.of(

                    "Java",
                    "Spring Boot",
                    "Hibernate",
                    "Microservices",
                    "React",
                    "Angular",
                    "JavaScript",
                    "HTML",
                    "CSS",
                    "MySQL",
                    "MongoDB",
                    "Docker",
                    "Kubernetes",
                    "AWS",
                    "Python",
                    "Machine Learning",
                    "Deep Learning",
                    "TensorFlow",
                    "Pandas",
                    "NumPy",
                    "Power BI",
                    "Tableau"
            );

    public List<String> extractSkills(
            String resumeText) {

        return SKILLS.stream()

                .filter(skill ->

                        resumeText
                                .toLowerCase()
                                .contains(
                                        skill.toLowerCase()
                                )
                )

                .toList();
    }
}
