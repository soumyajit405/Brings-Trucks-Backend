package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_USR_REG_TYPE database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_USR_REG_TYPE")
@NamedQuery(name="BrngLkpUsrRegType.findAll", query="SELECT b FROM BrngLkpUsrRegType b")
public class BrngLkpUsrRegType extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="usr_reg_type", nullable=false, length=45)
	private String usrRegType;

	//bi-directional many-to-one association to BrngUsrReg
	@OneToMany(mappedBy="brngLkpUsrRegType")
	private List<BrngUsrReg> brngUsrRegs;

	public BrngLkpUsrRegType() {
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

	public String getUsrRegType() {
		return this.usrRegType;
	}

	public void setUsrRegType(String usrRegType) {
		this.usrRegType = usrRegType;
	}

	public List<BrngUsrReg> getBrngUsrRegs() {
		return this.brngUsrRegs;
	}

	public void setBrngUsrRegs(List<BrngUsrReg> brngUsrRegs) {
		this.brngUsrRegs = brngUsrRegs;
	}

	public BrngUsrReg addBrngUsrReg(BrngUsrReg brngUsrReg) {
		getBrngUsrRegs().add(brngUsrReg);
		brngUsrReg.setBrngLkpUsrRegType(this);

		return brngUsrReg;
	}

	public BrngUsrReg removeBrngUsrReg(BrngUsrReg brngUsrReg) {
		getBrngUsrRegs().remove(brngUsrReg);
		brngUsrReg.setBrngLkpUsrRegType(null);

		return brngUsrReg;
	}

}