package com.inthebytes.stacklunch;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class StackLunchApplication {
	
	private StackLunchApplication() {}
	
	public static ConfigurableApplicationContext run(Class<?> app, String[] args) {
		SpringApplication application = new SpringApplication(UniversalStackLunchConfiguration.class, StackLunchDataSource.class, app);
		Properties props = new Properties();
		props.putAll(propertiesMap());
		application.setDefaultProperties(props);
		application.setBanner(new StackLunchBanner());
		return application.run(args);
	}
	
	private static Map<String, String> propertiesMap() {
		Map<String, String> props = new HashMap<>(3);
		props.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQL8Dialect");
		props.put("spring.autoconfigure.exclude", "org.springframework.boot.autoconfigure.jdbx.DataSourceAutoConfiguration");
		props.put("spring.jpa.properties.hibernate.jdbc.time_zone", "UTC");
		return props;
	}
}
