package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_USR_PASS_CHANGE database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_PASS_CHANGE")
@NamedQuery(name="BrngUsrPassChange.findAll", query="SELECT b FROM BrngUsrPassChange b")
public class BrngUsrPassChange extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="effective_date", nullable=false, length=45)
	private String effectiveDate;

	@Column(nullable=false, length=45)
	private String password;

	//bi-directional many-to-one association to BrngUsrReg
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;

	public BrngUsrPassChange() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

}