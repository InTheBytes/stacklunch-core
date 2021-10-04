package com.inthebytes.stacklunch.data.confirmation;

import java.sql.Timestamp;

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
public class ConfirmationDto {

	private String tokenId;
	private String confirmationToken;
	private Timestamp createdDate;
	private Boolean isConfirmed;
	private UserDto user;
	
	public static ConfirmationDto convert(Confirmation entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Confirmation convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public static Page<ConfirmationDto> convert(Page<Confirmation> entities) {
		return entities.map(ConfirmationDto::convert);
	}
}
