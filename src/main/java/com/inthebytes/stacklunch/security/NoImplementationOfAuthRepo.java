package com.inthebytes.stacklunch.security;

import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NoImplementationOfAuthRepo implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory factory = context.getBeanFactory();
		return factory.getBeansOfType(AuthorizationRepository.class).size() == 0;
    }
}
/**
 * 
    
    
    class NoImplementationOfAuthRepo implements Condition {

		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			ConfigurableListableBeanFactory factory = context.getBeanFactory();
			return factory.getBeansOfType(AuthorizationRepository.class).size() == 0;
		}
    	
    }
 */