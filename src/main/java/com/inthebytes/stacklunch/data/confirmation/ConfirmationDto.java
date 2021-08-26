package com.inthebytes.stacklunch.data.confirmation;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.user.UserDto;

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
	
	public Confirmation convert() {
		return getMapper().convert(this);
	}
	
	public static Page<ConfirmationDto> convert(Page<Confirmation> entities) {
		return entities.map((x) -> convert(x));
	}
}
