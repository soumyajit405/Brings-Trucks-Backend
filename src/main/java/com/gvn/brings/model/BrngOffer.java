package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_offer")
@NamedQuery(name="BrngOffer.findAll", query="SELECT b FROM BrngOffer b")
public class BrngOffer extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="brng_offer_type", nullable=false, length=1)
	private int offerType;
	
	@Column(name="bring_offer_percent", nullable=false)
	private String offerPercent;
	
	@Column(name="max_discount", nullable=false)
	private String maxDiscount;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="max_times", nullable=false)
	private String maxTimes;
	
	public String getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(String maxTimes) {
		this.maxTimes = maxTimes;
	}

	@Column(name="start_date", nullable=false)
	private Timestamp startDate;
	
	@Column(name="end_date", nullable=false)
	private Timestamp endDate;

	public BrngOffer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOfferType() {
		return offerType;
	}

	public void setOfferType(int offerType) {
		this.offerType = offerType;
	}

	public String getOfferPercent() {
		return offerPercent;
	}

	public void setOfferPercent(String offerPercent) {
		this.offerPercent = offerPercent;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(String maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	
}