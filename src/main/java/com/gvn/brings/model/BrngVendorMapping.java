package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_USR_REG database table.
 * 
 */
@Entity
@Table(name="BRNG_VENDOR_MAPPING")
@NamedQuery(name="BrngVendorMapping.findAll", query="SELECT b FROM BrngVendorMapping b")
public class BrngVendorMapping extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="vendor_id", nullable=false, length=10)
	private int vendorId;
	
	@Column(name="user_reg_id", nullable=false, length=10)
	private int usrRegId;

	
	
	
	@Column(name="registered_date")
	private Timestamp registeredDate;
	
	


	

	
	
	public Timestamp getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}

	

	
	public BrngVendorMapping() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getUsrRegId() {
		return usrRegId;
	}

	public void setUsrRegId(int usrRegId) {
		this.usrRegId = usrRegId;
	}

	
	

}