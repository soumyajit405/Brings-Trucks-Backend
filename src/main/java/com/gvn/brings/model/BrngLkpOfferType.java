package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_offers_type_lkp")
@NamedQuery(name="BrngLkpOfferType.findAll", query="SELECT b FROM BrngLkpOfferType b")
public class BrngLkpOfferType extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="code", nullable=false, length=45)
	private String code;
	
	@Column(name="description", nullable=false, length=45)
	private String description;

	public BrngLkpOfferType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}