package com.inthebytes.stacklunch.data;

import org.mapstruct.factory.Mappers;

public abstract class StackLunchDto {

	public static StackLunchMapper getMapper() {
		return Mappers.getMapper(StackLunchMapper.class);
	}
}
