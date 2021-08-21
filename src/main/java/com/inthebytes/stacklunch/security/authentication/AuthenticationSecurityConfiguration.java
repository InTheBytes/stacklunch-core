package com.inthebytes.stacklunch.security.authentication;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.inthebytes.stacklunch.StackLunchApplication;

@Configuration
public class AuthenticationSecurityConfiguration {
	
//	@Autowired
//	private StackLunchApplication app;
//	
//	@Autowired
//	private AuthenticationManager authManager;
	
	public HttpSecurity addAuthenticationFilter(HttpSecurity http) {
		return http;
	}
	

	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }

}
