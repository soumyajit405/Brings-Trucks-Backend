package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_delivery_executive_web")
@NamedQuery(name="BrngDeliveryExecutiveFromWeb.findAll", query="SELECT b FROM BrngDeliveryExecutiveFromWeb b")
public class BrngDeliveryExecutiveFromWeb extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	@Column(name="name", nullable=false, length=100)
	private String name;
	
	@Column(name="phone", nullable=false, length=45)
	private String phone;
	
	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngDeliveryExecutiveFromWeb() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	
}