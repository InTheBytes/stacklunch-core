package com.inthebytes.stacklunch.object.dto;

import java.sql.Timestamp;

import com.inthebytes.stacklunch.object.StackLunchDto;
import com.inthebytes.stacklunch.object.entity.Confirmation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ConfirmationDto extends StackLunchDto {

	private String tokenId;
	private String confirmationToken;
	private Timestamp createdDate;
	private Boolean isConfirmed;
	private UserDto user;
	
	public static ConfirmationDto convert(Confirmation entity) {
		return getMapper().convert(entity);
	}
}
