package com.inthebytes.stacklunch;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StackLunchDataSource {
	
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
