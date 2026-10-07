package com.englishportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VocabularyEvaluationResponse {
    private Boolean correct;
    private Integer newMastery;
    private Integer pointsEarned;
    private Boolean nowMastered;
    private String message;
}
