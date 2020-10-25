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
@Table(name="brng_corporate_coupon")
@NamedQuery(name="BrngCorporateOffer.findAll", query="SELECT b FROM BrngCorporateOffer b")
public class BrngCorporateOffer extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="coupon_code", nullable=false, length=1)
	private String couponCode;
	
	@Column(name="amount", nullable=false)
	private BigDecimal amount;
	
	@Column(name="remaining_amount", nullable=false)
	private BigDecimal remainingAmount;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="start_date", nullable=false)
	private Timestamp startDate;
	
	@Column(name="end_date", nullable=false)
	private Timestamp endDate;

	public BrngCorporateOffer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
}