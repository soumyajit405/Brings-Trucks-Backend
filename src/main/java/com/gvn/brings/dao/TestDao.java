package com.gvn.brings.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.dto.TestDto;
import com.gvn.brings.model.BrngTest;

@Repository
@Transactional
public class TestDao extends AbstractBaseDao{
	
	public List<TestDto> getList(){
		List<BrngTest> brngTests = getManager().createQuery("Select a From BrngTest a",BrngTest.class).getResultList();
		List<TestDto> dtoList = new ArrayList<>();
		TestDto testDto = null;
		for(BrngTest brngTest:brngTests){
			testDto = new TestDto(brngTest);
			dtoList.add(testDto);
		}
		return dtoList;
	}
	
	public TestDto getById(Integer id){
		BrngTest brngTest = getManager().find(BrngTest.class, id);
		return new TestDto(brngTest);
	}
	
	public void addTestData(BrngTest testData){
		getManager().persist(testData);
	}
}
