//package com.center.runner;
//
//import com.center.entity.*;
//import com.center.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MyRunner implements CommandLineRunner {
//
//    @Autowired
//    private CourseRepo courseRepo;
//    @Autowired
//    private InstructorRepo instructorRepo;
//    @Autowired
//    private StudentRepo studentRepo;
//    @Autowired
//    private UserRepo userRepo;
//    @Autowired
//    private RoleRepo roleRepo;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Role role1 = new Role("Admin");
//        roleRepo.save(role1);
//        Role role2 = new Role("Instructor");
//        roleRepo.save(role2);
//        Role role3 = new Role("Student");
//        roleRepo.save(role3);
//
//        User user1 = new User("admin@email.com", "pass1");
//        user1.assignRoleToUser(role1);
//        userRepo.save(user1);
//        Instructor instructor1 = new Instructor("Ahmed", "Adel", "Master", user1);
//        instructorRepo.save(instructor1);
//
//        User user2 = new User("instructor@email.com", "pass2");
//        user2.assignRoleToUser(role2);
//        userRepo.save(user2);
//        Instructor instructor2 = new Instructor("instructor", "Adel", "Master", user2);
//        instructorRepo.save(instructor2);
//
//        User user3 = new User("student@email.com", "pass3");
//        user3.assignRoleToUser(role3);
//        userRepo.save(user3);
//        Student student = new Student("Mariam", "Ahmed", "Beginner", user3);
//        studentRepo.save(student);
//
//        Course course1 = new Course("Spring Data JPA", "20 Hour", "Advanced level", instructor1);
//        course1.assignStudentToCourse(student);
//        courseRepo.save(course1);
//    }
//}
