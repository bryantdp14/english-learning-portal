package com.englishportal.service;

import com.englishportal.dto.ExerciseDTO;
import com.englishportal.dto.ExerciseOptionDTO;
import com.englishportal.model.Exercise;
import com.englishportal.model.ExerciseOption;
import com.englishportal.model.Lesson;
import com.englishportal.repository.ExerciseRepository;
import com.englishportal.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final LessonRepository lessonRepository;

    public List<ExerciseDTO> getExercisesByLesson(Long lessonId) {
        return exerciseRepository.findByLessonId(lessonId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        return convertToDTO(exercise);
    }

    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Lesson lesson = lessonRepository.findById(exerciseDTO.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        
        Exercise exercise = Exercise.builder()
                .title(exerciseDTO.getTitle())
                .type(exerciseDTO.getType())
                .question(exerciseDTO.getQuestion())
                .audioUrl(exerciseDTO.getAudioUrl())
                .points(exerciseDTO.getPoints())
                .lesson(lesson)
                .build();
        
        Exercise saved = exerciseRepository.save(exercise);
        return convertToDTO(saved);
    }

    private ExerciseDTO convertToDTO(Exercise exercise) {
        List<ExerciseOptionDTO> options = exercise.getOptions().stream()
                .map(opt -> ExerciseOptionDTO.builder()
                        .id(opt.getId())
                        .option(opt.getOption())
                        .isCorrect(opt.getIsCorrect())
                        .build())
                .collect(Collectors.toList());
        
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .title(exercise.getTitle())
                .type(exercise.getType())
                .question(exercise.getQuestion())
                .audioUrl(exercise.getAudioUrl())
                .points(exercise.getPoints())
                .lessonId(exercise.getLesson().getId())
                .options(options)
                .build();
    }
}
