package com.center.web;

import com.center.dto.CourseDTO;
import com.center.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin("*")
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<CourseDTO> searchCourses(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        return courseService.getCoursesByName(keyword, page, size);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createNewCourse(courseDTO);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Integer courseId) {
        courseDTO.setCourseId(courseId);
        return courseService.updateCourse(courseDTO);
    }

    @PostMapping("/{courseId}/enroll/students/{studentId}")
    @PreAuthorize("hasAuthority('Student')")
    public void enrollStudentInCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.assignStudentToCourse(courseId, studentId);
    }
}
