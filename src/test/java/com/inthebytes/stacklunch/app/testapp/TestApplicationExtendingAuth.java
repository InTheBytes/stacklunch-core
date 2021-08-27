package com.inthebytes.stacklunch.app.testapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@SpringBootConfiguration
@Import(UniversalStackLunchConfiguration.class)
@EnableJpaRepositories(basePackageClasses = ExtendAuthRepo.class)
@EnableAutoConfiguration
public class TestApplicationExtendingAuth {

}
