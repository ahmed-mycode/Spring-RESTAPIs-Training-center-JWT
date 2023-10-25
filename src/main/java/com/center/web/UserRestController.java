package com.center.web;

import com.center.entity.User;
import com.center.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public boolean checkIfEmailExist(@RequestParam(name = "email", defaultValue = "") String email) {
        return userService.getUserByEmail(email) != null;
    }
}
