package com.inthebytes.stacklunch.data.authorization;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Authorization implements Serializable {
	
	private static final long serialVersionUID = 2243713327052003657L;
	
	@Id
	private String token;

	private Timestamp expirationDate;
}
