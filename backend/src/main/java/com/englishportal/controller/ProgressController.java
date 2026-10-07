package com.englishportal.controller;

import com.englishportal.dto.AnswerResponse;
import com.englishportal.dto.SubmitAnswerRequest;
import com.englishportal.model.UserProgress;
import com.englishportal.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/answer")
    public ResponseEntity<AnswerResponse> submitAnswer(
            Authentication authentication,
            @RequestBody SubmitAnswerRequest request) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(progressService.submitAnswer(userId, request));
    }

    @PostMapping("/lesson/{lessonId}/complete")
    public ResponseEntity<Void> markLessonCompleted(
            Authentication authentication,
            @PathVariable Long lessonId) {
        Long userId = getUserIdFromAuth(authentication);
        progressService.markLessonCompleted(userId, lessonId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-progress")
    public ResponseEntity<List<UserProgress>> getMyProgress(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }

    @GetMapping("/my-points")
    public ResponseEntity<Integer> getMyTotalPoints(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(progressService.getUserTotalPoints(userId));
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // This is simplified - you would extract the actual user ID
        return 1L; // For now, return a default ID
    }
}
