package com.inthebytes.stacklunch.data.driver;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DriverDto extends StackLunchDto {
	
	private String driverId;
	private UserDto user;
	private String vehicleId;
	private String financialId;
	private Integer status;
	
	public static DriverDto convert(Driver entity) {
		return getMapper().convert(entity);
	}
	
	public Driver convert() {
		return getMapper().convert(this);
	}
	
	public static Page<DriverDto> convert(Page<Driver> entities) {
		return entities.map((x) -> convert(x));
	}

}
