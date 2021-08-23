package com.inthebytes.stacklunch.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inthebytes.stacklunch.object.entity.User;
import com.inthebytes.stacklunch.repository.UserRepository;
import com.inthebytes.stacklunch.security.LoginUserDetails;

@Service
@ConditionalOnProperty(
		value = "security.login-logout.enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public class DaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByUsername(username);
		if (user.isPresent()) {
			return new LoginUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException(username);
		}
	}
}
