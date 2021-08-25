package com.inthebytes.stacklunch.data.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.inthebytes.stacklunch.data.role.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = -7738926502921359880L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String userId;

	private String username;
	private String password;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "user_role")
	private Role role;
}
