package com.inthebytes.stacklunch.data.driver;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DriverDto {
	
	private String driverId;
	private UserDto user;
	private String vehicleId;
	private String financialId;
	private Integer status;
	
	public static DriverDto convert(Driver entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Driver convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<DriverDto> convert(Page<Driver> entities) {
		return entities.map(DriverDto::convert);
	}

}
