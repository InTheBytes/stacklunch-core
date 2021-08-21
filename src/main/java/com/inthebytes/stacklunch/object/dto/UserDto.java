package com.inthebytes.stacklunch.object.dto;

import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDto extends StackLunchDto {

	private String userId;
	private RoleDto role;
	private String username;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private Boolean active;
	
	public static UserDto convert(User entity) {
		return getMapper().convert(entity);
	}
}
