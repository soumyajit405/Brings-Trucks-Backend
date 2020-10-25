package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_general_lkp")
@NamedQuery(name="BrngGeneralLkp.findAll", query="SELECT b FROM BrngGeneralLkp b")
public class BrngGeneralLkp extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	@Column(name="brng_key", nullable=false, length=50)
	private String brngKey;
	
	@Column(name="value", nullable=false, length=50)
	private String value;
	
	public String getBrngKey() {
		return brngKey;
	}

	public void setBrngKey(String brngKey) {
		this.brngKey = brngKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngGeneralLkp() {
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

	

	
}