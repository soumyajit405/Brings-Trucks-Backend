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
@Table(name="BRNG_USR_WALLET")
@NamedQuery(name="BrngUsrWallet.findAll", query="SELECT b FROM BrngUsrWallet b")
public class BrngUsrWallet extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="curr_amt",length=45)
	private BigDecimal currAmt;
	
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;
	
	public BrngUsrWallet() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BrngUsrReg getBrngUsrReg() {
		return brngUsrReg;
	}


	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}


	public BigDecimal getCuurAmt() {
		return currAmt;
	}


	public void setCuurAmt(BigDecimal currAmt) {
		this.currAmt = currAmt;
	}

}