package com.inthebytes.stacklunch.data.role;

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
public class RoleDto extends StackLunchDto {
	
	private Integer roleId;
	private String name;
	
	public static RoleDto convert(Role entity) {
		return getMapper().convert(entity);
	}
	
	public Role convert() {
		return getMapper().convert(this);
	}
	
	public static Page<RoleDto> convert(Page<Role> entities) {
		return entities.map((x) -> convert(x));
	}

}
