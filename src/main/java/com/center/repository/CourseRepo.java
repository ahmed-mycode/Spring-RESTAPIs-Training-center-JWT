package com.center.repository;

import com.center.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

    @Query(value = "select * from courses as c where c.course_name like %:keyword%", nativeQuery = true)
    List<Course> findCoursesContains(@Param("keyword") String keyword);

    @Query(value = "select * from courses as c where c.course_id in (select course_id from students_courses as s where s.student_id = :studentId)", nativeQuery = true)
    List<Course> findCoursesByStudentId(@Param("studentId") Integer studentId);
}
