package com.englishportal.repository;

import com.englishportal.model.UserVocabularyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserVocabularyProgressRepository extends JpaRepository<UserVocabularyProgress, Long> {

    Optional<UserVocabularyProgress> findByUser_IdAndVocabulary_Id(Long userId, Long vocabularyId);

    List<UserVocabularyProgress> findByUser_Id(Long userId);

    List<UserVocabularyProgress> findByUser_IdAndIsMasteredFalse(Long userId);

    @Query("SELECT uvp FROM UserVocabularyProgress uvp WHERE uvp.user.id = ?1 AND uvp.nextReviewDate <= ?2 AND uvp.isMastered = false ORDER BY uvp.vocabulary.difficulty DESC, uvp.mastery ASC")
    List<UserVocabularyProgress> findWordsForReview(Long userId, LocalDateTime now);

    @Query("SELECT COUNT(uvp) FROM UserVocabularyProgress uvp WHERE uvp.user.id = ?1 AND uvp.isMastered = true")
    Long countMasteredWords(Long userId);
}
