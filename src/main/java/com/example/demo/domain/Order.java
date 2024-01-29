package com.example.demo.domain;

import javax.persistence.Transient;

public class Order extends BaseEntity<Long> {

	@Transient
	private static final long serialVersionUID = -8329073844562089014L;

}
