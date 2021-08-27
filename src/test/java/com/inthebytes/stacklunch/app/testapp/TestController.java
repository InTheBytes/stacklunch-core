package com.inthebytes.stacklunch.app.testapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {
	
	private ResponseEntity<String> makeResponse(String message) {
		return ResponseEntity.ok(message);
	}

	@GetMapping("/any")
	public ResponseEntity<String> testPermitAll() {
		return makeResponse("Permit All Works");
	}
	
	@GetMapping("/users")
	public ResponseEntity<String> testAuthenticated() {
		return makeResponse("Authenticated Works");
	}
	
	@GetMapping("/details")
	public ResponseEntity<String> testRequestAttributes(
			@RequestAttribute String username,
			@RequestAttribute String role
			) {
		return makeResponse("Username: " + username + ", Role: " + role);
	}
	
	@GetMapping("/admins")
	public ResponseEntity<String> testHasRoleAdmin() {
		return makeResponse("Has Role Admin Works");
	}
	
	@GetMapping("/customers")
	public ResponseEntity<String> testHasRoleCustomer() {
		return makeResponse("Has Role Customer Works");
	}
	
	@GetMapping("/restaurants")
	public ResponseEntity<String> testHasRoleRestaurant() {
		return makeResponse("Has Role Restaurant Works");
	}
	
	@GetMapping("/drivers")
	public ResponseEntity<String> testHasRoleDriver() {
		return makeResponse("Has Role Driver Works");
	}
}
