package com.center.repository;

import com.center.entity.Instructor;
import com.center.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query(value = "select s from Student as s where s.firstName like %:name% or s.lastName like %:name%")
    Page<Student> findStudentsByName(@Param("name") String name, PageRequest pageRequest);
    //List<Student> findStudentsByName(@Param("name") String name);

    @Query(value = "select s from Student as s where s.user.email = :email")
    Student findStudentByEmail(@Param("email") String email);
}
