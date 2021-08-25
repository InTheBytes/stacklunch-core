package com.inthebytes.stacklunch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;


public abstract class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired(required = false)
	AuthorizationRepository authRepo;
	
	public abstract HttpSecurity addSecurityConfigs(HttpSecurity security) throws Exception;

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security = security.csrf().disable() // TODO: REMOVE ON PROD
            .cors()
            
            .and()
            .sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and()
			.addFilter(new AuthorizationFilter(authenticationManager(), 
					(authRepo == null) ? authorizationRepository().getObject() : authRepo));
			
        security = addSecurityConfigs(security);
        security.httpBasic();
    }
    
    @Override
    @Bean
	public AuthenticationManager authenticationManager() throws Exception {
	    return super.authenticationManagerBean();
	}
    
    @Bean
    @ConditionalOnMissingBean(AuthorizationRepository.class)
    public JpaRepositoryFactoryBean<AuthorizationRepository, Authorization, String> authorizationRepository() {
    	return new JpaRepositoryFactoryBean<AuthorizationRepository, Authorization, String>(AuthorizationRepository.class);
    }
}