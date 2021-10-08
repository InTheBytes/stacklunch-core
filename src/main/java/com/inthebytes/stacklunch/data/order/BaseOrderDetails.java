package com.inthebytes.stacklunch.data.order;

import java.sql.Timestamp;

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
public abstract class BaseOrderDetails extends StackLunchDto {

	private String orderId;
	private UserDto customer;
	private RestaurantDto restaurant;
	private LocationDto destination;
	private Integer status;
	private Timestamp windowStart;
	private Timestamp windowEnd;
	private String specialInstructions;
}
