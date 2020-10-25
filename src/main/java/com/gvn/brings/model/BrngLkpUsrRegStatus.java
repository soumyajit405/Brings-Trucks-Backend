package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_LKP_USR_REG_STATUS database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_USR_REG_STATUS")
@NamedQuery(name="BrngLkpUsrRegStatus.findAll", query="SELECT b FROM BrngLkpUsrRegStatus b")
public class BrngLkpUsrRegStatus extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String description;

	@Column(name="status_type", nullable=false, length=45)
	private String statusType;

	//bi-directional many-to-one association to BrngUsrReg
	@OneToMany(mappedBy="brngLkpUsrRegStatus")
	private List<BrngUsrReg> brngUsrRegs;

	public BrngLkpUsrRegStatus() {
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

	public String getStatusType() {
		return this.statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public List<BrngUsrReg> getBrngUsrRegs() {
		return this.brngUsrRegs;
	}

	public void setBrngUsrRegs(List<BrngUsrReg> brngUsrRegs) {
		this.brngUsrRegs = brngUsrRegs;
	}

	public BrngUsrReg addBrngUsrReg(BrngUsrReg brngUsrReg) {
		getBrngUsrRegs().add(brngUsrReg);
		brngUsrReg.setBrngLkpUsrRegStatus(this);

		return brngUsrReg;
	}

	public BrngUsrReg removeBrngUsrReg(BrngUsrReg brngUsrReg) {
		getBrngUsrRegs().remove(brngUsrReg);
		brngUsrReg.setBrngLkpUsrRegStatus(null);

		return brngUsrReg;
	}

}