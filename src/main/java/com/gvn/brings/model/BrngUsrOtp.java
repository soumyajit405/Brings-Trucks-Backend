package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRNG_USR_OTP database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_OTP")
@NamedQuery(name="BrngUsrOtp.findAll", query="SELECT b FROM BrngUsrOtp b")
public class BrngUsrOtp extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="effective_date", nullable=false)
	private Date effectiveDate;

	@Column(name="otp_code", nullable=false, length=45)
	private String otpCode;

	//bi-directional many-to-one association to BrngUsrReg
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;

	public BrngUsrOtp() {
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

	public String getOtpCode() {
		return this.otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

}