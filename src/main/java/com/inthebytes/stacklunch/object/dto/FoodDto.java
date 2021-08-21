package com.inthebytes.stacklunch.object.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.Food;
import com.inthebytes.stacklunch.object.entity.Restaurant;

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
	private Restaurant restaurant;
	
	public static FoodDto convert(Food entity) {
		return getMapper().convert(entity);
	}
	
	public Food convert() {
		return getMapper().convert(this);
	}

}
