package com.center.service;

import com.center.entity.User;

public interface UserService {

    User getUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String userEmail, String roleName);
}
