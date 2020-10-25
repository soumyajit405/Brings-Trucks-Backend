package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BRNG_USR_LOGIN database table.
 * 
 */
@Entity
@Table(name="brng_usr_files")
@NamedQuery(name="BrngUsrFiles.findAll", query="SELECT b FROM BrngUsrFiles b")
public class BrngUsrFiles extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="aadhar" , length=200)
	private String aadhar;
	
	@Column(name="pvin" , length=200)
	private String pvin;
	
	@Column(name="driving_lic" , length=200)
	private String driving_lic;
	
	@Column(name="vehicle_rc" , length=200)
	private String vehiclerc;
	
	@Column(name="image" , length=200)
	private String image;
	
	//bi-directional many-to-one association to BrngUsrReg
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;
	
	@Column(name="effective_date")
	private Timestamp effectiveDate;
	
	public BrngUsrFiles() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}


	public String getDriving_lic() {
		return driving_lic;
	}

	public void setDriving_lic(String driving_lic) {
		this.driving_lic = driving_lic;
	}

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPvin() {
		return pvin;
	}

	public void setPvin(String pvin) {
		this.pvin = pvin;
	}

	public String getVehiclerc() {
		return vehiclerc;
	}

	public void setVehiclerc(String vehiclerc) {
		this.vehiclerc = vehiclerc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}