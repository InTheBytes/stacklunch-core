package com.inthebytes.stacklunch.data;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationDto;
import com.inthebytes.stacklunch.data.confirmation.Confirmation;
import com.inthebytes.stacklunch.data.confirmation.ConfirmationDto;
import com.inthebytes.stacklunch.data.delivery.Delivery;
import com.inthebytes.stacklunch.data.delivery.DeliveryDto;
import com.inthebytes.stacklunch.data.driver.Driver;
import com.inthebytes.stacklunch.data.driver.DriverDto;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderDto;
import com.inthebytes.stacklunch.data.order.OrderFood;
import com.inthebytes.stacklunch.data.order.OrderFoodDto;
import com.inthebytes.stacklunch.data.passchange.PasswordChange;
import com.inthebytes.stacklunch.data.passchange.PasswordChangeDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.transaction.Transaction;
import com.inthebytes.stacklunch.data.transaction.TransactionDto;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRegistrationDto;

@Mapper
public interface StackLunchMapper {
	
	RoleDto convert(Role role);
	Role convert(RoleDto role);
	
	UserDto convert(User user);
	User convert(UserRegistrationDto user);
	
	@Mapping(target = "password", ignore = true)
	User convert(UserDto user);

	AuthorizationDto convert(Authorization authorization);
	Authorization convert(AuthorizationDto authorization);
	
	PasswordChangeDto convert(PasswordChange passwordChange);
	PasswordChange convert(PasswordChangeDto passwordChange);
	
	ConfirmationDto convert(Confirmation confirmation);
	Confirmation convert(ConfirmationDto confirmation);
	
	LocationDto convert(Location location);
	Location convert(LocationDto location);
	
	FoodDto convert(Food location);
	Food convert(FoodDto food);
	
	RestaurantDto convert(Restaurant restaurant);
	Restaurant convert(RestaurantDto restaurant);
	
	@Mapping(target = "order", ignore = true)
	@Mapping(target = "id", ignore = true)
	OrderFood convert(OrderFoodDto orderFood);
	
	@Mapping(target = "orderId", source = "id.orderId")
	OrderFoodDto convert(OrderFood orderFood);
	
	OrderDto convert(Order order);
	Order convert(OrderDto order);
	
	DeliveryDto convert(Delivery delivery);
	Delivery convert(DeliveryDto delivery);
	
	DriverDto convert(Driver driver);
	Driver convert(DriverDto driver);
	
	TransactionDto convert(Transaction transaction);
	Transaction convert(TransactionDto transaction);
}
