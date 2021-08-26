package com.inthebytes.stacklunch.data.user;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.role.RoleDto;

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
	
	public User convert() {
		return getMapper().convert(this);
	}
	
	public static Page<UserDto> convert(Page<User> entities) {
		return entities.map((x) -> convert(x));
	}
}
