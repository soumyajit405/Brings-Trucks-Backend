package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_per_person_pricing")
@NamedQuery(name="BrngPerPersonPricing.findAll", query="SELECT b FROM BrngPerPersonPricing b")
public class BrngPerPersonPricing extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="phone_number", nullable=false, length=50)
	private String phoneNumber;
	
	@Column(name="type", nullable=false, length=50)
	private String type;
	
	@Column(name="kms", nullable=false, length=50)
	private String kms;
	
	@Column(name="value", nullable=false, length=50)
	private String value;
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BrngPerPersonPricing() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}