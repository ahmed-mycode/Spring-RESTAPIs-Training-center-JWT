package com.center.web;

import com.center.dto.CourseDTO;
import com.center.dto.InstructorDTO;
import com.center.entity.User;
import com.center.service.CourseService;
import com.center.service.InstructorService;
import com.center.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorRestController {

    private final InstructorService instructorService;
    private final UserService userService;
    private final CourseService courseService;

    public InstructorRestController(InstructorService instructorService, UserService userService, CourseService courseService) {
        this.instructorService = instructorService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public Page<InstructorDTO> searchInstructors(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return instructorService.getInstructorsByName(keyword, page, size);
    }

    @GetMapping("/all")
    public List<InstructorDTO> findAllInstructors() {
        return instructorService.getInstructors();
    }

    @PostMapping
    public InstructorDTO saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        User user = userService.getUserByEmail(instructorDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email already exist");
        return instructorService.createInstructor(instructorDTO);
    }

    @PutMapping("/{instructorId}")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO, @PathVariable Integer instructorId) {
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }

    @GetMapping("/{instructorId}/courses")
    public Page<CourseDTO> coursesByInstructorId(@PathVariable Integer instructorId,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return courseService.getCoursesByInstructorId(instructorId, page, size);
    }

    @GetMapping("/find")
    public InstructorDTO getInstructorByEmail(@RequestParam(name = "email", defaultValue = "") String email){
        return instructorService.getInstructorByEmail(email);
    }

    @DeleteMapping("/{instructorId}")
    public void removeInstructor(@PathVariable Integer instructorId) {
        instructorService.removeInstructor(instructorId);
    }
}
