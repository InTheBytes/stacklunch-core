package com.inthebytes.stacklunch;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public interface StackLunchApplication {
	
	default ApplicationContext run(String[] args) {
		// List of Base Beans
		List<Class<?>> beans = new LinkedList<Class<?>>(
				Arrays.asList(
						this.getClass()
						, UniversalStackLunchConfiguration.class
						)
				);
		
		if (this.integratesEmailNotification()) {
			//TODO: Add Email Service bean
		}
		if (this.integratesTextNotification()) {
			//TODO: Add Text Messaging Service bean
		}
		
		//TODO: Add appropriate web security beans 
		beans.addAll((this.implementsUserLogin() ? 
				null : null));
		
		//Add Common Properties and Run Application
		return addPropertiesAndRun(new SpringApplication((Class<?>[]) beans.toArray()), args);
	}
	
	default ApplicationContext addPropertiesAndRun(SpringApplication application, String[] args) {
		Properties props = new Properties();
		props.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQL8Dialect");
		props.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
		props.put("spring.datasource.name", "stacklunch");
		props.put("spring.datasource.driver.url", System.getenv("SL_DB_URL"));
		props.put("spring.datasource.username", System.getenv("SL_DB_USER"));
		props.put("spring.datasource.password", System.getenv("SL_DB_PASS"));
		props.put("springdoc.api-docs.path", this.baseEndpoint() + "/v3/api-docs");
		application.setDefaultProperties(props);
		return application.run(args);
	}
	
	public abstract String baseEndpoint();
	
	public abstract Boolean implementsUserLogin();
	
	public abstract Boolean integratesEmailNotification();
	
	public abstract Boolean integratesTextNotification();
}
