package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_payu_lkp")
@NamedQuery(name="BrngLkpPayuDetails.findAll", query="SELECT b FROM BrngLkpPayuDetails b")
public class BrngLkpPayuDetails extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="payu_key", nullable=false, length=2000)
	private String payuKey;
	
	@Column(name="payu_value", nullable=false, length=1)
	private String payuValue;

	public BrngLkpPayuDetails() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayuKey() {
		return payuKey;
	}

	public void setPayuKey(String payuKey) {
		this.payuKey = payuKey;
	}

	public String getPayuValue() {
		return payuValue;
	}

	public void setPayuValue(String payuValue) {
		this.payuValue = payuValue;
	}

	

	
}