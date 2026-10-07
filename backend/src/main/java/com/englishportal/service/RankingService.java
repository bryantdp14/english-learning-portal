package com.englishportal.service;

import com.englishportal.dto.UserStatsDTO;
import com.englishportal.model.User;
import com.englishportal.repository.UserRepository;
import com.englishportal.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;

    public List<UserStatsDTO> getTopRanking(int limit) {
        List<User> topUsers = userRepository.findAll().stream()
                .sorted((u1, u2) -> u2.getTotalPoints().compareTo(u1.getTotalPoints()))
                .limit(limit)
                .collect(Collectors.toList());
        
        return topUsers.stream()
                .map(user -> UserStatsDTO.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .totalPoints(user.getTotalPoints())
                        .achievements(user.getAchievements().stream()
                                .map(Achievement::getTitle)
                                .collect(Collectors.toList()))
                        .lessonsCompleted((int) userProgressRepository.findByUserId(user.getId()).stream()
                                .filter(up -> up.getCompleted())
                                .count())
                        .build())
                .collect(Collectors.toList());
    }

    public UserStatsDTO getUserStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        int rank = (int) userRepository.findAll().stream()
                .filter(u -> u.getTotalPoints() > user.getTotalPoints())
                .count() + 1;
        
        return UserStatsDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .totalPoints(user.getTotalPoints())
                .rank(rank)
                .achievements(user.getAchievements().stream()
                        .map(Achievement::getTitle)
                        .collect(Collectors.toList()))
                .lessonsCompleted((int) userProgressRepository.findByUserId(userId).stream()
                        .filter(up -> up.getCompleted())
                        .count())
                .build();
    }
}
