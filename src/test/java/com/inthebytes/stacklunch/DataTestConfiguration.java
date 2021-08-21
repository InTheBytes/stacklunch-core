package com.inthebytes.stacklunch;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.inthebytes.stacklunch.repository")
public class DataTestConfiguration {
	

}
