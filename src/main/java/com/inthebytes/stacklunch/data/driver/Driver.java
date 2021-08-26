package com.inthebytes.stacklunch.data.driver;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

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
public class Driver implements Serializable {
	
	private static final long serialVersionUID = 797670646248274531L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String driverId;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private String vehicleId;
	private String financialId;
	private Integer status;

}
