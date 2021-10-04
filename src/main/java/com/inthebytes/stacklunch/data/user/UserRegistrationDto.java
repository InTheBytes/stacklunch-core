package com.inthebytes.stacklunch.data.user;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDto extends UserDto {
	
	private String password;

	@Override
	public User convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
}
