package com.inthebytes.stacklunch.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.inthebytes.stacklunch.StackLunchApplication;
import com.inthebytes.stacklunch.repository.AuthorizationRepository;
import com.inthebytes.stacklunch.security.authentication.LoginFilter;
import com.inthebytes.stacklunch.security.authentication.LogoutService;

@Configuration
@EnableWebSecurity
public class StackLunchSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Autowired(required = false)
	private DaoAuthenticationProvider loginProvider;
	
	@Autowired(required = false)
	private LogoutService logoutHandler;
	
	@Autowired
	private StackLunchApplication app;
	
	@Autowired
	private StackLunchJwtDecoder decoder;
	
	@Autowired
	private AuthorizationRepository authRepo;

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager (
				Arrays.asList(loginProvider, tokenAuthProvider())
				.stream().filter(x -> x != null).collect(Collectors.toList())
				);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HttpSecurity security = http.csrf().disable().cors()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilter(
				new AuthenticationFilter(authenticationManager(), decoder.getPrefix(), decoder.getHeader(), authRepo)
				);
		
		if (loginProvider != null) {
			security = security.addFilter(new LoginFilter(authenticationManager(), decoder, app.microserviceEndpoint()))
					.logout()
					.logoutUrl(app.microserviceEndpoint() + "/logout")
					.addLogoutHandler(logoutHandler)
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
					.invalidateHttpSession(true)
					.deleteCookies("remove").and();
		}
		
		app.configureEndpointSecurity(security).httpBasic();
	}
	
	@Bean
	public JwtAuthenticationProvider tokenAuthProvider() {
		
		JwtAuthenticationProvider provider = new JwtAuthenticationProvider(decoder);
		provider.setJwtAuthenticationConverter((jwt) -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(jwt.getClaim(decoder.getAuthoritiesKey()));
			return new AbstractAuthenticationToken(Arrays.asList(authority)) {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object getCredentials() {
					return null;
				}

				@Override
				public Object getPrincipal() {
					return jwt.getSubject();
				}
			};
		});
		return provider;
	}
}
