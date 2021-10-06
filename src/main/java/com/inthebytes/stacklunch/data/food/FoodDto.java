package com.inthebytes.stacklunch.data.food;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class FoodDto extends StackLunchDto {

	private String foodId;
	private String name;
	private Double price;
	private String description;

	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private RestaurantDto restaurant;
	
	public static FoodDto convert(Food entity) {
		return mapper.convert(entity);
	}
	
	public Food convert() {
		return mapper.convert(this);
	}
	
	public static Page<FoodDto> convert(Page<Food> entities) {
		return entities.map(FoodDto::convert);
	}

}
