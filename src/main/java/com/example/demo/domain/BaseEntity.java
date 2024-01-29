package com.example.demo.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	@Transient
	private static final long serialVersionUID = -7281713842303095398L;

	public static final String ID_C_NAME = "ID";
	public static final String VERSION_C_NAME = "VERSION";

	@Id
	@Column(name = ID_C_NAME, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	protected ID id;

	@Version
	@Column(name = VERSION_C_NAME, nullable = false)
	private Long version;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	protected Instant createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	protected Instant modifiedAt;

	

}
