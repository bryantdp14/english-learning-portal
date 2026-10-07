package com.englishportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDTO {
    private Long userId;
    private String username;
    private Integer totalPoints;
    private Integer rank;
    private List<String> achievements;
    private Integer lessonsCompleted;
}
