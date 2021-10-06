package com.inthebytes.stacklunch.data.order;

import com.inthebytes.stacklunch.data.food.FoodDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class OrderFoodDto {
	
	private OrderFoodKey id;
	private FoodDto food;
	private OrderDto order;
	private Integer quantity;

}
