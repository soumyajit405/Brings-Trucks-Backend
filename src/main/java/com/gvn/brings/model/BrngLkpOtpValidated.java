package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_lkp_otp_validated")
@NamedQuery(name="BrngLkpOtpValidated.findAll", query="SELECT b FROM BrngLkpOtpValidated b")
public class BrngLkpOtpValidated extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	private String description;

	@Column(name="code", nullable=false, length=1)
	private String code;

	public BrngLkpOtpValidated() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

}