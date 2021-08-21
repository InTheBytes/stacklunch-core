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
	
	ConfirmationDto convert(Confirmation confirmation);
	
	LocationDto convert(Location location);
	Location convert(LocationDto location);
	
	FoodDto convert(Food location);
	Food convert(FoodDto food);
	
	RestaurantDto convert(Restaurant restaurant);
	Restaurant convert(RestaurantDto restaurant);
}
