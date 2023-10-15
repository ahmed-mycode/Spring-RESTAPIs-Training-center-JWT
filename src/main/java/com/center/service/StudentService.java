package com.center.service;

import com.center.dto.StudentDTO;
import com.center.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {

    Student getStudentById(Integer studentId);

    StudentDTO getStudentByEmail(String email);

    Page<StudentDTO> getStudentsByName(String name, int page, int size);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(StudentDTO studentDTO);

    void removeStudent(Integer studentId);
}