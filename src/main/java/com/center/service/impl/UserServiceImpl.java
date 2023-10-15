package com.center.service.impl;

import com.center.entity.Role;
import com.center.entity.User;
import com.center.repository.RoleRepo;
import com.center.repository.UserRepo;
import com.center.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userRepo.save(new User(email, password));
    }

    @Override
    public void assignRoleToUser(String userEmail, String roleName) {
        User user = getUserByEmail(userEmail);
        Role role = roleRepo.findRoleByName(roleName);
        user.assignRoleToUser(role);
    }
}
