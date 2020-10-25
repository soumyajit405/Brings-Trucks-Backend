package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_rate_lkp")
@NamedQuery(name="BrngRateLkp.findAll", query="SELECT b FROM BrngRateLkp b")
public class BrngRateLkp extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	@Column(name="initial_rate", nullable=false, length=45)
	private String initialRate;
	
	@Column(name="rate_per_km", nullable=false, length=45)
	private String ratePerKm;
	
	@Column(name="rate_per_kg", nullable=false, length=45)
	private String ratePerKg;
	
	@Column(name="initial_kms", nullable=false, length=45)
	private String initialKms;
	
	@Column(name="initial_weight", nullable=false, length=45)
	private String initialWeight;
	
	@Column(name="service_percent", nullable=false, length=45)
	private String servicePercent;
	
	
	
	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngRateLkp() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getInitialRate() {
		return initialRate;
	}

	public void setInitialRate(String initialRate) {
		this.initialRate = initialRate;
	}

	public String getRatePerKm() {
		return ratePerKm;
	}

	public void setRatePerKm(String ratePerKm) {
		this.ratePerKm = ratePerKm;
	}

	public String getRatePerKg() {
		return ratePerKg;
	}

	public void setRatePerKg(String ratePerKg) {
		this.ratePerKg = ratePerKg;
	}

	public String getInitialKms() {
		return initialKms;
	}

	public void setInitialKms(String initialKms) {
		this.initialKms = initialKms;
	}

	public String getServicePercent() {
		return servicePercent;
	}

	public void setServicePercent(String servicePercent) {
		this.servicePercent = servicePercent;
	}

	public String getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(String initialWeight) {
		this.initialWeight = initialWeight;
	}


	

	
}