package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_IS_PICKED database table.
 * 
 */
@Entity
@Table(name="brng_lkp_is_cancelled")
@NamedQuery(name="BrngLkpIsCancelled.findAll", query="SELECT b FROM BrngLkpIsCancelled b")
public class BrngLkpIsCancelled extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="is_cancelled", nullable=false, length=1)
	private String isCancelled;

	//bi-directional many-to-one association to BrngOrder
	@OneToMany(mappedBy="brnglkpiscancelled")
	private List<BrngOrder> brngOrders;

	public BrngLkpIsCancelled() {
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

	public BrngOrder addBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().add(brngOrder);
		//brngOrder.setBrngLkpIsPicked(this);

		return brngOrder;
	}

	public BrngOrder removeBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().remove(brngOrder);
		//brngOrder.setBrngLkpIsPicked(null);

		return brngOrder;
	}

	public String getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

}