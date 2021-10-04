package com.inthebytes.stacklunch.data.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.data.domain.Page;

import com.inthebytes.stacklunch.data.StackLunchDtoMapper;
import com.inthebytes.stacklunch.data.order.OrderDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TransactionDto {
	
	private String transactionId;
	private OrderDto order;
	private String stripeId;
	private BigDecimal subtotal;
	private BigDecimal tax;
	private BigDecimal fee;
	private BigDecimal discount;
	private BigDecimal tip;
	private BigDecimal total;
	private Timestamp paymentTime;
	

	public static TransactionDto convert(Transaction entity) {
		return StackLunchDtoMapper.mapper.convert(entity);
	}
	
	public Transaction convert() {
		return StackLunchDtoMapper.mapper.convert(this);
	}
	
	public Page<TransactionDto> convert(Page<Transaction> entities) {
		return entities.map(TransactionDto::convert);
	}

}
