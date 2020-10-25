package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_IS_PICKED database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_IS_PICKED")
@NamedQuery(name="BrngLkpIsPicked.findAll", query="SELECT b FROM BrngLkpIsPicked b")
public class BrngLkpIsPicked extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="is_picked", nullable=false, length=2)
	private String isPicked;

	//bi-directional many-to-one association to BrngOrder
	@OneToMany(mappedBy="brngLkpIsInsurance")
	private List<BrngOrder> brngOrders;

	public BrngLkpIsPicked() {
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
	
	public BrngOrder addBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().add(brngOrder);
		brngOrder.setBrngLkpIsPicked(this);

		return brngOrder;
	}

	public BrngOrder removeBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().remove(brngOrder);
		brngOrder.setBrngLkpIsPicked(null);

		return brngOrder;
	}

	public String getIsPicked() {
		return isPicked;
	}

	public void setIsPicked(String isPicked) {
		this.isPicked = isPicked;
	}

}