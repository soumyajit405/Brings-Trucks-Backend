package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRNG_ORDER_DELIVERY_ATTR database table.
 * 
 */
@Entity
@Table(name="BRNG_ORDER_DELIVERY_ATTR")
@NamedQuery(name="BrngOrderDeliveryAttr.findAll", query="SELECT b FROM BrngOrderDeliveryAttr b")
public class BrngOrderDeliveryAttr extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;
	
	

	@Column(name="order_delivery_id", length=45)
	private int brngOrderDelivery;
	
	@Column(name="lat", length=45)
	private String lat;

	@Column(name="lng", length=45)
	private String lng;

	
	public BrngOrderDeliveryAttr() {
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


	public int getBrngOrderDelivery() {
		return brngOrderDelivery;
	}


	public void setBrngOrderDelivery(int brngOrderDelivery) {
		this.brngOrderDelivery = brngOrderDelivery;
	}

}