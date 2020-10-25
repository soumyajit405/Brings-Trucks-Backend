package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_missed_search")
@NamedQuery(name="BrngMissedSearch.findAll", query="SELECT b FROM BrngMissedSearch b")
public class BrngMissedSearch extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	
	
	@Column(name="from_address", nullable=false, length=1500)
	private String fromAddress;
	@Column(name="from_lat", nullable=false, length=50)
	private String fromLat;
	
	@Column(name="from_lng", nullable=false, length=50)
	private String fromLng;
	
	
	@Column(name="extra_info", length=50)
	private String extraInfo;
	
	
	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngMissedSearch() {
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

	

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromLat() {
		return fromLat;
	}

	public void setFromLat(String fromLat) {
		this.fromLat = fromLat;
	}

	
	public String getFromLng() {
		return fromLng;
	}

	public void setFromLng(String fromLng) {
		this.fromLng = fromLng;
	}

	

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	


	

	
}