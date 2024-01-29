package com.example.demo.domain;

import javax.persistence.Transient;

public class OrderLine extends BaseEntity<Long> {

	@Transient
	private static final long serialVersionUID = -1673997061623857641L;

}
