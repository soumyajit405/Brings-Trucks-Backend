package com.gvn.brings.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvn.brings.dao.TestDao;
import com.gvn.brings.dao.UtilityDAO;
import com.gvn.brings.dto.TestDto;
import com.gvn.brings.model.BrngAdminImages;
import com.gvn.brings.model.BrngLkpPayType;
import com.gvn.brings.model.BrngRateLkp;

@Service("utilityservice")
public class UtilityService extends AbstractBaseService{
	
	@Autowired
	private UtilityDAO utilitydao;
	
	public HashMap<String,String> getAppVersion(){
		return utilitydao.getAppVersion();
	}
	
	public List<BrngLkpPayType> getPaymentOptions(){
		return utilitydao.getPaymentOptions();
	}
	
	public float getRateValues(String dist,String weight, String city, String phoneNumber){
		return utilitydao.getRateValues( dist, weight, city, phoneNumber);
	}
	
	public float getRateValues(String dist,String weight){
		return utilitydao.getRateValues( dist, weight);
	}	
	public HashMap<String,String> deilveryBoyFromWeb(HashMap<String,String> inputDetails){
		return utilitydao.deilveryBoyFromWeb(inputDetails);
	}
	
	public HashMap<String,String> subscribeUsers(HashMap<String,String> inputDetails){
		return utilitydao.subscribeUsers(inputDetails);
	}
	public HashMap<String,String> contactDetails(HashMap<String,String> inputDetails){
		return utilitydao.contactDetails(inputDetails);
	}
	
	public ArrayList<HashMap<Object,Object>> getOffers(){
		return utilitydao.getOffers();
	}
	
    public HashMap<String,String> proceedPayment(HashMap<String,String> response) {
    	return utilitydao.proceedPayment(response);
    }
    
    public List<BrngAdminImages> getAdminImages(){
    	return utilitydao.getAdminImages();
    }
    
    public HashMap<String, String> activateDeliveryBoy(String email) {
    	return utilitydao.activateDeliveryBoy(email);
    }
}
