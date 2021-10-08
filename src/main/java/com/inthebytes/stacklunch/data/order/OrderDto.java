package com.inthebytes.stacklunch.data.order;

import java.util.Set;

import org.springframework.data.domain.Page;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends BaseOrderDetails {
	
	private Set<OrderFoodDto> foods;
	
	public static OrderDto convert(Order entity) {
		return mapper.convert(entity);
	}
	
	public Order convert() {
		return mapper.convert(this);
	}
	
	public static Page<OrderDto> convert(Page<Order> entities) {
		return entities.map(OrderDto::convert);
	}
}
