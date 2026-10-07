package com.englishportal.controller;

import com.englishportal.dto.ExerciseDTO;
import com.englishportal.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(exerciseService.getExercisesByLesson(lessonId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exerciseDTO) {
        return ResponseEntity.ok(exerciseService.createExercise(exerciseDTO));
    }
}
