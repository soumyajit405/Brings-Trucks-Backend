package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_USR_TYPE database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_USR_TYPE")
@NamedQuery(name="BrngLkpUsrType.findAll", query="SELECT b FROM BrngLkpUsrType b")
public class BrngLkpUsrType extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;
	
	@Column(length=45)
	private String description;

	@Column(name="usr_type", length=45)
	private String usrType;

	public BrngLkpUsrType() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsrType() {
		return this.usrType;
	}

	public void setUsrType(String usrType) {
		this.usrType = usrType;
	}

}