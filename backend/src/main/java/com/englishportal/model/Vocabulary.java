package com.englishportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vocabulary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    @Column(columnDefinition = "TEXT")
    private String exampleSentence;

    private String pronunciation;

    private String audioUrl;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false)
    private Integer mastery = 0;
}
