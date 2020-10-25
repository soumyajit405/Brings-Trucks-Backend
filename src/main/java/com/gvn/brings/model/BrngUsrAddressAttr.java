package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="brng_usr_address_attr")
@NamedQuery(name="BrngUsrAddressAttr.findAll", query="SELECT b FROM BrngUsrAddressAttr b")
public class BrngUsrAddressAttr extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;
	
	@Id
	@Column(name="brng_usr_address_id")
	private int brngUsrAddressId;

	@Column(name="first_name",length=450)
	private String firstName;
	@Column(name="last_name",length=450)
	private String lastName;
	@Column(name="full_address",length=2000)
	private String fullAddress;
	@Column(name="phone_number",length=45)
	private String phoneNumber;
	
	public BrngUsrAddressAttr() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getBrngUsrAddressId() {
		return brngUsrAddressId;
	}


	public void setBrngUsrAddressId(int brngUsrAddressId) {
		this.brngUsrAddressId = brngUsrAddressId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFullAddress() {
		return fullAddress;
	}


	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	

}