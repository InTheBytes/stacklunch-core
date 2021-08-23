package com.inthebytes.stacklunch.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inthebytes.stacklunch.object.entity.User;

public class LoginUserDetails implements UserDetails {
	
	private static final long serialVersionUID = -3628439372315076592L;
	
	private User user;
	
	public LoginUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = "ROLE_" + user.getRole().getName().toUpperCase();
		GrantedAuthority auth = new SimpleGrantedAuthority(role);
		return Arrays.asList(auth);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getActive();
	}

	@Override
	public boolean isEnabled() {
		return user.getActive();
	}

}
