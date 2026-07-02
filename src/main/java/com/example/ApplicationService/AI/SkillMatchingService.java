package com.example.ApplicationService.AI;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillMatchingService {
    public double calculateMatchScore(

            List<String> resumeSkills,

            List<String> jobSkills) {

        if(jobSkills.isEmpty()) {

            return 0;
        }

        long matchedSkills =

                jobSkills.stream()

                        .filter(
                                resumeSkills::contains
                        )

                        .count();

        return ((double) matchedSkills
                / jobSkills.size())
                * 100;
    }
}

