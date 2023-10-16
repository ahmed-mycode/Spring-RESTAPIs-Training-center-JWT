//package com.center.runner;
//
//import com.center.dto.CourseDTO;
//import com.center.dto.InstructorDTO;
//import com.center.dto.StudentDTO;
//import com.center.dto.UserDTO;
//import com.center.entity.*;
//import com.center.repository.*;
//import com.center.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class MyRunner implements CommandLineRunner {
//
//    @Autowired
//    private CourseService courseService;
//    @Autowired
//    private InstructorService instructorService;
//    @Autowired
//    private StudentService studentService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleService roleService;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Arrays.asList("Admin", "Instructor", "Student").forEach(role -> roleService.createRole(role));
//
//        userService.createUser("admin-1@email.com", "pass1");
//        userService.assignRoleToUser("admin-1@email.com", "Admin");
//
//
//        for (int i = 0; i < 10; i++) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail("instructor"+(i+1)+"@email.com");
//            userDTO.setPassword("1234");
//
//            InstructorDTO instructorDTO = new InstructorDTO();
//            instructorDTO.setFirstName("Instructor"+(i+1)+"FN");
//            instructorDTO.setLastName("Instructor"+(i+1)+"LN");
//            instructorDTO.setSummary("Master "+(i+1));
//            instructorDTO.setUser(userDTO);
//
//            instructorService.createInstructor(instructorDTO);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail("student"+(i+1)+"@email.com");
//            userDTO.setPassword("123");
//
//            StudentDTO studentDTO = new StudentDTO();
//            studentDTO.setFirstName("Student"+(i+1)+"FN");
//            studentDTO.setLastName("Student"+(i+1)+"LN");
//            studentDTO.setLevel("Beginner " + (i+1));
//            studentDTO.setUser(userDTO);
//            studentService.createStudent(studentDTO);
//        }
//
//        for (int i = 0; i < 20; i++) {
//            CourseDTO courseDTO = new CourseDTO();
//            courseDTO.setCourseName("Java course" + (i + 1));
//            courseDTO.setCourseDescription("Java" + (i + 1));
//            courseDTO.setCourseDuration((i + 1) + "Hour");
//            InstructorDTO instructorDTO = new InstructorDTO();
//            instructorDTO.setInstructorId(1);
//            courseDTO.setInstructor(instructorDTO);
//            courseService.createNewCourse(courseDTO);
//        }
//
//        courseService.assignStudentToCourse(1, 1);
//        courseService.assignStudentToCourse(7, 2);
//
//    }
//}
