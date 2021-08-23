package com.inthebytes.stacklunch;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.inthebytes.stacklunch.security.authentication.StackLunchJwtProperties;

@Configuration
@EntityScan("com.inthebytes.stacklunch.object.entity")
@ComponentScan
public class UniversalStackLunchConfiguration {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOriginPatterns("https://*.stacklunch.com", "https://stacklunch.com")
					.exposedHeaders(StackLunchJwtProperties.HEADER_STRING)
					.allowCredentials(true)
					.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}
}
