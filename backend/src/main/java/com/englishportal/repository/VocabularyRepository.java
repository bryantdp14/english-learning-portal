package com.englishportal.repository;

import com.englishportal.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByLevel(String level);
    List<Vocabulary> findByTopic(String topic);
    List<Vocabulary> findByLevelAndTopic(String level, String topic);
    Optional<Vocabulary> findByWord(String word);
}
