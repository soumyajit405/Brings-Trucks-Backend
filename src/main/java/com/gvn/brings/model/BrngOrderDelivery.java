package com.gvn.brings.model;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_ORDER_DELIVERY database table.
 * 
 */
@Entity
@Table(name="BRNG_ORDER_DELIVERY")
@NamedQuery(name="BrngOrderDelivery.findAll", query="SELECT b FROM BrngOrderDelivery b")
public class BrngOrderDelivery extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="transaction_id", nullable=false, length=45)
	private String transactionId;

	//bi-directional many-to-one association to BrngOrder
	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	private BrngOrder brngOrder;

	//bi-directional many-to-one association to BrngLkpOrderDelStatus
	@ManyToOne
	@JoinColumn(name="order_del_status_id", nullable=false)
	private BrngLkpOrderDelStatus brngLkpOrderDelStatus;

	//bi-directional many-to-one association to BrngUsrLogin
	@ManyToOne
	@JoinColumn(name="usr_login_id", nullable=false)
	private BrngUsrLogin brngUsrLogin;

	public BrngOrderDelivery() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BrngOrder getBrngOrder() {
		return this.brngOrder;
	}

	public void setBrngOrder(BrngOrder brngOrder) {
		this.brngOrder = brngOrder;
	}

	public BrngLkpOrderDelStatus getBrngLkpOrderDelStatus() {
		return this.brngLkpOrderDelStatus;
	}

	public void setBrngLkpOrderDelStatus(BrngLkpOrderDelStatus brngLkpOrderDelStatus) {
		this.brngLkpOrderDelStatus = brngLkpOrderDelStatus;
	}

	public BrngUsrLogin getBrngUsrLogin() {
		return this.brngUsrLogin;
	}

	public void setBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		this.brngUsrLogin = brngUsrLogin;
	}

}