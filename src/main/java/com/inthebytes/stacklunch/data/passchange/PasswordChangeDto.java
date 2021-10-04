package com.inthebytes.stacklunch.data.passchange;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PasswordChangeDto {
	
	private String confirmationToken;
	private Timestamp createdTime;
	private UserDto user;
	
	public static PasswordChangeDto convert(PasswordChange entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public PasswordChange convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<PasswordChangeDto> convert(Page<PasswordChange> entities) {
		return entities.map(PasswordChangeDto::convert);
	}
}
