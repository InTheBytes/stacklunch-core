package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
