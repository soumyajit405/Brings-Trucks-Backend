package com.gvn.brings.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvn.brings.dao.OrderDao;
import com.gvn.brings.dao.RegistrationDao;
import com.gvn.brings.dto.OrderDto;
import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngVehicleOrder;

@Service("orderService")
public class OrderService extends AbstractBaseService{

	@Autowired
	private OrderDao orderDao;
	
	public List<OrderDto> getPayPercent(){
		return orderDao.getpayPercent();
	}
	public List<OrderDto> getIsPaidTypes(){
		return orderDao.getIsPaidTypes();
	}
	
	public HashMap<String,String> bookAnOrder(BrngOrder brngorder,String apiKey){
		return orderDao.bookAnOrder(brngorder,apiKey);
	}
	
	public HashMap<String,String> bookAnVehicleOrder(BrngVehicleOrder brngorder,String apiKey){
		return orderDao.bookAnVehicleOrder(brngorder,apiKey);
	}
	
	public HashMap<String,Object> bookMultipleOrdersOrder(ArrayList<BrngOrder> brngorder,String apiKey){
		return orderDao.bookMultipleOrdersOrder(brngorder, apiKey);
	}
	
	public List<OrderDto> getOrdersById(String email,String apiKey){
		return orderDao.getOrdersById(email,apiKey);
	}
	public List<OrderDto> getOrdersByDate(Hashtable<String,String> inputDetails){
		return orderDao.getOrdersByDate(inputDetails);
	}
	
	public List<OrderDto> getOrdersByDateServiceMan(Hashtable<String,String> inputDetails){
		return orderDao.getOrdersByDateServiceMan(inputDetails);
	}
	public List<OrderDto> getCurrentOrdersCustomers(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.getCurrentOrdersCustomers(inputDetails,apiKey);
	}
	
	public List<OrderDto> getCurrentOrdersService(Hashtable<String,String> inputDetails){
		return orderDao.getCurrentOrdersService(inputDetails);
	}
	public HashMap<String,String> confirmPayment(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.confirmPayment(inputDetails,apiKey);
	}
	
	public HashMap<String,String> confirmPaymentForVehicle(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.confirmPaymentForVehicle(inputDetails,apiKey);
	}
	
	public HashMap<String,String> retryOrder(Hashtable<String,String> inputDetails){
		return orderDao.retryOrder(inputDetails);
	}
	
	public List<OrderDto> getOrderDetailsById(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.getOrderDetailsById(Integer.parseInt(inputDetails.get("orderId")),apiKey);
	}
	
	public List<OrderDto> getPastOrdersCustomers(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.getPastOrdersCustomers(inputDetails,apiKey);
	}
	
	public HashMap<String,String> completeTrip(Hashtable<String,String> inputDetails){
		return orderDao.completeTrip(inputDetails);
	}
	
	public List<OrderDto> getPastOrdersServiceMan(Hashtable<String,String> inputDetails){
		return orderDao.getPastOrdersServiceMan(inputDetails);
	}
	
	public HashMap<String,String> cancelOrderByCustomer(Hashtable<String,String> inputDetails){
		return orderDao.cancelOrderByCustomer(inputDetails);
	}
	
	public HashMap<String,String> rateTrip(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.rateTrip(inputDetails,apiKey);
	}
	
	public HashMap<String,String> validateCoupons(HashMap<String,String> inputDetails,String apiKey){
		return orderDao.validateCoupons(inputDetails,apiKey);
	}
	
	public HashMap<String,Object> generateCheckSum(Hashtable<String,String> inputDetails,String apiKey){
		return orderDao.generateCheckSum(inputDetails, apiKey);
	}
	
	public HashMap<String,String> retryMultipleOrder(Hashtable<String,Object> inputDetails){
		return orderDao.retryMultipleOrder(inputDetails);
	}
	
	public HashMap<String,Object> confirmPaymentForMultipleOrders(HashMap<String,Object> inputDetails,String apiKey){
		return orderDao.confirmPaymentForMultipleOrders(inputDetails,apiKey);
	}
	
	/*public List<OrderDto> getUpcomingOrders(Hashtable<String,String> inputDetails){
		return orderDao.getUpcomingOrders(inputDetails);
	}*/
}
