package com.inthebytes.stacklunch.data.delivery;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.driver.DriverDto;
import com.inthebytes.stacklunch.data.order.OrderDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DeliveryDto extends StackLunchDto {

	private String deliveryId;
	private OrderDto order;
	private DriverDto driver;
	private Timestamp startTime;
	private Timestamp pickupTime;
	private Timestamp deliverTime;
	
	public static DeliveryDto convert(Delivery entity) {
		return mapper.convert(entity);
	}
	
	public Delivery convert() {
		return mapper.convert(this);
	}
	
	public static Page<DeliveryDto> convert(Page<Delivery> entities) {
		return entities.map(DeliveryDto::convert);
	}
}
