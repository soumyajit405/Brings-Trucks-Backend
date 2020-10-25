package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_vehicle_typr_pl")
@NamedQuery(name="BrngLkpVehicleType.findAll", query="SELECT b FROM BrngLkpVehicleType b")
public class BrngLkpVehicleType extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column
	private String description;

	@Column(name="name", nullable=false, length=1)
	private String name;

	public BrngLkpVehicleType() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}