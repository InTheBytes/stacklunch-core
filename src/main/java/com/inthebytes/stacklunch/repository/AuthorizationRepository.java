package com.inthebytes.stacklunch.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.object.entity.Authorization;

@Repository
@ConditionalOnMissingBean(AuthorizationRepository.class)
public interface AuthorizationRepository extends JpaRepository<Authorization, String>{

}
