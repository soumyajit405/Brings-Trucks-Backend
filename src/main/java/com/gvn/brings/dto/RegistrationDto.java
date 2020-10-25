package com.gvn.brings.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.gvn.brings.model.BrngBankDetails;
import com.gvn.brings.model.BrngLkpUsrRegStatus;
import com.gvn.brings.model.BrngLkpUsrRegType;
import com.gvn.brings.model.BrngLkpUsrType;
import com.gvn.brings.model.BrngUsrAddress;
import com.gvn.brings.model.BrngUsrAddressAttr;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.model.BrngUsrRegAttr;

public class RegistrationDto extends AbstractBaseDto{
	private static final long serialVersionUID = 1L;
	
	private String usrRegType;
	private String description;
	private String usrRegStatus;
	private String userType;
	
	
	//For User Details
	
	private BrngUsrReg brngUsrReg;
	
	//For User Extra Details
	private String firstName;
	private String lastName;
	private String middleName;
	private String phoneNumber;
	private String gender;
	private int age;
	private BrngUsrRegAttr brngusrregattr;
	
	private String validatedId;
	
	
	//for Bank details
	
	private String bankName;

	private String ifscCode;

	private String accountNumber;
	
	private String accountName;
	
	private String branch;
	
	
	//for Address
	
	private String address;
	
	private String lat;
	
	private String lng;
	
	private String savedName;
	
	private int addressId;
	
	private String placeId;
	
	// for Address Attr
	private String firstNamea;
	private String lastNamea;
	private String phoneNumbera;
	private String fullAddressa;
	private int addressIda;
	
	
	public String getFirstNamea() {
		return firstNamea;
	}


	public void setFirstNamea(String firstNamea) {
		this.firstNamea = firstNamea;
	}


	public String getLastNamea() {
		return lastNamea;
	}


	public void setLastNamea(String lastNamea) {
		this.lastNamea = lastNamea;
	}


	public String getPhoneNumbera() {
		return phoneNumbera;
	}


	public void setPhoneNumbera(String phoneNumbera) {
		this.phoneNumbera = phoneNumbera;
	}


	public String getFullAddressa() {
		return fullAddressa;
	}


	public void setFullAddressa(String fullAddressa) {
		this.fullAddressa = fullAddressa;
	}


	public int getAddressIda() {
		return addressIda;
	}


	public void setAddressIda(int addressIda) {
		this.addressIda = addressIda;
	}


	private float totalDistance;
	
	private int profileChangeble;
	
	public int getProfileChangeble() {
		return profileChangeble;
	}


	public void setProfileChangeble(int profileChangeble) {
		this.profileChangeble = profileChangeble;
	}


	public BrngUsrReg getBrngUsrReg() {
		return brngUsrReg;
	}


	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}


	public BrngUsrRegAttr getBrngusrregattr() {
		return brngusrregattr;
	}


	public void setBrngusrregattr(BrngUsrRegAttr brngusrregattr) {
		
		this.brngusrregattr = brngusrregattr;
	}


	public RegistrationDto(BrngUsrReg brngUsrReg ){
		this.brngUsrReg = brngUsrReg;
		
	}
	
	
	public RegistrationDto(BrngUsrRegAttr brngusrregattr ){
		System.out.println(" yuk" +brngusrregattr.getAge());
		this.firstName=brngusrregattr.getFirstName();
		this.lastName=brngusrregattr.getLastName();
		this.age=brngusrregattr.getAge();
		this.phoneNumber=brngusrregattr.getPhoneNumber();
		this.middleName=brngusrregattr.getMiddleName();
		this.validatedId=brngusrregattr.getBrngUsrReg().getBrnglkpservicemanvalidated().getCode();
		if(brngusrregattr.getGender().equalsIgnoreCase("1"))
		{
			this.gender="Male";	
		}
		else
		{
			this.gender="Female";
		}
		
		//this.totalDistance=brngusrregattr.get
		
	}
	
	public RegistrationDto(BrngBankDetails brngbankdetails ){
		
		this.bankName=brngbankdetails.getBankName();
		this.ifscCode=brngbankdetails.getIfscCode();
		this.accountName=brngbankdetails.getAccountName();
		this.accountNumber=brngbankdetails.getAccountNumber();
		this.branch=brngbankdetails.getBranch();
		
		
	}
	
public RegistrationDto(BrngUsrAddress brngusraddress ){
		
		this.address=brngusraddress.getAddress();
		this.lat=brngusraddress.getLat();
		this.lng=brngusraddress.getLng();
		this.savedName=brngusraddress.getSavedName();
		this.addressId=brngusraddress.getId();
		this.placeId=brngusraddress.getPlaceId();
		
		
	}

public RegistrationDto(BrngUsrAddressAttr brngusraddressattr ){
	
	this.firstNamea=brngusraddressattr.getFirstName();
	this.lastNamea=brngusraddressattr.getLastName();
	this.phoneNumbera=brngusraddressattr.getPhoneNumber();
	this.fullAddressa=brngusraddressattr.getFullAddress();
	this.addressIda=brngusraddressattr.getBrngUsrAddressId();
	
}
	
	public RegistrationDto(BrngLkpUsrRegStatus brngLkpUsrRegStatus ){
		this.usrRegStatus = brngLkpUsrRegStatus.getStatusType();
		this.description = brngLkpUsrRegStatus.getDescription();
	}
	
	public RegistrationDto(BrngLkpUsrType brngLkpUsrType ){
		this.userType = brngLkpUsrType.getUsrType();
		this.description = brngLkpUsrType.getDescription();
	}
	
	public RegistrationDto(BrngLkpUsrRegType brngLkpUsrRegType){
		this.usrRegType = brngLkpUsrRegType.getUsrRegType();
		this.description = brngLkpUsrRegType.getDescription();
	}

	public String getUsrRegType() {
		return usrRegType;
	}

	public void setUsrRegType(String usrRegType) {
		this.usrRegType = usrRegType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsrRegStatus() {
		return usrRegStatus;
	}

	public void setUsrRegStatus(String usrRegStatus) {
		this.usrRegStatus = usrRegStatus;
	}
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
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


	public String getValidatedId() {
		return validatedId;
	}


	public void setValidatedId(String validatedId) {
		this.validatedId = validatedId;
	}


	public float getTotalDistance() {
		return totalDistance;
	}


	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}


	public int getAddressId() {
		return addressId;
	}


	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}


	public String getPlaceId() {
		return placeId;
	}


	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	
}
