package com.center.service;

import com.center.dto.CourseDTO;
import com.center.dto.StudentDTO;
import com.center.entity.Course;
import com.center.entity.Student;
import org.springframework.data.domain.Page;

public interface CourseService {

    Course findCourseById(Integer id);

    Page<CourseDTO> getCoursesByName(String name, int page, int size);

    CourseDTO createNewCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(CourseDTO courseDTO);

    void assignStudentToCourse(Integer courseId, Integer studentId);

    Page<CourseDTO> getCoursesByStudentId(Integer studentId, int page, int size);

    Page<CourseDTO> getNonEnrolledCoursesByStudentId(Integer studentId, int page, int size);

    Page<CourseDTO> getCoursesByInstructorId(Integer instructorId, int page, int size);

    void deleteCourse(Integer courseId);
}
