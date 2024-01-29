package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity<Long> {

	@Transient
	private static final long serialVersionUID = -2392530719997944346L;

	private String firstName;

	private String lastName;

	private String emailId;

	public Customer() {

	}

	public Customer(String firstName, String lastName, String emailId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", id=" + id
				+ ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
