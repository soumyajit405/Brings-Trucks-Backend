package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_lkp_is_retry")
@NamedQuery(name="BrngLkpIsRetry.findAll", query="SELECT b FROM BrngLkpIsRetry b")
public class BrngLkpIsRetry extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	private String description;

	@Column(name="is_retry", nullable=false, length=1)
	private String isRetry;

	public BrngLkpIsRetry() {
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

	public String getIsRetry() {
		return isRetry;
	}

	public void setIsRetry(String isRetry) {
		this.isRetry = isRetry;
	}

	

	
}