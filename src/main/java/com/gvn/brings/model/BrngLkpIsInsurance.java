package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_IS_PICKED database table.
 * 
 */
@Entity
@Table(name="brng_lkp_is_insurance")
@NamedQuery(name="BrngLkpIsInsurance.findAll", query="SELECT b FROM BrngLkpIsInsurance b")
public class BrngLkpIsInsurance extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="is_insured", nullable=false, length=1)
	private String isInsured;

	//bi-directional many-to-one association to BrngOrder
	@OneToMany(mappedBy="brngLkpIsInsurance")
	private List<BrngOrder> brngOrders;

	public BrngLkpIsInsurance() {
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

	
	public List<BrngOrder> getBrngOrders() {
		return this.brngOrders;
	}

	public void setBrngOrders(List<BrngOrder> brngOrders) {
		this.brngOrders = brngOrders;
	}

	public String getIsInsured() {
		return isInsured;
	}

	public void setIsInsured(String isInsured) {
		this.isInsured = isInsured;
	}

	

}