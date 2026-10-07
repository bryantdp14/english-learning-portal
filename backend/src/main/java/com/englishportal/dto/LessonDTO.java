package com.englishportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String content;
    private Integer order_num;
    private Long courseId;
}
