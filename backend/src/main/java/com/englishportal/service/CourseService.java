package com.englishportal.service;

import com.englishportal.dto.CourseDTO;
import com.englishportal.model.Course;
import com.englishportal.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public CourseDTO getCourseByCode(String code) {
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = Course.builder()
                .code(courseDTO.getCode())
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .level(courseDTO.getLevel())
                .build();
        Course saved = courseRepository.save(course);
        return convertToDTO(saved);
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .code(course.getCode())
                .title(course.getTitle())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }
}
