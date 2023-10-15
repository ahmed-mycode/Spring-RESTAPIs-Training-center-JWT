package com.center.service.impl;

import com.center.dto.StudentDTO;
import com.center.entity.Course;
import com.center.entity.Student;
import com.center.entity.User;
import com.center.mapper.StudentMapper;
import com.center.repository.StudentRepo;
import com.center.service.StudentService;
import com.center.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(UserService userService, StudentRepo studentRepo, StudentMapper studentMapper) {
        this.userService = userService;
        this.studentRepo = studentRepo;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getStudentById(Integer studentId) {
        return studentRepo.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " not found"));
    }

    @Override
    public StudentDTO getStudentByEmail(String email) {
        return studentMapper.fromStudent(studentRepo.findStudentByEmail(email));
    }

    @Override
    public Page<StudentDTO> getStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepo.findStudentsByName(name, pageRequest);
        return new PageImpl<>(studentPage.getContent().stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()), pageRequest, studentPage.getTotalElements());
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        User user = userService.createUser(studentDTO.getUser().getEmail(), studentDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Student");
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(user);
        Student savedStudent = studentRepo.save(student);
        return studentMapper.fromStudent(savedStudent);
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student loadedStudent = getStudentById(studentDTO.getStudentId());
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(loadedStudent.getUser());
        student.setCourses(loadedStudent.getCourses());
        Student updatedStudent = studentRepo.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    @Override
    public void removeStudent(Integer studentId) {
        Student student = getStudentById(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentRepo.deleteById(studentId);
    }
}
