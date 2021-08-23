package com.inthebytes.stacklunch.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.object.entity.User;

@Repository
@ConditionalOnMissingBean(UserRepository.class)
@ConditionalOnProperty(
		value = "security.login-logout.enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);
}
