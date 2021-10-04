package com.inthebytes.stacklunch.data;

import org.mapstruct.factory.Mappers;

public abstract class StackLunchDtoMapper {
	
	public static final StackLunchMapper mapper = Mappers.getMapper(StackLunchMapper.class);
	
	private StackLunchDtoMapper() {}
}
