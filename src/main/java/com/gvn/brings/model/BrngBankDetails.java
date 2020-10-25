package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BRNG_TEST database table.
 * 
 */
@Entity
@Table(name="BRNG_BANK_DETAILS")
@NamedQuery(name="BrngBankDetails.findAll", query="SELECT b FROM BrngBankDetails b")
public class BrngBankDetails extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="bank_name",length=45)
	private String bankName;
	@Column(name="ifsc_code",length=45)
	private String ifscCode;
	@Column(name="account_number",length=45)
	private String accountNumber;
	@Column(name="account_name",length=45)
	private String accountName;
	@Column(name="branch",length=45)
	private String branch;
	@Column(name="effective_date")
	private Timestamp effectiveDate;
	
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;
	
	public BrngBankDetails() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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


	public BrngUsrReg getBrngUsrReg() {
		return brngUsrReg;
	}


	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}


	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


}