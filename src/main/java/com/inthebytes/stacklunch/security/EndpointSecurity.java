package com.inthebytes.stacklunch.security;

import org.springframework.http.HttpMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointSecurity {
	
	public enum SecurityPermission {
		PermitAll, Authenticated,
		Customer, Driver, RestaurantManager, Admin
	}
	
	private String endpoint;
	
	private HttpMethod methods;
}
