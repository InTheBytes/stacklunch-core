package com.inthebytes.stacklunch.data.order;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class OrderFoodKey implements Serializable {

	private static final long serialVersionUID = -3134339846569940109L;

	private String orderId;
	private String foodId;
}
