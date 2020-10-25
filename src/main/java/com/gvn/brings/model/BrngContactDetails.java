package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_contact_details")
@NamedQuery(name="BrngContactDetails.findAll", query="SELECT b FROM BrngContactDetails b")
public class BrngContactDetails extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	@Column(name="email", nullable=false, length=100)
	private String email;
	
	@Column(name="phone", nullable=false)
	private String phone;
	
	@Column(name="name", nullable=false)
	private String name;
	

	@Column(name="description", nullable=false)
	private String description;
	
	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngContactDetails() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}