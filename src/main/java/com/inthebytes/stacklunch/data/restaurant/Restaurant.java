package com.inthebytes.stacklunch.data.restaurant;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.location.Location;
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
public class Restaurant implements Serializable {
	
	private static final long serialVersionUID = -7484864127087947004L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String restaurantId;

	private String name;
	private String cuisine;

	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Food> foods;
	
	@OneToMany
	@JoinTable(name = "manager",
			joinColumns = {@JoinColumn(name = "restaurant_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<User> manager;

}
