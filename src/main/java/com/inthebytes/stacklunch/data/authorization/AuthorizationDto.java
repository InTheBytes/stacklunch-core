package com.inthebytes.stacklunch.data.authorization;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthorizationDto {
	
	private String token;
	private Timestamp expirationDate;
	
	public static AuthorizationDto convert(Authorization entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Authorization convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}

	public static Page<AuthorizationDto> convert(Page<Authorization> entities) {
		return entities.map(AuthorizationDto::convert);
	}
}
