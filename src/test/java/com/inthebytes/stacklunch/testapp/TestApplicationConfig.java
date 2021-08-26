package com.inthebytes.stacklunch.testapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@Configuration
@ComponentScan
@Import(UniversalStackLunchConfiguration.class)
public class TestApplicationConfig {

}
