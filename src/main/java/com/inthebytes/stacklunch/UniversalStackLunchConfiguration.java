package com.inthebytes.stacklunch;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.inthebytes.stacklunch.security.JwtProperties;


@Configuration
@EntityScan("com.inthebytes.stacklunch.data")
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
	
	@Bean
	public DataSource datasource() {
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.url(System.getenv("SL_DB_URL"))
				.username(System.getenv("SL_DB_USER"))
				.password(System.getenv("SL_DB_PASS"))
				.build();
	}
}
