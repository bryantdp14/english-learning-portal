package com.englishportal.service;

import com.englishportal.dto.LessonDTO;
import com.englishportal.model.Course;
import com.englishportal.model.Lesson;
import com.englishportal.repository.CourseRepository;
import com.englishportal.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public List<LessonDTO> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrder_numAsc(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return convertToDTO(lesson);
    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Lesson lesson = Lesson.builder()
                .title(lessonDTO.getTitle())
                .content(lessonDTO.getContent())
                .order_num(lessonDTO.getOrder_num())
                .course(course)
                .build();
        
        Lesson saved = lessonRepository.save(lesson);
        return convertToDTO(saved);
    }

    private LessonDTO convertToDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .order_num(lesson.getOrder_num())
                .courseId(lesson.getCourse().getId())
                .build();
    }
}
