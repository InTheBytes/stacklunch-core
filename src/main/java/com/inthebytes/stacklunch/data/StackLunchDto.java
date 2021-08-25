package com.inthebytes.stacklunch.data;

import org.mapstruct.factory.Mappers;

public abstract class StackLunchDto {

	protected static StackLunchMapper getMapper() {
		return Mappers.getMapper(StackLunchMapper.class);
	}
}
