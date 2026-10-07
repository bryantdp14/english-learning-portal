package com.englishportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VocabularyDTO {
    private Long id;
    private String word;
    private String meaning;
    private String exampleSentence;
    private String pronunciation;
    private String audioUrl;
    private String level;
    private String topic;
    private Integer difficulty;
    private Integer mastery;
}
