package com.inthebytes.stacklunch.data.location;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;

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
		return mapper.convert(entity);
	}
	
	public Location convert() {
		return mapper.convert(this);
	}
	
	public static Page<LocationDto> convert(Page<Location> entities) {
		return entities.map(LocationDto::convert);
	}
}
