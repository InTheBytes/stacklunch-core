package com.inthebytes.stacklunch.object.dto;

import java.util.List;
import java.util.Set;

import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.Food;
import com.inthebytes.stacklunch.object.entity.Location;
import com.inthebytes.stacklunch.object.entity.Restaurant;
import com.inthebytes.stacklunch.object.entity.User;

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
