package com.inthebytes.stacklunch.app.web;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.stacklunch.app.testapp.ExtendAuthRepo;
import com.inthebytes.stacklunch.app.testapp.TestApplicationConfig;
import com.inthebytes.stacklunch.app.testapp.TestApplicationExtendingAuth;
import com.inthebytes.stacklunch.app.testapp.TestSecurityConfig;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;
import com.inthebytes.stacklunch.security.JwtProperties;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = {TestApplicationConfig.class, TestApplicationExtendingAuth.class, TestSecurityConfig.class})
@AutoConfigureMockMvc
// @AutoConfigureTestEntityManager
// @EnableAutoConfiguration
public class SecurityAuthorizationCasesTest {
	
	@Autowired
	List<AuthorizationRepository> repos;
	
	@Autowired
	MockMvc mock;
	
	@Autowired
	ConfigurableApplicationContext context;
	
	@Test
	public void testShouldNotHaveMoreThanOneAuthRepo() {
		assertEquals(1, repos.size());
		assertThatCode(() -> context.getBean(ExtendAuthRepo.class)).doesNotThrowAnyException();
	}
	
	@Test
	public void shouldReturnUnauthorizedForLoggedOutTokens() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, "Bearer logged-out-token")
				).andExpect(status().isUnauthorized());
	}

	@Test
	public void shouldReturnUnauthorizedForExpiredTokens() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, 
						SecurityAndCorsTest.makeJwtWithDate("test-user", "test", new Date(System.currentTimeMillis() - 1000)))
				).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void shouldReturnUnauthorizedWithBadSecret() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, 
						SecurityAndCorsTest.makeJwtWithDateAndSecret(
								"test-user", 
								"test", 
								new Date(System.currentTimeMillis() - 1000),
								"BadSecret".getBytes()))
				).andExpect(status().isUnauthorized());
	}
}
