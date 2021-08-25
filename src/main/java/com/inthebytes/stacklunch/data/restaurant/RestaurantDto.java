package com.inthebytes.stacklunch.data.restaurant;

import java.util.List;
import java.util.Set;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.user.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class RestaurantDto extends StackLunchDto {
	
	private String restaurantId;
	private String name;
	private String cuisine;

	private Location location;
	private List<Food> foods;
	private Set<User> manager;
	
	public static RestaurantDto convert(Restaurant entity) {
		return getMapper().convert(entity);
	}
	
	public Restaurant convert() {
		return getMapper().convert(this);
	}
}
