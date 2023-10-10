package com.center.repository;

import com.center.entity.Course;
import com.center.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

    @Query(value = "select * from courses as c where c.course_name like %:keyword%", nativeQuery = true)
    Page<Course> findCoursesContains(@Param("keyword") String keyword, PageRequest pageRequest);
    //List<Course> findCoursesContains(@Param("keyword") String keyword);

    @Query(value = "select * from courses as c where c.course_id in (select course_id from students_courses as s where s.student_id = :studentId)", nativeQuery = true)
    Page<Course> findCoursesByStudentId(@Param("studentId") Integer studentId, PageRequest pageRequest);
    //List<Course> findCoursesByStudentId(@Param("studentId") Integer studentId);

    @Query(value = "select * from courses as c where c.course_id not in (select course_id from students_courses as s where s.student_id = :studentId)", nativeQuery = true)
    Page<Course> findNonEnrolledCoursesByStudentId(@Param("studentId") Integer studentId, PageRequest pageRequest);
    //List<Course> findNonEnrolledCoursesByStudentId(@Param("studentId") Integer studentId);

    @Query(value = "select c from Course as c where c.instructor.instructorId = :instructorId")
    Page<Course> findCoursesByInstructorId(@Param("instructorId") Integer instructorId, PageRequest pageRequest);
    //List<Course> findCoursesByInstructorId(@Param("instructorId") Integer instructorId);

}
