package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.PasswordChange;

public interface PasswordChangeRepository extends JpaRepository<PasswordChange, String> {

}
