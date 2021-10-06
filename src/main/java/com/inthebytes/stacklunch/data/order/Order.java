package com.inthebytes.stacklunch.data.order;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.user.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
	
	private static final long serialVersionUID = -373433364416695104L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String orderId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User customer;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "destination_id")
	private Location destination;
	
	@OneToMany(mappedBy = "order",
			cascade = CascadeType.PERSIST, 
			orphanRemoval = true)
	private Set<OrderFood> foods;
	
	private Integer status;
	private Timestamp windowStart;
	private Timestamp windowEnd;
	private String specialInstructions;
}
