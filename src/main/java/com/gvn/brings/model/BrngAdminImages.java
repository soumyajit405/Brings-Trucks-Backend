package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="brng_admin_images")
@NamedQuery(name="BrngAdminImages.findAll", query="SELECT b FROM BrngAdminImages b")
public class BrngAdminImages extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="path",length=2000)
	private String path;
	@Column(name="status",length=45)
	private String status;
	@Column(name="effective_start_date")
	private Timestamp effectiveStartDate;
	
	@Column(name="effective_end_date")
	private Timestamp effectiveEndDate;
	
	
	public BrngAdminImages() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Timestamp getEffectiveStartDate() {
		return effectiveStartDate;
	}


	public void setEffectiveStartDate(Timestamp effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}


	public Timestamp getEffectiveEndDate() {
		return effectiveEndDate;
	}


	public void setEffectiveEndDate(Timestamp effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}





}