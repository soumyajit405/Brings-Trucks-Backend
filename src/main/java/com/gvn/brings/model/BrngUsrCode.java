package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRNG_USR_CODE database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_CODE")
@NamedQuery(name="BrngUsrCode.findAll", query="SELECT b FROM BrngUsrCode b")
public class BrngUsrCode extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="effective_date", nullable=false)
	private Date effectiveDate;

	@Column(name="prom_code", length=45)
	private String promCode;

	@Column(name="ref_code", length=45)
	private String refCode;
	
	@Column(name="ref_code_id")
	private int refCodeId;

	//bi-directional many-to-one association to BrngUsrReg
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;

	public BrngUsrCode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPromCode() {
		return this.promCode;
	}

	public void setPromCode(String promCode) {
		this.promCode = promCode;
	}

	public String getRefCode() {
		return this.refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

	public int getRefCodeId() {
		return refCodeId;
	}

	public void setRefCodeId(int refCodeId) {
		this.refCodeId = refCodeId;
	}

}