package com.inthebytes.stacklunch.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;

@Configuration
@ConditionalOnMissingBean(AuthorizationRepository.class)
//@ConditionalOnBean(SecurityConfig.class)
@ComponentScan("com.inthebytes.stacklunch.data.authorization")
//@Order(101)
public class CautiousDeclareAuthRepository {

}
