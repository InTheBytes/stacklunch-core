package com.inthebytes.stacklunch.app;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

import com.inthebytes.stacklunch.StackLunchApplication;
import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;
import com.inthebytes.stacklunch.stub.StubConfiguration;

public class StackLunchApplicationTest {
	
	private static ConfigurableApplicationContext context;

	@BeforeAll
	public static void makeContext() {
		context = StackLunchApplication.run(StubConfiguration.class, new String[] {});
	}
	
	@AfterAll
	public static void shutdown() {
		context.close();
	}
	
	@Test
	public void testPropertiesExist() {
		assertTrue(context.getEnvironment().containsProperty("spring.jpa.database-platform"));
		assertTrue(context.getEnvironment().containsProperty("spring.datasource.driver-class-name"));
		assertTrue(context.getEnvironment().containsProperty("spring.datasource.name"));
		assertTrue(context.getEnvironment().containsProperty("spring.datasource.url"));
		assertTrue(context.getEnvironment().containsProperty("spring.datasource.username"));
		assertTrue(context.getEnvironment().containsProperty("spring.datasource.password"));
		assertTrue(context.getEnvironment().containsProperty("stacklunch.email.sender"));
	}
	
	@Test
	public void testForUniversalConfiguration() {
		assertThatCode(() -> context.getBean(UniversalStackLunchConfiguration.class))
			.doesNotThrowAnyException();
		assertNotNull(context.getBean(UniversalStackLunchConfiguration.class));
	}
}
