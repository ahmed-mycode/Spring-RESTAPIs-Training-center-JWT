package com.center.repository;

import com.center.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    @Query(value = "select r from Role as r where r.name = :name")
    Role findRoleByName(@Param("name") String name);
}
