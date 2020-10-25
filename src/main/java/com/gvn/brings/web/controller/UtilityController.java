package com.gvn.brings.web.controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import com.gvn.brings.dto.TestDto;
import com.gvn.brings.model.BrngAdminImages;
import com.gvn.brings.model.BrngLkpPayType;
import com.gvn.brings.model.BrngRateLkp;
import com.gvn.brings.services.TestService;
import com.gvn.brings.services.UtilityService;

@RestController
public class UtilityController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(UtilityController.class);

	@Autowired
	private UtilityService utilityservice;
	
	@RequestMapping(value = REST+"getAppVersion", method = RequestMethod.GET,headers="Accept=application/json")
	public HashMap<String,String> getAppVersion()
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		HashMap<String,String> versionInfo = utilityservice.getAppVersion();
		//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}
		
		//logs exception
		logger.error("This is Error message", new Exception("Testing"));
		return versionInfo;
	}
	
	@RequestMapping(value = REST+"getPaymentOptions", method = RequestMethod.GET,headers="Accept=application/json")
	public HashMap<String,List<BrngLkpPayType>> getPaymentOptions()
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		 List<BrngLkpPayType> brnglkppaytypes = utilityservice.getPaymentOptions();
		//logs debug message
		 HashMap<String,List<BrngLkpPayType>> response=new HashMap<>();
		 response.put("response", brnglkppaytypes);
		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}
		
		//logs exception
		logger.error("This is Error message", new Exception("Testing"));
		return response;
	}
	
	@RequestMapping(value = REST+"getRateValues", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,Float> getRateValues(@RequestBody HashMap<String,String> inputDetails)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		float price = 0;
		if (inputDetails.get("city") != null) {
			 price=utilityservice.getRateValues(inputDetails.get("dist"),inputDetails.get("weight"),inputDetails.get("city"),inputDetails.get("phoneNumber"));
		} else {
		//float price=utilityservice.getRateValues(inputDetails.get("dist"),inputDetails.get("weight"),inputDetails.get("city"),inputDetails.get("phoneNumber"));
		 price=utilityservice.getRateValues(inputDetails.get("dist"),inputDetails.get("weight"));
		}
		float GST=0;
		//float GST= (price*18)/100;
		HashMap<String,Float> response=new HashMap<>();
		response.put("price", (float)Math.ceil(price));
		//response.put("GST", (price*18)/100);
		response.put("GST",GST );
		return response;
		//logs debug message
		
	}
	
	@RequestMapping(value = REST+"deilveryBoyFromWeb", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String> deilveryBoyFromWeb(@RequestBody HashMap<String,String> inputDetails)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		return utilityservice.deilveryBoyFromWeb(inputDetails);//logs debug message
		
	}
	
	@RequestMapping(value = REST+"subscribeUsers", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String> subscribeUsers(@RequestBody HashMap<String,String> inputDetails)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		return utilityservice.subscribeUsers(inputDetails);//logs debug message
		
	}
	
	@RequestMapping(value = REST+"contactDetails", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String> contactDetails(@RequestBody HashMap<String,String> inputDetails,@RequestHeader(value="api-key") String apiKey)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		return utilityservice.contactDetails(inputDetails);//logs debug message
		
	}
	
	@RequestMapping(value = REST+"getOffers", method = RequestMethod.GET,headers="Accept=application/json")
	public HashMap<String,Object> getOffers()
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		ArrayList<HashMap<Object,Object>> listOfOffers=utilityservice.getOffers();//logs debug message
		HashMap<String,Object> response=new HashMap<>();
		response.put("response",listOfOffers);
		return response;
		
	}
	
	@RequestMapping(value = REST+"payment-details", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String> proceedPayment(@RequestBody HashMap<String,String> inputDetails)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		return utilityservice.proceedPayment(inputDetails);//logs debug message
		
	}
	
	@RequestMapping(value = REST+"getAdminImages", method = RequestMethod.GET,headers="Accept=application/json")
	public List<BrngAdminImages> getAdminImages()
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		return utilityservice.getAdminImages();//logs debug message
		
	}
	
	@RequestMapping(value=REST+"uploadImage2",method = RequestMethod.POST,headers="Accept=application/json")
    public String  uploadImage2(@RequestBody HashMap<String,String> inputDetails)
    {
        try
        {
        	String imageDataArr[]=inputDetails.get("imageValue").split(",");
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte=Base64.decodeBase64(imageDataArr[1]);
            
            String directory="C:\\Users\\THINKPAD\\Documents\\Works\\"+"sample.jpeg";

            new FileOutputStream(directory).write(imageByte);
            return "success ";
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            return "error = "+e;
        }

    }
	
	@RequestMapping(value = REST+"activateDeliveryBoy/{email}", method = RequestMethod.GET,headers="Accept=application/json")
	public HashMap<String, String> activateDeliveryBoy(@PathVariable("email") String email)
	{				
		/*TestModel testModel = new TestModel("Sunil", "Kumar");
		return testModel;*/
		System.out.println("Email "+email);
		return utilityservice.activateDeliveryBoy(email);//logs debug message
		
	}
	
}
