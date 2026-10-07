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
public class VocabularyReviewResponse {
    private List<VocabularyDTO> words;
    private Integer totalWords;
    private Integer masteredWords;
    private String level;
}
