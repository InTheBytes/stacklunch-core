package com.inthebytes.stacklunch.data.passchange;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
public class PasswordChange implements Serializable {
	
	private static final long serialVersionUID = -5694440634899628978L;

	@Id
	private String confirmationToken;
	
	private Timestamp createdTime;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
