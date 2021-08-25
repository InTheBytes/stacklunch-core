package com.inthebytes.stacklunch.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;


public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	private AuthorizationRepository authRepo;

	public AuthorizationFilter(AuthenticationManager authenticationManager, AuthorizationRepository authRepo) {
		super(authenticationManager);
		this.authRepo = authRepo;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().contains("actuator");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Authentication authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}
	
	
	private Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(JwtProperties.HEADER_STRING);
		if (token == null) {
			return null;
		} else {
			token = token.replace(JwtProperties.TOKEN_PREFIX, "");
		}
		
		if (!authRepo.findById(token).isPresent()) {
			try {
				DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
						.build()
						.verify(token);
			
				String role = jwt.getClaim(JwtProperties.AUTHORITIES_KEY)
						.asString();
						
				String userName = jwt.getSubject();
			
				if (role != null && userName != null) {
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					GrantedAuthority authority = new SimpleGrantedAuthority(role);
					authorities.add(authority);
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, authorities);
					request.setAttribute("username", userName);
					return auth;
				}
			} catch (TokenExpiredException ex) {
				return null;
			}
			return null;
		}
		return null;
	}
	
}
