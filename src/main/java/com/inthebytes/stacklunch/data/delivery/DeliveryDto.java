package com.inthebytes.stacklunch.data.delivery;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.driver.DriverDto;
import com.inthebytes.stacklunch.data.order.OrderDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DeliveryDto {

	private String deliveryId;
	private OrderDto order;
	private DriverDto driver;
	private Timestamp startTime;
	private Timestamp pickupTime;
	private Timestamp endTime;
	
	public static DeliveryDto convert(Delivery entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Delivery convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<DeliveryDto> convert(Page<Delivery> entities) {
		return entities.map(DeliveryDto::convert);
	}
}
