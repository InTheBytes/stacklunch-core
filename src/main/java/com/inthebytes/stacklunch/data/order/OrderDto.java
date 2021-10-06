package com.inthebytes.stacklunch.data.order;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends StackLunchDto {
	
	private String orderId;
	private UserDto customer;
	private RestaurantDto restaurant;
	private LocationDto destination;
	private Set<OrderFoodDto> foods;
	private Integer status;
	private Timestamp windowStart;
	private Timestamp windowEnd;
	private String specialInstructions;
	
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
