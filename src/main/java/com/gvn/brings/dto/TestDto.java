package com.gvn.brings.dto;

import com.gvn.brings.model.BrngTest;

public class TestDto extends AbstractBaseDto{
	
	private static final long serialVersionUID = 1L;
	
	private String name;

	public TestDto(){
		
	}
	
	public TestDto(BrngTest brngTest){
		this.name = brngTest.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
