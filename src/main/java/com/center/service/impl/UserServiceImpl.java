package com.center.service.impl;

import com.center.entity.Role;
import com.center.entity.User;
import com.center.repository.RoleRepo;
import com.center.repository.UserRepo;
import com.center.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null) throw new RuntimeException("User with email: " + email + " already exist");
        String encodedPassword = passwordEncoder.encode(password);
        return userRepo.save(new User(email, encodedPassword));
    }

    @Override
    public void assignRoleToUser(String userEmail, String roleName) {
        User user = getUserByEmail(userEmail);
        Role role = roleRepo.findRoleByName(roleName);
        user.assignRoleToUser(role);
    }
}
