package com.englishportal.controller;

import com.englishportal.dto.UserStatsDTO;
import com.englishportal.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/top")
    public ResponseEntity<List<UserStatsDTO>> getTopRanking(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(rankingService.getTopRanking(limit));
    }

    @GetMapping("/my-stats")
    public ResponseEntity<UserStatsDTO> getMyStats(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(rankingService.getUserStats(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserStatsDTO> getUserStats(@PathVariable Long userId) {
        return ResponseEntity.ok(rankingService.getUserStats(userId));
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // This is simplified - you would extract the actual user ID
        return 1L; // For now, return a default ID
    }
}
