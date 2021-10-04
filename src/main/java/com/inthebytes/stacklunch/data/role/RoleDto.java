package com.inthebytes.stacklunch.data.role;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleDto {
	
	private Integer roleId;
	private String name;
	
	public static RoleDto convert(Role entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Role convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<RoleDto> convert(Page<Role> entities) {
		return entities.map(RoleDto::convert);
	}

}
