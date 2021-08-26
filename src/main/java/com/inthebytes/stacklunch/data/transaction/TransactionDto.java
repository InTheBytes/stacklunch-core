package com.inthebytes.stacklunch.data.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.inthebytes.stacklunch.data.StackLunchDto;
import com.inthebytes.stacklunch.data.order.OrderDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class TransactionDto extends StackLunchDto {
	
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

}
