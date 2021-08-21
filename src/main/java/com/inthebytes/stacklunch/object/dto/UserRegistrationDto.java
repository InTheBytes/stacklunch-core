package com.inthebytes.stacklunch.object.dto;

import com.inthebytes.stacklunch.object.entity.User;

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

	public User convert() {
		return getMapper().convert(this);
	}
}
