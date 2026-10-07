package com.englishportal.controller;

import com.englishportal.dto.EvaluateVocabularyRequest;
import com.englishportal.dto.VocabularyDTO;
import com.englishportal.dto.VocabularyEvaluationResponse;
import com.englishportal.dto.VocabularyReviewResponse;
import com.englishportal.model.UserVocabularyProgress;
import com.englishportal.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
@RequiredArgsConstructor
public class VocabularyController {

    private final VocabularyService vocabularyService;

    @GetMapping("/review")
    public ResponseEntity<VocabularyReviewResponse> getWordsForReview(
            @RequestParam Long userId,
            @RequestParam String level,
            @RequestParam(defaultValue = "20") Integer limit) {
        return ResponseEntity.ok(vocabularyService.getWordsForReview(userId, level, limit));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<VocabularyEvaluationResponse> evaluateWord(
            @RequestParam Long userId,
            @RequestBody EvaluateVocabularyRequest request) {
        return ResponseEntity.ok(
                vocabularyService.evaluateWord(userId, request.getVocabularyId(), Boolean.TRUE.equals(request.getIsCorrect()))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VocabularyDTO> getWordById(@PathVariable Long id) {
        return ResponseEntity.ok(vocabularyService.getWordById(id));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<VocabularyDTO>> getWordsByLevel(@PathVariable String level) {
        return ResponseEntity.ok(vocabularyService.getWordsByLevel(level));
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<List<UserVocabularyProgress>> getUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(vocabularyService.getUserProgress(userId));
    }

    @PostMapping
    public ResponseEntity<VocabularyDTO> createWord(@RequestBody VocabularyDTO vocabularyDTO) {
        return ResponseEntity.ok(vocabularyService.createWord(vocabularyDTO));
    }
}
