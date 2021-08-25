package com.inthebytes.stacklunch;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.inthebytes.stacklunch.security.CautiousDeclareAuthRepository;
import com.inthebytes.stacklunch.security.JwtProperties;


@Configuration
@EntityScan("com.inthebytes.stacklunch.data")
//@Import(CautiousDeclareAuthRepository.class)
public class UniversalStackLunchConfiguration {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOriginPatterns("https://*.stacklunch.com", "https://stacklunch.com")
					.exposedHeaders(JwtProperties.HEADER_STRING)
					.allowCredentials(true)
					.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}
}
