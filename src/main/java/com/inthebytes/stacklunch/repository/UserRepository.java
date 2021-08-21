package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
