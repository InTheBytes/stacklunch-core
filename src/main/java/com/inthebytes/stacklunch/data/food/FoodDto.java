package com.inthebytes.stacklunch.data.food;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FoodDto {

	private String foodId;
	private String name;
	private Double price;
	private String description;

	@JsonBackReference
	private Restaurant restaurant;
	
	public static FoodDto convert(Food entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Food convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<FoodDto> convert(Page<Food> entities) {
		return entities.map(FoodDto::convert);
	}

}
