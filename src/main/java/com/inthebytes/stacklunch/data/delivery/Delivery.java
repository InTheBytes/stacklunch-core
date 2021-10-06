package com.inthebytes.stacklunch.data.delivery;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inthebytes.stacklunch.data.driver.Driver;
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
@Table(name = "delivery")
public class Delivery implements Serializable {
	
	private static final long serialVersionUID = 1397476994485720096L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String deliveryId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;
	
	private Timestamp startTime;
	private Timestamp pickupTime;
	private Timestamp deliverTime;

}
