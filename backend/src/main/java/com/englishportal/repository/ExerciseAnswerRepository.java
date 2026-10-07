package com.englishportal.repository;

import com.englishportal.model.ExerciseAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseAnswerRepository extends JpaRepository<ExerciseAnswer, Long> {
    List<ExerciseAnswer> findByUserId(Long userId);
    List<ExerciseAnswer> findByUserIdAndExerciseId(Long userId, Long exerciseId);
}
