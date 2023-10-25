package com.center.web;

import com.center.dto.CourseDTO;
import com.center.dto.StudentDTO;
import com.center.entity.User;
import com.center.service.CourseService;
import com.center.service.StudentService;
import com.center.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentRestController {

    private final StudentService studentService;
    private final UserService userService;
    private final CourseService courseService;

    public StudentRestController(StudentService studentService, UserService userService, CourseService courseService) {
        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<StudentDTO> searchStudents(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return studentService.getStudentsByName(keyword, page, size);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteStudent(@PathVariable Integer studentId) {
        studentService.removeStudent(studentId);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        User user = userService.getUserByEmail(studentDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email already exist");
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('Student')")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable Integer studentId) {
        studentDTO.setStudentId(studentId);
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("/{studentId}/courses")
    @PreAuthorize("hasAuthority('Student')")
    public Page<CourseDTO> getCoursesByStudentId(
            @PathVariable Integer studentId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        return courseService.getCoursesByStudentId(studentId, page, size);
    }

    @GetMapping("/{studentId}/other-courses")
    @PreAuthorize("hasAuthority('Student')")
    public Page<CourseDTO> getNonEnrolledCoursesByStudentId(
            @PathVariable Integer studentId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        return courseService.getNonEnrolledCoursesByStudentId(studentId, page, size);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('Student')")
    public StudentDTO getStudentByEmail(@RequestParam(name = "email", defaultValue = "") String email) {
        return studentService.getStudentByEmail(email);
    }
}
