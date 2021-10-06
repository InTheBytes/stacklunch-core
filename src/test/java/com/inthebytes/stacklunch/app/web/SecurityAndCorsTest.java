package com.inthebytes.stacklunch.app.web;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.inthebytes.stacklunch.app.testapp.TestApplicationConfig;
import com.inthebytes.stacklunch.app.testapp.TestController;
import com.inthebytes.stacklunch.app.testapp.TestSecurityConfig;
import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;
import com.inthebytes.stacklunch.security.JwtProperties;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = {TestApplicationConfig.class, TestController.class, TestSecurityConfig.class, JwtProperties.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
class SecurityAndCorsTest {
	
	@Autowired
	AuthorizationRepository authRepo;
	
	@Autowired
	MockMvc mock;
	
	private static String makeJwtToken(String username, String role) {
		return makeJwtWithDate(username, role, new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME));
	}
	
	public static String makeJwtWithDate(String username, String role, Date expiration) {
		return makeJwtWithDateAndSecret(username, role, expiration, JwtProperties.SECRET.getBytes());
	}
	
	public static String makeJwtWithDateAndSecret(String username, String role, Date expiration, byte[] secret) {
		role = (role != null) ? "ROLE_" + role.toUpperCase() : role; 
		return JwtProperties.TOKEN_PREFIX + JWT.create()
			.withSubject(username)
			.withClaim(JwtProperties.AUTHORITIES_KEY, role)
			.withExpiresAt(expiration)
			.sign(Algorithm.HMAC512(secret));
	}
	
	@Test
	void testCorsConfigurations() throws Exception {
		mock.perform(options("/tests/any")
				.header("Access-Control-Request-Method", "GET")
				.header("Origin", "https://not-our-website.com")
				).andExpect(status().isForbidden());
		
		mock.perform(options("/tests/any")
				.header("Access-Control-Request-Method", "GET")
				.header("Origin", "https://stacklunch.com")
				).andExpect(status().isOk());
	}
	
	@Test
	void testPermitAll() throws Exception {
		mock.perform(get("/tests/any"))
			.andExpect(status().isOk())
			.andExpect(content().string("Permit All Works"));
		
		mock.perform(
				get("/tests/any")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "A"))
				).andExpect(status().isOk())
				.andExpect(content().string("Permit All Works"));
	}
	
	@Test
	void testAuthenticated() throws Exception {
		mock.perform(get("/tests/users")).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "customer"))
				).andExpect(status().isOk())
		.andExpect(content().string("Authenticated Works"));
	}
	
	@Test
	void testAdminEndpoint() throws Exception {
		mock.perform(get("/tests/admins")).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/admins")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "customer"))
				).andExpect(status().isForbidden());
		
		mock.perform(get("/tests/admins")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "admin"))
				).andExpect(status().isOk())
			.andExpect(content().string("Has Role Admin Works"));
	}
	
	@Test
	void testCustomerEndpoint() throws Exception {
		mock.perform(get("/tests/customers")).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/customers")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "admin"))
				).andExpect(status().isForbidden());
		
		mock.perform(get("/tests/customers")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "customer"))
				).andExpect(status().isOk())
			.andExpect(content().string("Has Role Customer Works"));
	}
	
	@Test
	void testDriverEndpoint() throws Exception {
		mock.perform(get("/tests/drivers")).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/drivers")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "restaurant"))
				).andExpect(status().isForbidden());
		
		mock.perform(get("/tests/drivers")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "driver"))
				).andExpect(status().isOk())
			.andExpect(content().string("Has Role Driver Works"));
	}
	
	@Test
	void testRestaurantEndpoint() throws Exception {
		mock.perform(get("/tests/restaurants")).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/restaurants")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "driver"))
				).andExpect(status().isForbidden());
		
		mock.perform(get("/tests/restaurants")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", "restaurant"))
				).andExpect(status().isOk())
			.andExpect(content().string("Has Role Restaurant Works"));
	}
	
	@Test
	void testRequestAttributesFromToken() throws Exception {
		mock.perform(get("/tests/details")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test-driver", "driver"))
				).andExpect(status().isOk())
			.andExpect(content().string("Username: test-driver, Role: driver"));
		
		mock.perform(get("/tests/details")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test-admin", "admin"))
				).andExpect(status().isOk())
			.andExpect(content().string("Username: test-admin, Role: admin"));
		
		mock.perform(get("/tests/details")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test-customer", "customer"))
				).andExpect(status().isOk())
			.andExpect(content().string("Username: test-customer, Role: customer"));
		
		mock.perform(get("/tests/details")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test-restaurant", "restaurant"))
				).andExpect(status().isOk())
			.andExpect(content().string("Username: test-restaurant, Role: restaurant"));
	}

	
	@Test
	void shouldReturnUnauthorizedForLoggedOutTokens() throws Exception {
		Authorization loggedOutToken = new Authorization();
		loggedOutToken.setToken("logged-out-token");
		loggedOutToken.setExpirationDate(new Timestamp(System.currentTimeMillis()));
		
		authRepo.save(loggedOutToken);
		
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + "logged-out-token")
				).andExpect(status().isUnauthorized());
	}

	@Test
	void shouldReturnUnauthorizedForExpiredTokens() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, 
						SecurityAndCorsTest.makeJwtWithDate("test-user", "test", new Date(System.currentTimeMillis() - 1000)))
				).andExpect(status().isUnauthorized());
	}
	
	@Test
	void shouldReturnUnauthorizedWithBadSecret() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, 
						SecurityAndCorsTest.makeJwtWithDateAndSecret(
								"test-user", 
								"test", 
								new Date(System.currentTimeMillis() - 1000),
								"BadSecret".getBytes()))
				).andExpect(status().isUnauthorized());
	}
	
	@Test
	void testAuthenticationWithNullRoleOrUsername() throws Exception {
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, makeJwtToken("test", null))
				).andExpect(status().isUnauthorized());
		
		mock.perform(get("/tests/users")
				.header(JwtProperties.HEADER_STRING, makeJwtToken(null, "customer"))
				).andExpect(status().isUnauthorized());
	}
}
