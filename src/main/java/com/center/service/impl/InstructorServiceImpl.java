package com.center.service.impl;

import com.center.dto.InstructorDTO;
import com.center.entity.Course;
import com.center.entity.Instructor;
import com.center.entity.User;
import com.center.mapper.InstructorMapper;
import com.center.repository.InstructorRepo;
import com.center.service.CourseService;
import com.center.service.InstructorService;
import com.center.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final InstructorMapper instructorMapper;
    private final CourseService courseService;
    private final UserService userService;

    public InstructorServiceImpl(InstructorRepo instructorRepo, InstructorMapper instructorMapper, CourseService courseService, UserService userService) {
        this.instructorRepo = instructorRepo;
        this.instructorMapper = instructorMapper;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public Instructor getInstructorById(Integer instructorId) {
        return instructorRepo.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " not found"));
    }

    @Override
    public Page<InstructorDTO> getInstructorsByName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorByNamePage = instructorRepo.findInstructorsByName(keyword, pageRequest);
        return new PageImpl<>(instructorByNamePage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()), pageRequest, instructorByNamePage.getTotalElements());
    }

    @Override
    public InstructorDTO getInstructorByEmail(String email) {
        return instructorMapper.fromInstructor(instructorRepo.findInstructorByEmail(email));
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        User user = userService.createUser(instructorDTO.getUser().getEmail(), instructorDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Instructor");
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorRepo.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        Instructor loadedInstructor = getInstructorById(instructorDTO.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(loadedInstructor.getUser());
        instructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorRepo.save(instructor);
        return instructorMapper.fromInstructor(updatedInstructor);
    }

    @Override
    public List<InstructorDTO> getInstructors() {
        return instructorRepo.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Integer instructorId) {
        Instructor instructor = getInstructorById(instructorId);
        for (Course course : instructor.getCourses()) {
            courseService.deleteCourse(course.getCourseId());
        }

        instructorRepo.deleteById(instructorId);
    }
}
