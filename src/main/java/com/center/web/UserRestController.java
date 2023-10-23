package com.center.web;

import com.center.entity.User;
import com.center.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public boolean checkIfEmailExist(@RequestParam(name = "email", defaultValue = "") String email){
        return userService.getUserByEmail(email) != null;
    }
}
