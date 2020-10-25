package com.gvn.brings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_WALLET_ATTR")
@NamedQuery(name="BrngUsrWalletAttr.findAll", query="SELECT b FROM BrngUsrWalletAttr b")
public class BrngUsrWalletAttr extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="tran_amt",length=45)
	private BigDecimal tranAmt;
	
	@Column(name="effective_date")
	private Timestamp effectiveDate;
	
	
	@ManyToOne
	@JoinColumn(name="brng_usr_wallet_id", nullable=false)
	private  BrngUsrWallet brngUsrWallet;
	
	@Column(name="pay_txn_id")
	private String payTxnId;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	private BrngOrder brngOrder;
	
	public BrngUsrWalletAttr() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public BigDecimal getTranAmt() {
		return tranAmt;
	}


	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}


	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public BrngUsrWallet getBrngUsrWallet() {
		return brngUsrWallet;
	}


	public void setBrngUsrWallet(BrngUsrWallet brngUsrWallet) {
		this.brngUsrWallet = brngUsrWallet;
	}


	public String getPayTxnId() {
		return payTxnId;
	}


	public void setPayTxnId(String payTxnId) {
		this.payTxnId = payTxnId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public BrngOrder getBrngOrder() {
		return brngOrder;
	}


	public void setBrngOrder(BrngOrder brngOrder) {
		this.brngOrder = brngOrder;
	}


	

	

	

	

	

}