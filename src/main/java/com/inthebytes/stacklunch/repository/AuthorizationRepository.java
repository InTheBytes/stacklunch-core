package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization, String>{

}
