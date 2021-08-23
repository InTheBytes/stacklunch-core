package com.inthebytes.stacklunch;

import java.util.LinkedList;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface StackLunchApplication {
	
	default ApplicationContext run(String[] args) {
		List<Class<?>> beans = new LinkedList<Class<?>>(
				Arrays.asList(
						this.getClass(), UniversalStackLunchConfiguration.class)
				);
		SpringApplication application = new SpringApplication((Class<?>[]) beans.toArray());
		Properties props = new Properties();
		props.putAll(propertiesMap());
		props.put("stacklunch.jwt.expire-time", 3600000 * 2);
		application.setDefaultProperties(props);
		application.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				out.print(StackLunchBanner.logoBanner() + StackLunchBanner.titleBanner());
			}
		});
		return application.run(args);
	}
	
	default Map<String, String> propertiesMap() {
		Map<String, String> props = new HashMap<String, String>(10);
		props.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQL8Dialect");
		props.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
		props.put("spring.datasource.name", "stacklunch");
		props.put("spring.datasource.url", System.getenv("SL_DB_URL"));
		props.put("spring.datasource.username", System.getenv("SL_DB_USER"));
		props.put("spring.datasource.password", System.getenv("SL_DB_PASS"));
		props.put("springdoc.api-docs.path", microserviceEndpoint() + "/v3/api-docs");
		props.put("stacklunch.email.sender", System.getenv("SL_EMAIL"));
		props.put("stacklunch.domain.protocol", System.getenv("SL_DOMAIN_PROTOCOL"));
		props.put("stacklunch.domain.host", System.getenv("SL_DOMAIN_HOST"));
		props.put("stacklunch.base-endpoint", microserviceEndpoint());
		props.put("stacklunch.jwt.secret", System.getenv("SL_SECRET"));
		props.put("stacklunch.jwt.authorities-key", "InBytesAuth");
		props.put("stacklunch.jwt.header", "Authentication");
		props.put("stacklunch.jwt.prefix", "Bearer ");
		return props;
	}
	
	public String microserviceEndpoint();
	
	public HttpSecurity configureEndpointSecurity(HttpSecurity http);
}
