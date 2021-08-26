package com.inthebytes.stacklunch.testapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.inthebytes.stacklunch.security.StackLunchSecurityConfig;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig extends StackLunchSecurityConfig {

	@Override
	public HttpSecurity addSecurityConfigs(HttpSecurity security) throws Exception {
		return security.authorizeRequests()
				.antMatchers("/tests/any/**").permitAll()
				.antMatchers("/tests/users/**").authenticated()
				.antMatchers("/tests/admins/**").hasRole("ADMIN")
				.antMatchers("/tests/customers/**").hasRole("CUSTOMER")
				.antMatchers("/tests/restaurants/**").hasRole("RESTAURANT")
				.antMatchers("/tests/drivers/**").hasRole("DRIVER")
				.and();
	}

}
