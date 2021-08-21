package com.inthebytes.stacklunch.object.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Confirmation implements Serializable {
	
	private static final long serialVersionUID = -550336738128222594L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String tokenId;

	private String confirmationToken;
	private Timestamp createdDate;
	private Boolean isConfirmed;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
