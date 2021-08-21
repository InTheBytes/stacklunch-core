package com.inthebytes.stacklunch.object;

import org.mapstruct.Mapper;

import com.inthebytes.stacklunch.object.dto.*;
import com.inthebytes.stacklunch.object.entity.*;

@Mapper
public interface StackLunchMapper {
	
	RoleDto convert(Role role);
	Role convert(RoleDto role);
	
	UserDto convert(User user);
	User convert(UserRegistrationDto user);

	AuthorizationDto convert(Authorization authorization);
	Authorization convert(AuthorizationDto authorization);
	
	PasswordChangeDto convert(PasswordChange passwordChange);
	PasswordChange convert(PasswordChangeDto passwordChange);
	
	ConfirmationDto convert(Confirmation confirmation);
	Confirmation convert(ConfirmationDto confirmation);

}
