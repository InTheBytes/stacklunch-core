package com.inthebytes.stacklunch.data.restaurant;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RestaurantDto {
	
	private String restaurantId;
	private String name;
	private String cuisine;

	private LocationDto location;
	private List<FoodDto> foods;
	private Set<UserDto> manager;
	
	public static RestaurantDto convert(Restaurant entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Restaurant convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<RestaurantDto> convert(Page<Restaurant> entities) {
		return entities.map(RestaurantDto::convert);
	}
}
