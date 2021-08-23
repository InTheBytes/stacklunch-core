package com.inthebytes.stacklunch.security.authentication;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.inthebytes.stacklunch.security.LoginUserDetails;
import com.inthebytes.stacklunch.security.StackLunchJwtDecoder;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;
	private StackLunchJwtDecoder jwt;
	
	public LoginFilter(AuthenticationManager authManager, StackLunchJwtDecoder jwt, String endpoint) {
		super();
		this.authManager = authManager;
		this.jwt = jwt;
		setFilterProcessesUrl(endpoint + "/login");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		abstract class Creds {
			abstract public String getUsername();
			abstract public String getPassword();
		}
		
		Creds credentials = new Creds() {
			private String username = request.getParameter("username");
			private String password = request.getParameter("password");
			public String getUsername() {
				return username;
			}
			public String getPassword() {
				return password;
			}
		};

		return authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						credentials.getUsername(),
						credentials.getPassword(),
						new ArrayList<>()
						)
				);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		response.addHeader(jwt.getHeader(), jwt.encode((LoginUserDetails) authResult.getPrincipal()));
	}
	
	

	
}
