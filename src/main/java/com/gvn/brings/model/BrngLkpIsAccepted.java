package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="BRNG_LKP_IS_ACCEPTED")
@NamedQuery(name="BrngLkpIsAccepted.findAll", query="SELECT b FROM BrngLkpIsAccepted b")
public class BrngLkpIsAccepted extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	private String description;

	@Column(name="is_accepted", nullable=false, length=1)
	private String isAccepted;

	public BrngLkpIsAccepted() {
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

	public String getIsAccepted() {
		return this.isAccepted;
	}

	public void setIsAccepted(String isAccepted) {
		this.isAccepted = isAccepted;
	}

}