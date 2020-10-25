package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRNG_LKP_IS_ACCEPTED database table.
 * 
 */
@Entity
@Table(name="brng_lkp_file_path")
@NamedQuery(name="BrngLkpFilePath.findAll", query="SELECT b FROM BrngLkpFilePath b")
public class BrngLkpFilePath extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="file_path", nullable=false, length=2000)
	private String filePath;
	
	@Column(name="type", nullable=false, length=1)
	private String type;

	public BrngLkpFilePath() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}