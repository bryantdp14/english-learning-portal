package com.englishportal;

import com.englishportal.service.AchievementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnglishLearningPortalApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EnglishLearningPortalApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(AchievementService achievementService) {
        return args -> achievementService.initializeAchievements();
    }
}
