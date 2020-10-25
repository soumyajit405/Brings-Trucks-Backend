package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_IS_PAID database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_IS_PAID")
@NamedQuery(name="BrngLkpIsPaid.findAll", query="SELECT b FROM BrngLkpIsPaid b")
public class BrngLkpIsPaid extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="is_paid", nullable=false, length=1)
	private String isPaid;

	//bi-directional many-to-one association to BrngOrder
	@OneToMany(mappedBy="brngLkpIsPaid")
	private List<BrngOrder> brngOrders;

	public BrngLkpIsPaid() {
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

	public String getIsPaid() {
		return this.isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public List<BrngOrder> getBrngOrders() {
		return this.brngOrders;
	}

	public void setBrngOrders(List<BrngOrder> brngOrders) {
		this.brngOrders = brngOrders;
	}

	public BrngOrder addBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().add(brngOrder);
		brngOrder.setBrngLkpIsPaid(this);

		return brngOrder;
	}

	public BrngOrder removeBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().remove(brngOrder);
		brngOrder.setBrngLkpIsPaid(null);

		return brngOrder;
	}

}