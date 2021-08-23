package com.inthebytes.stacklunch.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.inthebytes.stacklunch.repository.AuthorizationRepository;

public class AuthenticationFilter extends BasicAuthenticationFilter {
	
	private Set<String> paths = new HashSet<String>(Arrays.asList("/health", "/login"));
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	private String jwtPrefix;
	private String jwtHeader;
	private AuthorizationRepository repo;

	public AuthenticationFilter(AuthenticationManager authenticationManager, 
			String prefix, String header, AuthorizationRepository tokenRepo) {
		super(authenticationManager);
		jwtPrefix = prefix;
		jwtHeader = header;
		repo = tokenRepo;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return paths.stream().anyMatch(path -> pathMatcher.match(path, request.getRequestURI()));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = request.getHeader(jwtHeader).replace(jwtPrefix, "");
		if (repo.existsById(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			Authentication auth = getAuthenticationManager().authenticate(new BearerTokenAuthenticationToken(token));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		
		request.setAttribute("username", authResult.getPrincipal());
		
		Iterator<? extends GrantedAuthority> iterator = authResult.getAuthorities().iterator();
		request.setAttribute("role", iterator.next().toString().replace("ROLE_", "").toLowerCase());
	}
	
	
}
