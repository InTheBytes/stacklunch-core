package com.inthebytes.stacklunch.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("stacklunch.jwt")
public class StackLunchJwtDecoder implements JwtDecoder {
	
	private String secret;
	private String authoritiesKey;
	private String header;
	private String prefix;
	private Integer expireTime;
	
	@Override
	public Jwt decode(String token) throws JwtException {
		DecodedJWT decoded;
		try {
			decoded = JWT.require(Algorithm.HMAC512(secret))
					.build()
					.verify(token);
		} catch (JWTVerificationException e) {
			throw new JwtException("Failed to verify JWT", e);
		}
		
		return Jwt.withTokenValue(token)
				.header("typ", "JWT")
				.header("alg", "HS512")
				.subject(decoded.getSubject())
				.claim(authoritiesKey, decoded.getClaim(authoritiesKey))
				.issuedAt(decoded.getIssuedAt().toInstant())
				.issuer("stacklunch")
				.expiresAt(decoded.getExpiresAt().toInstant())
				.build();
	}
	
	public String encode(LoginUserDetails principal) {
		String authorities = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		return JWT.create().withSubject(principal.getUsername())
				.withClaim(authoritiesKey, authorities)
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
				.sign(Algorithm.HMAC512(secret.getBytes()));
	}

}
