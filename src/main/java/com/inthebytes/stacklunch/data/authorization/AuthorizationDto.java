package com.inthebytes.stacklunch.data.authorization;

import java.sql.Timestamp;

import com.inthebytes.stacklunch.data.StackLunchDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthorizationDto extends StackLunchDto {
	
	private String token;
	private Timestamp expirationDate;
	
	public static AuthorizationDto convert(Authorization entity) {
		return getMapper().convert(entity);
	}
	
	public Authorization convert() {
		return getMapper().convert(this);
	}

}
