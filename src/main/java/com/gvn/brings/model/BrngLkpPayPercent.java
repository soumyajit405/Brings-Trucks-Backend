package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BRNG_LKP_PAY_PERCENT database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_PAY_PERCENT")
@NamedQuery(name="BrngLkpPayPercent.findAll", query="SELECT b FROM BrngLkpPayPercent b")
public class BrngLkpPayPercent extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="effective_date", nullable=false)
	private Date effectiveDate;

	@Column(name="pay_percent", nullable=false, precision=10, scale=2)
	private BigDecimal payPercent;

	public BrngLkpPayPercent() {
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

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getPayPercent() {
		return this.payPercent;
	}

	public void setPayPercent(BigDecimal payPercent) {
		this.payPercent = payPercent;
	}

}