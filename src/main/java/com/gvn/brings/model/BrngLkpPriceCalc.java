package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_PRICE_CALC database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_PRICE_CALC")
@NamedQuery(name="BrngLkpPriceCalc.findAll", query="SELECT b FROM BrngLkpPriceCalc b")
public class BrngLkpPriceCalc extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	public BrngLkpPriceCalc() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}