package com.gvn.brings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_corporate_coupon_tx")
@NamedQuery(name="BrngCorporateOfferTx.findAll", query="SELECT b FROM BrngCorporateOfferTx b")
public class BrngCorporateOfferTx extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="coupon_id", nullable=false, length=1)
	private int couponId;
	
	@Column(name="amount", nullable=false)
	private BigDecimal amount;
	
	
	@Column(name="effective_date", nullable=false)
	private Timestamp effectiveDate;
	
	public BrngCorporateOfferTx() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
}