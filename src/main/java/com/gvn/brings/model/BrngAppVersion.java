package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_app_version")
@NamedQuery(name="BrngAppVersion.findAll", query="SELECT b FROM BrngAppVersion b")
public class BrngAppVersion extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;


	@Column(name="android_version", nullable=false, length=45)
	private String androidVersion;
	
	@Column(name="ios_version", nullable=false, length=45)
	private String iosVersion;
	
	@Column(name="effective_date", nullable=false, length=1)
	private Timestamp effectiveDate;

	public BrngAppVersion() {
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

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getIosVersion() {
		return iosVersion;
	}

	public void setIosVersion(String iosVersion) {
		this.iosVersion = iosVersion;
	}


	

	
}