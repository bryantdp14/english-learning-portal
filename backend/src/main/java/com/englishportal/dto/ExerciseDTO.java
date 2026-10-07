package com.englishportal.dto;

import com.englishportal.model.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String title;
    private ExerciseType type;
    private String question;
    private String audioUrl;
    private Integer points;
    private Long lessonId;
    private List<ExerciseOptionDTO> options;
}
