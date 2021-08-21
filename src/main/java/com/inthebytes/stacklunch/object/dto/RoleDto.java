package com.inthebytes.stacklunch.object.dto;

import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends StackLunchDto {
	
	private String roleId;
	private String name;
	
	public static RoleDto convert(Role entity) {
		return getMapper().convert(entity);
	}
	
	public Role convert() {
		return getMapper().convert(this);
	}

}
