package com.inthebytes.stacklunch.data.passchange;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PasswordChangeDto extends StackLunchDto {
	
	private String confirmationToken;
	private Timestamp createdTime;
	private UserDto user;
	
	public static PasswordChangeDto convert(PasswordChange entity) {
		return getMapper().convert(entity);
	}
	
	public PasswordChange convert() {
		return getMapper().convert(this);
	}
	
	public static Page<PasswordChangeDto> convert(Page<PasswordChange> entities) {
		return entities.map((x) -> convert(x));
	}
}