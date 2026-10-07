package com.englishportal.service;

import com.englishportal.model.Achievement;
import com.englishportal.model.User;
import com.englishportal.repository.AchievementRepository;
import com.englishportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public void checkAndAwardAchievements(User user) {
        // Check for 100 points achievement
        if (user.getTotalPoints() >= 100) {
            awardAchievement(user, "100_points");
        }
        
        // Check for 500 points achievement
        if (user.getTotalPoints() >= 500) {
            awardAchievement(user, "500_points");
        }
        
        // Check for 1000 points achievement
        if (user.getTotalPoints() >= 1000) {
            awardAchievement(user, "1000_points");
        }
    }

    private void awardAchievement(User user, String badge) {
        Optional<Achievement> achievement = achievementRepository.findByBadge(badge);
        
        if (achievement.isPresent() && !user.getAchievements().contains(achievement.get())) {
            user.getAchievements().add(achievement.get());
            userRepository.save(user);
        }
    }

    public void initializeAchievements() {
        if (achievementRepository.findByBadge("100_points").isEmpty()) {
            achievementRepository.save(Achievement.builder()
                    .badge("100_points")
                    .title("Century")
                    .description("Earn 100 points")
                    .build());
        }
        
        if (achievementRepository.findByBadge("500_points").isEmpty()) {
            achievementRepository.save(Achievement.builder()
                    .badge("500_points")
                    .title("Half Thousand")
                    .description("Earn 500 points")
                    .build());
        }
        
        if (achievementRepository.findByBadge("1000_points").isEmpty()) {
            achievementRepository.save(Achievement.builder()
                    .badge("1000_points")
                    .title("Master")
                    .description("Earn 1000 points")
                    .build());
        }
    }
}
