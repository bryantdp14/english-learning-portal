package com.englishportal.service;

import com.englishportal.dto.VocabularyDTO;
import com.englishportal.dto.VocabularyEvaluationResponse;
import com.englishportal.dto.VocabularyReviewResponse;
import com.englishportal.model.User;
import com.englishportal.model.UserVocabularyProgress;
import com.englishportal.model.Vocabulary;
import com.englishportal.repository.UserRepository;
import com.englishportal.repository.UserVocabularyProgressRepository;
import com.englishportal.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final UserVocabularyProgressRepository progressRepository;
    private final UserRepository userRepository;

    public VocabularyReviewResponse getWordsForReview(Long userId, String level, Integer limit) {
        List<UserVocabularyProgress> wordsForReview = progressRepository.findWordsForReview(userId, LocalDateTime.now());

        if (wordsForReview.isEmpty()) {
            List<Vocabulary> newWords = vocabularyRepository.findByLevel(level).stream()
                    .filter(word -> progressRepository.findByUser_IdAndVocabulary_Id(userId, word.getId()).isEmpty())
                    .sorted(Comparator.comparingInt(Vocabulary::getDifficulty).reversed())
                    .limit(limit != null ? limit : 20)
                    .collect(Collectors.toList());

            return VocabularyReviewResponse.builder()
                    .words(newWords.stream().map(this::convertToDTO).collect(Collectors.toList()))
                    .totalWords(newWords.size())
                    .masteredWords(0)
                    .level(level)
                    .build();
        }

        List<VocabularyDTO> vocabularyDTOs = wordsForReview.stream()
                .limit(limit != null ? limit : 20)
                .map(progress -> convertToDTO(progress.getVocabulary()))
                .collect(Collectors.toList());

        Long masteredCount = progressRepository.countMasteredWords(userId);

        return VocabularyReviewResponse.builder()
                .words(vocabularyDTOs)
                .totalWords(vocabularyDTOs.size())
                .masteredWords(Math.toIntExact(masteredCount))
                .level(level)
                .build();
    }

    public VocabularyEvaluationResponse evaluateWord(Long userId, Long vocabularyId, boolean isCorrect) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        UserVocabularyProgress progress = progressRepository.findByUser_IdAndVocabulary_Id(userId, vocabularyId)
                .orElseGet(() -> UserVocabularyProgress.builder()
                        .user(user)
                        .vocabulary(vocabulary)
                        .mastery(0)
                        .correctCount(0)
                        .wrongCount(0)
                        .nextReviewDate(LocalDateTime.now())
                        .isMastered(false)
                        .build());

        int pointsEarned = 0;
        boolean nowMastered = false;
        String message;

        if (isCorrect) {
            progress.setCorrectCount(progress.getCorrectCount() + 1);
            progress.setMastery(Math.min(100, progress.getMastery() + 15));
            pointsEarned = 10;
            user.setTotalPoints(user.getTotalPoints() + pointsEarned);
            message = "Correct! +10 points";
        } else {
            progress.setWrongCount(progress.getWrongCount() + 1);
            progress.setMastery(Math.max(0, progress.getMastery() - 10));
            message = "Incorrect. Try again next time!";
        }

        if (progress.getMastery() >= 80 && !Boolean.TRUE.equals(progress.getIsMastered())) {
            progress.setIsMastered(true);
            progress.setNextReviewDate(LocalDateTime.now().plusDays(7));
            nowMastered = true;
            message = "Word mastered! 🎉";
        } else if (!Boolean.TRUE.equals(progress.getIsMastered())) {
            int daysUntilReview = Math.max(1, 5 - (progress.getMastery() / 20));
            progress.setNextReviewDate(LocalDateTime.now().plusDays(daysUntilReview));
        }

        progress.setLastReviewedAt(LocalDateTime.now());
        progressRepository.save(progress);
        userRepository.save(user);

        return VocabularyEvaluationResponse.builder()
                .correct(isCorrect)
                .newMastery(progress.getMastery())
                .pointsEarned(pointsEarned)
                .nowMastered(nowMastered)
                .message(message)
                .build();
    }

    public VocabularyDTO createWord(VocabularyDTO vocabularyDTO) {
        Vocabulary vocabulary = Vocabulary.builder()
                .word(vocabularyDTO.getWord())
                .meaning(vocabularyDTO.getMeaning())
                .exampleSentence(vocabularyDTO.getExampleSentence())
                .pronunciation(vocabularyDTO.getPronunciation())
                .audioUrl(vocabularyDTO.getAudioUrl())
                .level(vocabularyDTO.getLevel())
                .topic(vocabularyDTO.getTopic())
                .difficulty(vocabularyDTO.getDifficulty())
                .mastery(0)
                .build();

        Vocabulary saved = vocabularyRepository.save(vocabulary);
        return convertToDTO(saved);
    }

    public List<VocabularyDTO> getWordsByLevel(String level) {
        return vocabularyRepository.findByLevel(level).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VocabularyDTO getWordById(Long id) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));
        return convertToDTO(vocabulary);
    }

    public List<UserVocabularyProgress> getUserProgress(Long userId) {
        return progressRepository.findByUser_Id(userId);
    }

    private VocabularyDTO convertToDTO(Vocabulary vocabulary) {
        return VocabularyDTO.builder()
                .id(vocabulary.getId())
                .word(vocabulary.getWord())
                .meaning(vocabulary.getMeaning())
                .exampleSentence(vocabulary.getExampleSentence())
                .pronunciation(vocabulary.getPronunciation())
                .audioUrl(vocabulary.getAudioUrl())
                .level(vocabulary.getLevel())
                .topic(vocabulary.getTopic())
                .difficulty(vocabulary.getDifficulty())
                .mastery(vocabulary.getMastery())
                .build();
    }
}
