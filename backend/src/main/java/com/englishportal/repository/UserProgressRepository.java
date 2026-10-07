package com.englishportal.repository;

import com.englishportal.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
    List<UserProgress> findByUserId(Long userId);
    @Query("SELECT SUM(up.pointsEarned) FROM UserProgress up WHERE up.user.id = ?1")
    Integer getTotalPointsByUserId(Long userId);
}
