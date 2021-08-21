package com.inthebytes.stacklunch.object.dto;

import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.Location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class LocationDto extends StackLunchDto {

	private String locationId;
	private String street;
	private String unit;
	private String city;
	private String state;
	private Integer zipCode;
	
	public static LocationDto convert(Location entity) {
		return getMapper().convert(entity);
	}
	
	public Location convert() {
		return getMapper().convert(this);
	}
}
