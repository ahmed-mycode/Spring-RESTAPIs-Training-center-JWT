package com.center.service.impl;

import com.center.entity.Role;
import com.center.repository.RoleRepo;
import com.center.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }


    @Override
    public Role createRole(String roleName) {
        return roleRepo.save(new Role(roleName));
    }
}
