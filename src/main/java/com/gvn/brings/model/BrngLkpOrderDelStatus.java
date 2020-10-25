package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_ORDER_DEL_STATUS database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_ORDER_DEL_STATUS")
@NamedQuery(name="BrngLkpOrderDelStatus.findAll", query="SELECT b FROM BrngLkpOrderDelStatus b")
public class BrngLkpOrderDelStatus extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="del_status", nullable=false, length=45)
	private String delStatus;

	@Column(nullable=false, length=45)
	private String description;

	//bi-directional many-to-one association to BrngOrderDelivery
	@OneToMany(mappedBy="brngLkpOrderDelStatus")
	private List<BrngOrderDelivery> brngOrderDeliveries;

	public BrngLkpOrderDelStatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BrngOrderDelivery> getBrngOrderDeliveries() {
		return this.brngOrderDeliveries;
	}

	public void setBrngOrderDeliveries(List<BrngOrderDelivery> brngOrderDeliveries) {
		this.brngOrderDeliveries = brngOrderDeliveries;
	}

	public BrngOrderDelivery addBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().add(brngOrderDelivery);
		brngOrderDelivery.setBrngLkpOrderDelStatus(this);

		return brngOrderDelivery;
	}

	public BrngOrderDelivery removeBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().remove(brngOrderDelivery);
		brngOrderDelivery.setBrngLkpOrderDelStatus(null);

		return brngOrderDelivery;
	}

}