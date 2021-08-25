package com.inthebytes.stacklunch.data.order;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inthebytes.stacklunch.data.food.Food;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class OrderFood implements Serializable {

	public OrderFood() {
		super();
		id = new OrderFoodKey();
	}

	private static final long serialVersionUID = 4793574294278895405L;

	@EmbeddedId
	private OrderFoodKey id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JsonIgnore
	@MapsId("orderId")
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne 
	@MapsId("foodId")
	@JoinColumn(name = "food_id")
	private Food food;
	
	private Integer quantity;
}
