package com.inthebytes.stacklunch.app.testapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@SpringBootConfiguration
@Import(value = UniversalStackLunchConfiguration.class)
public class TestApplicationConfig {

}
