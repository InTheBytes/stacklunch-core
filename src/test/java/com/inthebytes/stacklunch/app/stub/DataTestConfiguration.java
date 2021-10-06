package com.inthebytes.stacklunch.app.stub;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.inthebytes.stacklunch.data")
@PropertySource("data.properties")
public class DataTestConfiguration {

}
