package com.center.service;

import com.center.dto.InstructorDTO;
import com.center.entity.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {

    Instructor getInstructorById(Integer instructorId);

    Page<InstructorDTO> getInstructorsByName(String keyword, int page, int size);

    InstructorDTO getInstructorByEmail(String email);

    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO updateInstructor(InstructorDTO instructorDTO);

    List<InstructorDTO> getInstructors();

    void removeInstructor(Integer instructorId);
}
