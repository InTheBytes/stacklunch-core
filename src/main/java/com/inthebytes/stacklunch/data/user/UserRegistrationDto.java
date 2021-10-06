package com.inthebytes.stacklunch.data.user;

import com.inthebytes.stacklunch.data.StackLunchDto;

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
		return StackLunchDto.mapper.convert(this);
	}
}
