package com.inthebytes.stacklunch.data.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.inthebytes.stacklunch.data.order.Order;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 352810048908776558L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String transactionId;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	private String stripeId;
	private BigDecimal subtotal;
	private BigDecimal tax;
	private BigDecimal fee;
	private BigDecimal discount;
	private BigDecimal tip;
	private BigDecimal total;
	private Timestamp paymentTime;

}
