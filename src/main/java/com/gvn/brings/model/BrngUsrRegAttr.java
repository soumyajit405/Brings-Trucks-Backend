package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRNG_USR_REG_ATTR database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_REG_ATTR")
@NamedQuery(name="BrngUsrRegAttr.findAll", query="SELECT b FROM BrngUsrRegAttr b")
public class BrngUsrRegAttr extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="effective_date", nullable=false)
	private Date effectiveDate;

	//bi-directional many-to-one association to BrngUsrReg
	
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;

	
	@Column(name="first_name", nullable=false, length=45)
	private String firstName;

	@Column(name="last_name", length=45)
	private String lastName;

	@Column(name="middle_name", length=45)
	private String middleName;
	
	@Column(name="phone_number" ,nullable=false, length=1)
	private String phoneNumber;
	
	
	@Column(name="gender" , length=20)
	private String gender;
	
	@Column(name="age" , length=3)
	private int age;

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

	public BrngUsrRegAttr() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

}