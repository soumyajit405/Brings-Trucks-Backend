package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="brng_serviceman_location_dtls")
@NamedQuery(name="BrngServicemanLocationDtls.findAll", query="SELECT b FROM BrngServicemanLocationDtls b")
public class BrngServicemanLocationDtls extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="lat",length=45)
	private String lat;
	@Column(name="lng",length=45)
	private String lng;
	@Column(name="address",length=45)
	private String address;
	
	
	@ManyToOne
	@JoinColumn(name="usr_login_id", nullable=false)
	private BrngUsrLogin brngUsrLogin;
	
	public BrngServicemanLocationDtls() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public BrngUsrLogin getBrngUsrLogin() {
		return brngUsrLogin;
	}


	public void setBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		this.brngUsrLogin = brngUsrLogin;
	}


	

}