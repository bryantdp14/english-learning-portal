package com.englishportal.service;

import com.englishportal.dto.AnswerResponse;
import com.englishportal.dto.SubmitAnswerRequest;
import com.englishportal.model.*;
import com.englishportal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserProgressRepository userProgressRepository;
    private final ExerciseAnswerRepository exerciseAnswerRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    public AnswerResponse submitAnswer(Long userId, SubmitAnswerRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        
        ExerciseOption correctOption = exercise.getOptions().stream()
                .filter(ExerciseOption::getIsCorrect)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No correct option found"));
        
        boolean isCorrect = request.getAnswer().equals(correctOption.getOption());
        
        ExerciseAnswer answer = ExerciseAnswer.builder()
                .user(user)
                .exercise(exercise)
                .answer(request.getAnswer())
                .isCorrect(isCorrect)
                .build();
        
        exerciseAnswerRepository.save(answer);
        
        int pointsEarned = 0;
        String message = "Incorrect answer";
        
        if (isCorrect) {
            pointsEarned = exercise.getPoints();
            message = "Correct! You earned " + pointsEarned + " points";
            
            user.setTotalPoints(user.getTotalPoints() + pointsEarned);
            userRepository.save(user);
            
            achievementService.checkAndAwardAchievements(user);
        }
        
        return AnswerResponse.builder()
                .correct(isCorrect)
                .pointsEarned(pointsEarned)
                .message(message)
                .build();
    }

    public void markLessonCompleted(Long userId, Long lessonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Lesson lesson = new Lesson(); // simplified
        lesson.setId(lessonId);
        
        UserProgress progress = userProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> UserProgress.builder()
                        .user(user)
                        .lesson(lesson)
                        .build());
        
        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        userProgressRepository.save(progress);
    }

    public List<UserProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId);
    }

    public Integer getUserTotalPoints(Long userId) {
        Integer total = userProgressRepository.getTotalPointsByUserId(userId);
        return total != null ? total : 0;
    }
}
