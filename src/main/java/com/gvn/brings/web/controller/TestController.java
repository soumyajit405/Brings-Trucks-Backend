package com.gvn.brings.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.gvn.brings.dto.TestDto;
import com.gvn.brings.services.TestService;

@RestController
public class TestController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(TestController.class);

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = REST+"test", method = RequestMethod.GET,headers="Accept=application/json")
	public List<TestDto> test()
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		List<TestDto> testDtos = testService.getList();
		//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}

		//logs exception
		logger.error("This is Error message", new Exception("Testing"));
		return testDtos;
	}
	
}
