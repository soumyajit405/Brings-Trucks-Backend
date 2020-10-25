package com.gvn.brings.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvn.brings.dao.TestDao;
import com.gvn.brings.dto.TestDto;

@Service("testService")
public class TestService extends AbstractBaseService{
	
	@Autowired
	private TestDao testDao;
	
	public List<TestDto> getList(){
		return testDao.getList();
	}
}
