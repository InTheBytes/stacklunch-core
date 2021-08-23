package com.inthebytes.stacklunch.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inthebytes.stacklunch.service.DaoUserDetailsService;

@Configuration
@ConditionalOnProperty(
		value = "security.login-logout-enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public class StackLunchAuthenticationConfiguration {
	
	@Autowired
	private DaoUserDetailsService detailService;
	
	@Bean
	public DaoAuthenticationProvider daoAuthProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setPasswordEncoder(passwordEncoder());
		dap.setUserDetailsService(detailService);
		return dap;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
