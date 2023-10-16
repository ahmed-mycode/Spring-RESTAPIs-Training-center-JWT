package com.center.service.impl;

import com.center.dto.CourseDTO;
import com.center.entity.Course;
import com.center.entity.Instructor;
import com.center.entity.Student;
import com.center.mapper.CourseMapper;
import com.center.repository.CourseRepo;
import com.center.repository.InstructorRepo;
import com.center.repository.StudentRepo;
import com.center.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final InstructorRepo instructorRepo;
    private final StudentRepo studentRepo;

    public CourseServiceImpl(CourseRepo courseRepo, CourseMapper courseMapper, InstructorRepo instructorRepo, StudentRepo studentRepo) {
        this.courseRepo = courseRepo;
        this.courseMapper = courseMapper;
        this.instructorRepo = instructorRepo;
        this.studentRepo = studentRepo;
    }

    // ************************* //

    @Override
    public Course findCourseById(Integer id) {
        return courseRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Course " + id + " not found"));
    }

    @Override
    public CourseDTO createNewCourse(CourseDTO courseDTO) {
        Course course = courseMapper.fromCourseDTO(courseDTO);
        Instructor instructor = instructorRepo.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
        course.setInstructor(instructor);
        Course savedCourse = courseRepo.save(course);
        return courseMapper.fromCourse(savedCourse);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course courseLoaded = findCourseById(courseDTO.getCourseId());
        Instructor instructor = instructorRepo.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
        Course course = courseMapper.fromCourseDTO(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(courseLoaded.getStudents());
        course.setCreatedAt(courseLoaded.getCreatedAt());
        Course updatedCourse = courseRepo.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }

    @Override
    public void assignStudentToCourse(Integer courseId, Integer studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Course course = findCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDTO> getCoursesByStudentId(Integer studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> studentCoursePage = courseRepo.findCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(studentCoursePage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, studentCoursePage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> getCoursesByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepo.findCoursesContains(name, pageRequest);
        return new PageImpl<>(coursePage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, coursePage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> getNonEnrolledCoursesByStudentId(Integer studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> NonEnrolledCoursesPage = courseRepo.findNonEnrolledCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(NonEnrolledCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, NonEnrolledCoursesPage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> getCoursesByInstructorId(Integer instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> instructorCoursesPage = courseRepo.findCoursesByInstructorId(instructorId, pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, instructorCoursesPage.getTotalElements());
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseRepo.deleteById(courseId);
    }
}
