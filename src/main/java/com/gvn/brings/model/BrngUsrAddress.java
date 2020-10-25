package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="brng_usr_address")
@NamedQuery(name="BrngUsrAddress.findAll", query="SELECT b FROM BrngUsrAddress b")
public class BrngUsrAddress extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="address",length=300)
	private String address;
	@Column(name="lat",length=45)
	private String lat;
	@Column(name="lng",length=45)
	private String lng;
	@Column(name="saved_name",length=45)
	private String savedName;
	
	@Column(name="place_id")
	private String placeId;
	
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;
	
	public BrngUsrAddress() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	

	public BrngUsrReg getBrngUsrReg() {
		return brngUsrReg;
	}


	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getSavedName() {
		return savedName;
	}


	public void setSavedName(String savedName) {
		this.savedName = savedName;
	}


	public String getPlaceId() {
		return placeId;
	}


	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}


	


}