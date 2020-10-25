package com.gvn.brings.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.dto.OrderDeliveryDto;
import com.gvn.brings.dto.OrderDto;
import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngCorporateOffer;
import com.gvn.brings.model.BrngCorporateOfferTx;
import com.gvn.brings.model.BrngGeneralLkp;
import com.gvn.brings.model.BrngLkpIsAccepted;
import com.gvn.brings.model.BrngLkpIsCancelled;
import com.gvn.brings.model.BrngLkpIsInsurance;
import com.gvn.brings.model.BrngLkpIsPaid;
import com.gvn.brings.model.BrngLkpIsPicked;
import com.gvn.brings.model.BrngLkpIsRetry;
import com.gvn.brings.model.BrngLkpOrderDelStatus;
import com.gvn.brings.model.BrngLkpPayPercent;
import com.gvn.brings.model.BrngLkpPayType;
import com.gvn.brings.model.BrngLkpUsrRegStatus;
import com.gvn.brings.model.BrngLkpUsrRegType;
import com.gvn.brings.model.BrngLkpUsrType;
import com.gvn.brings.model.BrngOffer;
import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngOrderDelivery;
import com.gvn.brings.model.BrngServicemanLocationDtls;
import com.gvn.brings.model.BrngUsrFiles;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrPassChange;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.model.BrngUsrRegAttr;
import com.gvn.brings.model.BrngVehicleOrder;
import com.gvn.brings.util.CommonUtility;
import com.gvn.brings.util.DistanceChecker;
import com.gvn.brings.util.NotificationGenerator;
import com.gvn.brings.util.NotificationGeneratorImpl;
import com.gvn.brings.util.NotificationGeneratorImplA;
import com.gvn.brings.util.PushHelper;
import com.gvn.brings.util.UserMessages;

@Transactional
@Repository
public class OrderDao {

	@PersistenceContext
	private EntityManager manager;
	
	
	
	public List<OrderDto> getpayPercent(){
		List<BrngLkpPayPercent> brngLkpPayPercents = manager.createQuery("Select a From BrngLkpPayPercent a",BrngLkpPayPercent.class).
				getResultList();
		List<OrderDto> dtoList = new ArrayList();
		OrderDto orderDto = null;
		for(BrngLkpPayPercent brngLkpPayPercent:brngLkpPayPercents){
			orderDto = new OrderDto(brngLkpPayPercent);
			dtoList.add(orderDto);
		}
		return dtoList;
	}
	
	public List<OrderDto> getIsPaidTypes(){
		List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager.createQuery("Select a From BrngLkpIsPaid a",BrngLkpIsPaid.class).
				getResultList();
		List<OrderDto> dtoList = new ArrayList();
		OrderDto orderDto = null;
		for(BrngLkpIsPaid brngLkpIsPaid:brngLkpIsPaidTypes){
			orderDto = new OrderDto(brngLkpIsPaid);
			dtoList.add(orderDto);
		}
		return dtoList;
	}
	
	public HashMap<String,String> bookAnOrder(BrngOrder brngorder,String apiKey){
		
		HashMap<String,String> response=new HashMap<>();
		String message="";
		BrngOffer brngoffer =null;
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		try
		{
			// Made Error 
		// int r=	1/0;
		BrngLkpIsPaid brngispaid = manager.createQuery("Select a From BrngLkpIsPaid a where a.isPaid='N'",BrngLkpIsPaid.class).getSingleResult();
		BrngLkpIsPicked brngispicked = manager.createQuery("Select a From BrngLkpIsPicked a where a.isPicked='N'",BrngLkpIsPicked.class).getSingleResult();
		BrngLkpIsAccepted brngisaccepted = manager.createQuery("Select a From BrngLkpIsAccepted a where a.isAccepted.isAccepted='N'",BrngLkpIsAccepted.class).getSingleResult();
		BrngLkpIsCancelled brngiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).getSingleResult();
		//BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='N'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='Y'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpPayType brnglkppaytype = manager.createQuery("Select a From BrngLkpPayType a where a.type='N'",BrngLkpPayType.class).getSingleResult();
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngorder.getBrngUsrLogin().getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
BrngLkpIsInsurance brnglkpinsurance=manager.createQuery("Select a From BrngLkpIsInsurance a where a.isInsured='"+brngorder.getBrngLkpIsInsurance().getIsInsured()+"'",BrngLkpIsInsurance.class).getSingleResult();
		//int checkFreeStatus=checkLimitForFree(userLoginId);
String offerName=brngorder.getBrngOffer().getName();
if(offerName.toUpperCase().contains("COR"))
{

	//brngorder.getBrngOffer().setName(brngorder.getBrngOffer().getName().toUpperCase());
	 brngoffer=manager.createQuery("select a from BrngOffer a where a.name='CORP'",BrngOffer.class).getSingleResult();
	BrngCorporateOffer brngcorporateoffer=manager.createQuery("Select a from BrngCorporateOffer a where a.couponCode='"+offerName.toUpperCase()+"'",BrngCorporateOffer.class).getSingleResult();
	BrngCorporateOfferTx brngcotx=new BrngCorporateOfferTx();
	brngcotx.setAmount(brngorder.getDiscountPrice());
	brngcotx.setCouponId(brngcorporateoffer.getId());
	brngcotx.setEffectiveDate(ts);
	
	manager.persist(brngcotx);
	Query query = manager.createQuery("update BrngCorporateOffer set remainingAmount="+brngcorporateoffer.getRemainingAmount().subtract(brngorder.getDiscountPrice())+" where couponCode='"+offerName.toUpperCase()+"'");
	query.executeUpdate();
}
else
{
	brngoffer=manager.createQuery("select a from BrngOffer a where a.name='"+brngorder.getBrngOffer().getName()+"'",BrngOffer.class).getSingleResult();
}
		int checkFirstOrderStatus=checkFirstOrderForFree(userLoginId);
	//	BrngLkpIsComplete brnglkpiscomplete = manager.createQuery("Select a From BrngLkpIsComplete a where a.isComplete='N'",BrngLkpIsComplete.class).getSingleResult();
		Query query = manager.
			      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId);
				long count = (long)query.getSingleResult();
				System.out.println("Count of Order Sequence " +count);
		//Timestamp ts=new Timestamp(System.currentTimeMillis());
		
		
		/* transaction = manager.getTransaction();
		transaction.begin();*/
			brngorder.setBrngLkpIsPaid(brngispaid);
			brngorder.setBrngLkpIsPicked(brngispicked);
			brngorder.setIsAccepted(brngisaccepted);
			brngorder.setBrnglkpisretry(brnglkpisretry);
			brngorder.setBrngLkpIsInsurance(brnglkpinsurance);
			
		//	brngorder.setBrnglkpiscomplete(brnglkpiscomplete);
			
			brngorder.setBrnglkpiscancelled(brngiscancelled);
			System.out.println("order seq"+(int)count+1);
			brngorder.setOrderSeq((int)count+1);
			brngorder.getBrngUsrLogin().setId(userLoginId);
			ArrayList<BigDecimal> payValues =new ArrayList<>();
			/*if(checkFreeStatus == 1)
			{
				payValues=calculatePrice(new BigDecimal("1"));
				brngorder.setTotalPrice(new BigDecimal("1"));
			}*/
			/*if(checkFirstOrderStatus ==0)
			{
				brngorder.setTotalPrice(new BigDecimal("1"));
			}*/
			
				payValues=calculatePrice(BigDecimal.valueOf(Math.ceil(brngorder.getTotalPrice().doubleValue())));
				//brngorder.setGst(calculateGST(brngorder.getTotalPrice()));
				brngorder.setGst(new BigDecimal("0"));

				brngorder.setServicePrice(BigDecimal.valueOf(Math.ceil(payValues.get(0).doubleValue())));
				brngorder.setCompanyPrice(BigDecimal.valueOf(Math.ceil(payValues.get(1).doubleValue())));
				
			
			brngorder.setPayuTxtCharges(new BigDecimal("0"));
			brngorder.setOrderTime(ts);
			brngorder.setTripRating(-1);
			brngorder.setBrnglkppaytype(brnglkppaytype);
			brngorder.setBrngOffer(brngoffer);
		manager.persist(brngorder);
		//transaction.commit();
		
		
		 query= manager.createQuery("Select a.id From BrngOrder a where a.orderSeq="+((int)count+1)+" and a.brngUsrLogin.id="+userLoginId);
		 int orderId = (Integer)query.getSingleResult();
		 ArrayList<String> listOfServiceMan= getListOfServiceMan(brngorder.getFromLatitude(), brngorder.getFromLongitude());
	        NotificationGeneratorImpl obj = new NotificationGeneratorImpl(); 
	        NotificationGenerator ngenerator = new NotificationGeneratorImplA(); 
	        obj.registerNotificationGenerator(ngenerator); 
	        obj.sendNotificationForBooking(brngorder, orderId,listOfServiceMan); 

		//int orderId=brngordertemp.getId();
		/*PushHelper pushhelper=new PushHelper();
		pushhelper.pushToAllServiceMan(getListOfServiceMan(brngorder.getFromLatitude(), brngorder.getFromLongitude()), Integer.toString(orderId));
		*/message=UserMessages.getUserMessagesNew("OPS");
		response.put("message", message);
		response.put("response", Integer.toString(orderId));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}
	

	public HashMap<String,String> bookAnVehicleOrder(BrngVehicleOrder brngvehicleorder,String apiKey){
		
		HashMap<String,String> response=new HashMap<>();
		String message="";
		BrngOffer brngoffer =null;
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		try
		{
			// Made Error 
		// int r=	1/0;
		BrngLkpIsPaid brngispaid = manager.createQuery("Select a From BrngLkpIsPaid a where a.isPaid='N'",BrngLkpIsPaid.class).getSingleResult();
		BrngLkpIsPicked brngispicked = manager.createQuery("Select a From BrngLkpIsPicked a where a.isPicked='N'",BrngLkpIsPicked.class).getSingleResult();
		BrngLkpIsAccepted brngisaccepted = manager.createQuery("Select a From BrngLkpIsAccepted a where a.isAccepted.isAccepted='N'",BrngLkpIsAccepted.class).getSingleResult();
		BrngLkpIsCancelled brngiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).getSingleResult();
		//BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='N'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='Y'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpPayType brnglkppaytype = manager.createQuery("Select a From BrngLkpPayType a where a.type='N'",BrngLkpPayType.class).getSingleResult();
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngvehicleorder.getBrngUsrLogin().getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
BrngLkpIsInsurance brnglkpinsurance=manager.createQuery("Select a From BrngLkpIsInsurance a where a.isInsured='"+brngvehicleorder.getBrngLkpIsInsurance().getIsInsured()+"'",BrngLkpIsInsurance.class).getSingleResult();
		//int checkFreeStatus=checkLimitForFree(userLoginId);
String offerName=brngvehicleorder.getBrngOffer().getName();
if(offerName.toUpperCase().contains("COR"))
{

	//brngorder.getBrngOffer().setName(brngorder.getBrngOffer().getName().toUpperCase());
	 brngoffer=manager.createQuery("select a from BrngOffer a where a.name='CORP'",BrngOffer.class).getSingleResult();
	BrngCorporateOffer brngcorporateoffer=manager.createQuery("Select a from BrngCorporateOffer a where a.couponCode='"+offerName.toUpperCase()+"'",BrngCorporateOffer.class).getSingleResult();
	BrngCorporateOfferTx brngcotx=new BrngCorporateOfferTx();
	brngcotx.setAmount(brngvehicleorder.getDiscountPrice());
	brngcotx.setCouponId(brngcorporateoffer.getId());
	brngcotx.setEffectiveDate(ts);
	
	manager.persist(brngcotx);
	Query query = manager.createQuery("update BrngCorporateOffer set remainingAmount="+brngcorporateoffer.getRemainingAmount().subtract(brngvehicleorder.getDiscountPrice())+" where couponCode='"+offerName.toUpperCase()+"'");
	query.executeUpdate();
}
else
{
	brngoffer=manager.createQuery("select a from BrngOffer a where a.name='"+brngvehicleorder.getBrngOffer().getName()+"'",BrngOffer.class).getSingleResult();
}
		int checkFirstOrderStatus=checkFirstOrderForFree(userLoginId);
	//	BrngLkpIsComplete brnglkpiscomplete = manager.createQuery("Select a From BrngLkpIsComplete a where a.isComplete='N'",BrngLkpIsComplete.class).getSingleResult();
		Query query = manager.
			      createQuery("Select count(*) from BrngVehicleOrder a where a.brngUsrLogin.id="+userLoginId);
				long count = (long)query.getSingleResult();
				System.out.println("Count of Order Sequence " +count);
		//Timestamp ts=new Timestamp(System.currentTimeMillis());
		
		
		/* transaction = manager.getTransaction();
		transaction.begin();*/
			brngvehicleorder.setBrngLkpIsPaid(brngispaid);
			brngvehicleorder.setBrngLkpIsPicked(brngispicked);
			brngvehicleorder.setIsAccepted(brngisaccepted);
			brngvehicleorder.setBrnglkpisretry(brnglkpisretry);
			brngvehicleorder.setBrngLkpIsInsurance(brnglkpinsurance);
			
		//	brngorder.setBrnglkpiscomplete(brnglkpiscomplete);
			
			brngvehicleorder.setBrnglkpiscancelled(brngiscancelled);
			System.out.println("order seq"+(int)count+1);
			brngvehicleorder.setOrderSeq((int)count+1);
			brngvehicleorder.getBrngUsrLogin().setId(userLoginId);
			ArrayList<BigDecimal> payValues =new ArrayList<>();
			/*if(checkFreeStatus == 1)
			{
				payValues=calculatePrice(new BigDecimal("1"));
				brngorder.setTotalPrice(new BigDecimal("1"));
			}*/
			/*if(checkFirstOrderStatus ==0)
			{
				brngorder.setTotalPrice(new BigDecimal("1"));
			}*/
			
				payValues=calculatePrice(BigDecimal.valueOf(Math.ceil(brngvehicleorder.getTotalPrice().doubleValue())));
				//brngorder.setGst(calculateGST(brngorder.getTotalPrice()));
				brngvehicleorder.setGst(new BigDecimal("0"));

				brngvehicleorder.setServicePrice(BigDecimal.valueOf(Math.ceil(payValues.get(0).doubleValue())));
				brngvehicleorder.setCompanyPrice(BigDecimal.valueOf(Math.ceil(payValues.get(1).doubleValue())));
				
			
				brngvehicleorder.setPayuTxtCharges(new BigDecimal("0"));
				brngvehicleorder.setOrderTime(ts);
				brngvehicleorder.setTripRating(-1);
				brngvehicleorder.setBrnglkppaytype(brnglkppaytype);
				brngvehicleorder.setBrngOffer(brngoffer);
		manager.persist(brngvehicleorder);
		//transaction.commit();
		
		
		 query= manager.createQuery("Select a.id From BrngVehicleOrder a where a.orderSeq="+((int)count+1)+" and a.brngUsrLogin.id="+userLoginId);
		 int orderId = (Integer)query.getSingleResult();
		 ArrayList<String> listOfServiceMan= getListOfVehicleServiceMan(brngvehicleorder.getFromLatitude(), brngvehicleorder.getFromLongitude(), brngvehicleorder.getBrnglkpvehicleType().getId());
	        NotificationGeneratorImpl obj = new NotificationGeneratorImpl(); 
	        NotificationGenerator ngenerator = new NotificationGeneratorImplA(); 
	        obj.registerNotificationGenerator(ngenerator); 
	        obj.sendNotificationForVehicleBooking(brngvehicleorder, orderId,listOfServiceMan); 

		//int orderId=brngordertemp.getId();
		/*PushHelper pushhelper=new PushHelper();
		pushhelper.pushToAllServiceMan(getListOfServiceMan(brngorder.getFromLatitude(), brngorder.getFromLongitude()), Integer.toString(orderId));
		*/message=UserMessages.getUserMessagesNew("OPS");
		response.put("message", message);
		response.put("response", Integer.toString(orderId));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

public HashMap<String,Object> bookMultipleOrdersOrder(ArrayList<BrngOrder> brngorder,String apiKey){
		

	//manager manager = factory.createmanager();

	//manager.getTransaction().begin();

	ArrayList<Integer> orderList=new ArrayList<>();
		HashMap<String,Object> response=new HashMap<>();
		String message="";
		try
		{
			
		BrngLkpIsPaid brngispaid = manager.createQuery("Select a From BrngLkpIsPaid a where a.isPaid='N'",BrngLkpIsPaid.class).getSingleResult();
		BrngLkpIsPicked brngispicked = manager.createQuery("Select a From BrngLkpIsPicked a where a.isPicked='N'",BrngLkpIsPicked.class).getSingleResult();
		BrngLkpIsAccepted brngisaccepted = manager.createQuery("Select a From BrngLkpIsAccepted a where a.isAccepted.isAccepted='N'",BrngLkpIsAccepted.class).getSingleResult();
		BrngLkpIsCancelled brngiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).getSingleResult();
		//BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='N'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpIsRetry brnglkpisretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='Y'",BrngLkpIsRetry.class).getSingleResult();
		BrngLkpPayType brnglkppaytype = manager.createQuery("Select a From BrngLkpPayType a where a.type='N'",BrngLkpPayType.class).getSingleResult();
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngorder.get(0).getBrngUsrLogin().getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}


		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		BrngLkpIsInsurance brnglkpinsurance=null;
		BrngOffer brngoffer=null;
		for(int i=0;i<brngorder.size();i++)
		{
			 brnglkpinsurance=manager.createQuery("Select a From BrngLkpIsInsurance a where a.isInsured='"+brngorder.get(i).getBrngLkpIsInsurance().getIsInsured()+"'",BrngLkpIsInsurance.class).getSingleResult();
				//int checkFreeStatus=checkLimitForFree(userLoginId);
		 brngoffer=manager.createQuery("select a from BrngOffer a where a.name='"+brngorder.get(i).getBrngOffer().getName()+"'",BrngOffer.class).getSingleResult();		
				int checkFirstOrderStatus=checkFirstOrderForFree(userLoginId);
			//	BrngLkpIsComplete brnglkpiscomplete = manager.createQuery("Select a From BrngLkpIsComplete a where a.isComplete='N'",BrngLkpIsComplete.class).getSingleResult();
				Query query = manager.
					      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId);
						long count = (long)query.getSingleResult();
						System.out.println("Count of Order Sequence " +count);
				Timestamp ts=new Timestamp(System.currentTimeMillis());
				
				
			//	 transaction = manager.getTransaction();
			//	transaction.begin();
					brngorder.get(i).setBrngLkpIsPaid(brngispaid);
					brngorder.get(i).setBrngLkpIsPicked(brngispicked);
					brngorder.get(i).setIsAccepted(brngisaccepted);
					brngorder.get(i).setBrnglkpisretry(brnglkpisretry);
					brngorder.get(i).setBrngLkpIsInsurance(brnglkpinsurance);
					
				//	brngorder.setBrnglkpiscomplete(brnglkpiscomplete);
					
					brngorder.get(i).setBrnglkpiscancelled(brngiscancelled);
					System.out.println("order seq"+(int)count+1);
					brngorder.get(i).setOrderSeq((int)count+1);
					brngorder.get(i).getBrngUsrLogin().setId(userLoginId);
					ArrayList<BigDecimal> payValues =new ArrayList<>();
					/*if(checkFreeStatus == 1)
					{
						payValues=calculatePrice(new BigDecimal("1"));
						brngorder.setTotalPrice(new BigDecimal("1"));
					}
					if(checkFirstOrderStatus ==0)
					{
						brngorder.setTotalPrice(new BigDecimal("1"));
					}*/
					
						payValues=calculatePrice(brngorder.get(i).getTotalPrice());
						//brngorder.setGst(calculateGST(brngorder.getTotalPrice()));
						brngorder.get(i).setGst(new BigDecimal("0"));

						brngorder.get(i).setServicePrice(payValues.get(0));
						brngorder.get(i).setCompanyPrice(payValues.get(1));
						
					
					brngorder.get(i).setPayuTxtCharges(new BigDecimal("0"));
					brngorder.get(i).setOrderTime(ts);
					brngorder.get(i).setTripRating(-1);
					brngorder.get(i).setBrnglkppaytype(brnglkppaytype);
					brngorder.get(i).setBrngOffer(brngoffer);
				manager.persist(brngorder.get(i));
				
				//transaction.commit();
				
				
				 query= manager.createQuery("Select a.id From BrngOrder a where a.orderSeq="+((int)count+1)+" and a.brngUsrLogin.id="+userLoginId);
				 int orderId = (Integer)query.getSingleResult();
				 orderList.add(orderId);
				 PushHelper pushhelper=new PushHelper();
					pushhelper.pushToAllServiceMan(getListOfServiceMan(brngorder.get(i).getFromLatitude(), brngorder.get(i).getFromLongitude()), Integer.toString(orderId));
					
				// manager.getTransaction().commit();
		}
		//int orderId=brngordertemp.getId();
		message=UserMessages.getUserMessagesNew("OPS");
		response.put("message", message);
		response.put("response", orderList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "1");
		}
		return response;
	}


public HashMap<String,Object> confirmPaymentForMultipleOrders(HashMap<String,Object> inputDetails,String apiKey){
	

	//manager manager = factory.createmanager();

	//manager.getTransaction().begin();

	ArrayList<Integer> orderList=new ArrayList<>();
		HashMap<String,Object> response=new HashMap<>();
		String message="";
		try
		{
			
			String email=(String)inputDetails.get("email");
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}


List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager.createQuery("Select a From BrngLkpIsPaid a",BrngLkpIsPaid.class).
getResultList();
String paidStatus="";		int paidId=0;
List<OrderDeliveryDto> dtoList = new ArrayList();
OrderDeliveryDto orderDeliveryDto = null;
for(BrngLkpIsPaid brngLkpIsPaidType:brngLkpIsPaidTypes){
if(brngLkpIsPaidType.getIsPaid().equalsIgnoreCase("Y"))
{
paidId=brngLkpIsPaidType.getId();
paidStatus=brngLkpIsPaidType.getIsPaid();
break;
}
}
		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		BrngLkpIsInsurance brnglkpinsurance=null;
		BrngOffer brngoffer=null;
		BigDecimal payuTxtCharges =new BigDecimal("0");
		ArrayList<Integer> listOfOrders=(ArrayList<Integer>)inputDetails.get("orders");
		BrngLkpPayType brnglkppaytype=manager.createQuery("Select a From BrngLkpPayType a where a.type= '"+(String)inputDetails.get("type")+"'",BrngLkpPayType.class).getSingleResult();
		for(int i=0;i<listOfOrders.size();i++)
		{
				
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+listOfOrders.get(0),BrngOrder.class).getSingleResult();
			System.out.println( "  " + brngorder1.getBrngLkpIsPaid().getIsPaid());
			if(brngorder1.getBrngLkpIsPaid().getIsPaid().equalsIgnoreCase(paidStatus))
			{
				message=UserMessages.getUserMessagesNew("OAP");
				response.put("message", message);
				response.put("response", "7");
				return response;
			}

			if(((String)inputDetails.get("type")).equalsIgnoreCase("S") || ((String)inputDetails.get("type")).equalsIgnoreCase("R"))
			{
				
			BrngOrder brngOrder=manager.createQuery("Select a From BrngOrder a where a.id= "+listOfOrders.get(0)+"",BrngOrder.class).getSingleResult();
			//int payTypeId=brnglkppaytype.getId();
			payuTxtCharges=calculatePayuMoney(brngOrder.getTotalPrice().add(brngOrder.getGst()));
			Query query = manager
					.createQuery("UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId,a.payuTxtCharges =:payTxtCharges where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
					query.setParameter("isPaid",paidId);
					
					
					query.setParameter("orderId",listOfOrders.get(i));
					query.setParameter("userLoginId",userLoginId);
					query.setParameter("payTxId",((String)inputDetails.get("payTxId")));
					query.setParameter("payTypeId",brnglkppaytype.getId());
					query.setParameter("payTxtCharges",payuTxtCharges);
					
					query.executeUpdate();
					message=UserMessages.getUserMessagesNew("PD");
					response.put("message", message);
					response.put("response", "1");
			}
			else
			{
		Query query = manager
				.createQuery("UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("isPaid",paidId);
				
				
				query.setParameter("orderId",listOfOrders.get(i));
				query.setParameter("userLoginId",userLoginId);
				query.setParameter("payTxId",(inputDetails.get("payTxId")));
				query.setParameter("payTypeId",brnglkppaytype.getId());
				
				query.executeUpdate();
			//transaction.commit();
				message=UserMessages.getUserMessagesNew("PD");
				response.put("message", message);
				response.put("response", "1");
				
			}
						// manager.getTransaction().commit();
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "1");
		}
		return response;
	}


	public List<OrderDto> getOrdersByDate(Hashtable<String,String> inputDetails){
		
		OrderDao odao=new OrderDao();
		int returnValue;
		CommonUtility cm=new CommonUtility();
		try {
			 returnValue=cm.checkDates(inputDetails);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
List<OrderDto> dtoList = new ArrayList();	
try{
	

		int userId=brngusrreg.getId();
		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		BrngLkpIsRetry brnglkpsiretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='Y'",BrngLkpIsRetry.class).
				getSingleResult();
		
		System.out.println(userId);
		
		BrngLkpIsAccepted brnglkpisacceted = manager.createQuery("Select a From BrngLkpIsAccepted a where a.isAccepted='Y'",BrngLkpIsAccepted.class).
				getSingleResult();
		
		
		BrngLkpIsCancelled brnglkpiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).
				getSingleResult();
		
		System.out.println("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and order_time between date('"+inputDetails.get("startDate")+ "') and DATE_ADD(date('"+inputDetails.get("endDate")+ "'),INTERVAL 1 DAY)");
		//List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and a.orderTime between date('"+inputDetails.get("startDate")+ "') and date('"+inputDetails.get("endDate")+ "')+1 ",BrngOrder.class).
		List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and (a.brnglkpisretry.id="+brnglkpsiretry.getId()+ " or a.isAccepted.id="+brnglkpisacceted.getId()+") and a.orderTime between date('"+inputDetails.get("startDate")+ "') and date('"+inputDetails.get("endDate")+ "')+1 and a.brnglkpiscancelled.id="+ brnglkpiscancelled.getId()+" order by a.orderTime desc",BrngOrder.class).
				
				getResultList();
	
		OrderDto orderDto = null;
		//System.out.println(brngOrders.get(0).getCompanyPrice());
		for(BrngOrder brngOrder:brngOrders){
			//odao.getCodes("A", brngOrder.getIsAccepted());
			
			orderDto = new OrderDto(brngOrder);
			dtoList.add(orderDto);
		}
}
catch(Exception e)
{
	return dtoList;
}
		return dtoList;
	}
	
public List<OrderDto> getOrdersByDateServiceMan(Hashtable<String,String> inputDetails){
		
	List<OrderDto> dtoList1 = new ArrayList();
	
		OrderDao odao=new OrderDao();
	try
	{
BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
				getSingleResult();
		
		
		int completeId=brnglkporderdelstatus.getId();
		
		BrngLkpIsCancelled brnglkpiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).
				getSingleResult();
		
		//Query query= manager.createQuery("Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngUsrLogin.id="+userLoginId+ " and a.orderTime between date('"+inputDetails.get("startDate")+ "') and date('"+inputDetails.get("endDate")+"')");
		Query query= manager.createQuery("Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngUsrLogin.id="+userLoginId+ " and a.orderTime between date('"+inputDetails.get("startDate")+ "') and date('"+inputDetails.get("endDate")+"') +1 and a.brnglkpiscancelled.id="+ brnglkpiscancelled.getId()+" order by a.orderTime desc");
		
		List<BrngOrder> orders = (List<BrngOrder>) query.getResultList();
		
		
		//System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
		
		
		OrderDto orderdto = null;
		//System.out.println(brngOrders.get(0).getCompanyPrice());
		for(BrngOrder order:orders){
			
			orderdto = new OrderDto(order);
			dtoList1.add(orderdto);
		}
		
		/*System.out.println(userId);
		System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
		List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and order_time between date('"+inputDetails.get("startDate")+ "') and date('"+inputDetails.get("endDate")+ "')",BrngOrder.class).
				getResultList();
		List<OrderDto> dtoList = new ArrayList();
		OrderDto orderDto = null;
		//System.out.println(brngOrders.get(0).getCompanyPrice());
		for(BrngOrder brngOrder:brngOrders){
			//odao.getCodes("A", brngOrder.getIsAccepted());
			
			orderDto = new OrderDto(brngOrder);
			dtoList.add(orderDto);
		}*/
	}
	catch(Exception e)
	{
		return dtoList1;
	}
		//return dtoList;
		return dtoList1;
	}
	
	
	public List<OrderDto> getCurrentOrdersCustomers(Hashtable<String,String> inputDetails,String apiKey){
		
		List<OrderDeliveryDto> dtoList = new ArrayList();
		List<OrderDto> dtoList1 = new LinkedList<OrderDto>();
		
		int acceptanceId=0;
		int nonacceptanceId=0;
		try
		{
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
				
				int userId=brngusrreg.getId();
				
				int ret=checkAuthentication(apiKey, userId);
		System.out.println(ret +" ret");
		if(ret==0)
		{
			return dtoList1;
		}
		else if(ret==-1)
		{
			return dtoList1;

		}

		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
				
				int userLoginId=brngusrlogin.getId();
				
				System.out.println(userId);
				List<BrngLkpIsAccepted> brngLkpIsAcceptedTypes = manager.createQuery("Select a From BrngLkpIsAccepted a",BrngLkpIsAccepted.class).
						getResultList();
				
				OrderDeliveryDto orderDeliveryDto = null;
				for(BrngLkpIsAccepted brngLkpIsAcceptedType:brngLkpIsAcceptedTypes){
					if(brngLkpIsAcceptedType.getIsAccepted().equalsIgnoreCase("N"))
					{
						nonacceptanceId=brngLkpIsAcceptedType.getId();
					}
					if(brngLkpIsAcceptedType.getIsAccepted().equalsIgnoreCase("Y"))
					{
						acceptanceId=brngLkpIsAcceptedType.getId();
						
						
					}
				}
				BrngLkpIsRetry brnglkpsiretry = manager.createQuery("Select a From BrngLkpIsRetry a where a.isRetry='Y'",BrngLkpIsRetry.class).
						getSingleResult();
				System.out.println("Retry "+brnglkpsiretry.getId());
				BrngLkpIsCancelled brnglkpiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).
						getSingleResult();
				int cancellationId=brnglkpiscancelled.getId();
				System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
				
				BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='N'",BrngLkpOrderDelStatus.class).
						getSingleResult();
				int completeId=brnglkporderdelstatus.getId();
				System.out.println("nonaccept "+nonacceptanceId);
				//Query query= manager.createQuery("Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and a.brngUsrLogin.id="+userLoginId+" and a.isAccepted.id="+acceptanceId+" and a.brnglkpiscancelled.id="+cancellationId + "union Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and a.brngUsrLogin.id="+userLoginId+" and a.brnglkpisretry.id="+brnglkpsiretry.getId()+" and a.brnglkpiscancelled.id="+cancellationId);
				//Query query= manager.createQuery("Select distinct a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and a.brngUsrLogin.id="+userLoginId+" and a.isAccepted.id="+acceptanceId+" and a.brnglkpiscancelled.id="+cancellationId +"  or (a.brngUsrLogin.id="+userLoginId+"  and a.brnglkpisretry.id="+brnglkpsiretry.getId()+")");
				Query query= manager.createQuery("Select distinct a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and a.brngUsrLogin.id="+userLoginId+" and a.isAccepted.id="+acceptanceId+" and a.brnglkpiscancelled.id="+cancellationId+" order by a.orderTime desc"  );
				 
						System.out.println("query ");
				List<BrngOrder> orders = (List<BrngOrder>) query.getResultList();
				
				
				//System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
				
				
				OrderDto orderdto = null;
				//System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder order:orders){
					
					orderdto = new OrderDto(order);
					dtoList1.add(orderdto);
				}
			query=manager.createQuery(" Select distinct a From BrngOrder a  where    a.brnglkpisretry.id="+brnglkpsiretry.getId()+ " and a.brngUsrLogin.id="+userLoginId+" and a.brnglkpiscancelled.id="+cancellationId +" and a.isAccepted="+nonacceptanceId );
			 orders = (List<BrngOrder>) query.getResultList();
			  orderdto = null;
			  for(BrngOrder order:orders){
					
					orderdto = new OrderDto(order);
					dtoList1.add(orderdto);
				}
				/*List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and a.isAccepted.id="+acceptanceId+" and a.brnglkpiscancelled.id="+cancellationId,BrngOrder.class).
						getResultList();
				List<OrderDto> dtoList1 = new ArrayList();
				OrderDto orderDto = null;
				//System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder brngOrder:brngOrders){
					
					orderDto = new OrderDto(brngOrder);
					dtoList1.add(orderDto);
				}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return dtoList1;
		}
				return dtoList1;
			}
			
	
	
		public List<OrderDto> getPastOrdersCustomers(Hashtable<String,String> inputDetails,String apiKey){
		
		int completedId=0;
		List<OrderDto> dtoList1 = new LinkedList<>();
		List<OrderDeliveryDto> dtoList = new ArrayList();
		try
		{
		StringBuffer listorders=new StringBuffer("(");
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
				
				int userId=brngusrreg.getId();
				
				int ret=checkAuthentication(apiKey, userId);
		System.out.println(ret +" ret");
		if(ret==0)
		{
			return dtoList1;
		}
		else if(ret==-1)
		{
			return dtoList1;
		}

		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
				
				int userLoginId=brngusrlogin.getId();
				
				System.out.println(userId);
			
				
				OrderDeliveryDto orderDeliveryDto = null;
				
				BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
						getSingleResult();
				int completeId=brnglkporderdelstatus.getId();
				
				Query query= manager.createQuery("Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and a.brngUsrLogin.id="+userLoginId+" order by a.orderTime desc");
						
				List<BrngOrder> orders = (List<BrngOrder>) query.getResultList();
				
				
				//System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
				
				
				OrderDto orderdto = null;
				//System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder order:orders){
					
					orderdto = new OrderDto(order);
					orderdto.setOrderStatus("Completed");
					dtoList1.add(orderdto);
				}
		}catch(Exception e)
		{
			return dtoList1;
		}
		return dtoList1;
			}
		
		

		public List<OrderDto> getPastOrdersServiceMan(Hashtable<String,String> inputDetails){
		
		int completedId=0;
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
				
				int userId=brngusrreg.getId();
				
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
				
				int userLoginId=brngusrlogin.getId();
				
				System.out.println(userId);
				BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
						getSingleResult();
				int completeId=brnglkporderdelstatus.getId();
				
				//System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
				Query query= manager.createQuery("Select a From BrngOrder a , BrngOrderDelivery b where a.id=b.brngOrder.id and b.brngLkpOrderDelStatus.id="+completeId+" and b.brngUsrLogin.id="+userLoginId+" order by a.orderTime desc");
				
				
				List<BrngOrder> orders = (List<BrngOrder>) query.getResultList();
				
				
				//System.out.println("query -Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId+" and orderTime >= "+inputDetails.get("startDate")+ " and orderTime <="+inputDetails.get("endDate"));
				
				List<OrderDto> dtoList1 = new LinkedList<>();
				OrderDto orderdto = null;
				//System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder order:orders){
					
					orderdto = new OrderDto(order);
					orderdto.setOrderStatus("Completed");
					dtoList1.add(orderdto);
				}
				return dtoList1;
			}
public List<OrderDto> getCurrentOrdersService(Hashtable<String,String> inputDetails){
		
		int deliveredId=0;
		String orderList="(";
		List<OrderDto> dtoList1 = new LinkedList<>();
		try
		{
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
				
				int userId=brngusrreg.getId();
				
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
				
				int userLoginId=brngusrlogin.getId();
			
				System.out.println(userId);
				BrngLkpOrderDelStatus brngLkpDeliveredTypes = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='N'",BrngLkpOrderDelStatus.class).
						getSingleResult();
				deliveredId= brngLkpDeliveredTypes.getId();
				System.out.println("deliver ID" +deliveredId);
				
				List<BrngOrderDelivery> brngDelOrders = manager.createQuery("Select a From BrngOrderDelivery a where a.brngUsrLogin.id="+userLoginId+" and a.brngLkpOrderDelStatus.id="+deliveredId,BrngOrderDelivery.class).
						getResultList();
				
				for(int i=0;i<brngDelOrders.size();i++ )
				{
					if(i==brngDelOrders.size()-1)
					{
						orderList=orderList+brngDelOrders.get(i).getBrngOrder().getId()+")";
					}
					else
					{
					orderList=orderList+brngDelOrders.get(i).getBrngOrder().getId()+",";
					}
				}
				
				if(orderList.equalsIgnoreCase("("))
				{
					return dtoList1;
				}
				System.out.println("orders list"+orderList);
				BrngLkpIsCancelled brnglkpiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='N'",BrngLkpIsCancelled.class).
						getSingleResult();
				int cancellationId=brnglkpiscancelled.getId();
				List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where  a.id in "+orderList +" and a.brnglkpiscancelled.id="+cancellationId+" order by a.orderTime desc ",BrngOrder.class).
						getResultList();
			
				OrderDto orderDto = null;
				//System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder brngOrder:brngOrders){
					
					orderDto = new OrderDto(brngOrder);
					dtoList1.add(orderDto);
				}
		}
		catch(Exception e)
		{
			return dtoList1;
		}
		return dtoList1;
			}
			

	public List<OrderDto> getOrdersById(String email,String apiKey){
		
		List<OrderDto> dtoList = new ArrayList();
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
				
				int userId=brngusrreg.getId();
				
				
				int ret=checkAuthentication(apiKey, brngusrreg.getId());
		System.out.println(ret +" ret");
		if(ret==0)
		{
			return dtoList;
		}
		else if(ret==-1)
		{
			return dtoList;

		}

				
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
				
				int userLoginId=brngusrlogin.getId();
				
				System.out.println(userId);
				List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngOrder a where a.brngUsrLogin.id="+userLoginId,BrngOrder.class).
						getResultList();
		
				OrderDto orderDto = null;
				System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder brngOrder:brngOrders){
					
					orderDto = new OrderDto(brngOrder);
					dtoList.add(orderDto);
				}
				return dtoList;
			}
	
	
public List<OrderDto> getOrderDetailsById(int orderId,String apiKey){
		
	List<OrderDto> dtoList = new ArrayList();
	try
	{
				List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngO"
						+ "rder a where a.id="+orderId,BrngOrder.class).
						getResultList();
				
				OrderDto orderDto = null;
				int loginId=brngOrders.get(0).getBrngUsrLogin().getId();
				BrngUsrLogin brngusrlogin= manager.createQuery("Select a From BrngUsrLogin a where a.id="+loginId,BrngUsrLogin.class).
						getSingleResult();
				int regId=brngusrlogin.getBrngUsrReg().getId();

				int ret=checkAuthentication(apiKey, regId);
		System.out.println(ret +" ret");
		if(ret==0)
		{
			return dtoList;
		}
		else if(ret==-1)
		{
			return dtoList;
		}
				System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder brngOrder:brngOrders){
					orderDto = new OrderDto(brngOrder);
					
					
					dtoList.add(orderDto);
				}
				if(orderDto.getIsAccepted().equalsIgnoreCase("Y"))
				{
					Query query = manager.
						      createQuery("Select count(*) from BrngOrderDelivery a where a.brngOrder.id="+orderId);
							long count = (long)query.getSingleResult();
							if(count==0)
							{
								
							}
							else
							{
				BrngOrderDelivery brngOrderDelivery = manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id="+orderId,BrngOrderDelivery.class).
						getSingleResult();
				
				System.out.println(brngOrderDelivery.getBrngLkpOrderDelStatus().getId());
				if(brngOrderDelivery.getBrngLkpOrderDelStatus().getId()==1)
				{
					orderDto.setOrderStatus("Completed");
				}

				BrngUsrRegAttr brngusrregattr= manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id="+brngOrderDelivery.getBrngUsrLogin().getBrngUsrReg().getId(),BrngUsrRegAttr.class).
						getSingleResult();
				
				BrngServicemanLocationDtls brngservicemandlocationdtls=manager.createQuery("Select a From BrngServicemanLocationDtls a where a.brngUsrLogin.id="+brngOrderDelivery.getBrngUsrLogin().getId(),BrngServicemanLocationDtls.class).
						getSingleResult();
				List<BrngUsrFiles> brngusrfiles=manager.createQuery("Select a From BrngUsrFiles a where a.brngUsrReg.id="+brngusrregattr.getBrngUsrReg().getId()+"order by a.effectiveDate desc ",BrngUsrFiles.class).
						getResultList();
				orderDto.setDriverName(brngusrregattr.getFirstName()+ " "+brngusrregattr.getLastName());
				orderDto.setDriverNumber(brngusrregattr.getPhoneNumber());
				orderDto.setDriverCurrentLat(brngservicemandlocationdtls.getLat());
				orderDto.setDriverCurrentLng(brngservicemandlocationdtls.getLng());
				orderDto.setDeliveryStatus(brngOrderDelivery.getBrngLkpOrderDelStatus().getDelStatus());
				orderDto.setDriverImage(brngusrfiles.get(0).getImage());
							}
				}
	}
	catch(Exception e)
	{
		return dtoList;
	}
						return dtoList;
			}

//Only For Player Id Method
public List<OrderDto> getOrderDetailsById(int orderId){
	
	List<OrderDto> dtoList = new ArrayList();
	try
	{
				List<BrngOrder> brngOrders = manager.createQuery("Select a From BrngO"
						+ "rder a where a.id="+orderId,BrngOrder.class).
						getResultList();
				
				OrderDto orderDto = null;
				System.out.println(brngOrders.get(0).getCompanyPrice());
				for(BrngOrder brngOrder:brngOrders){
					
					orderDto = new OrderDto(brngOrder);
					dtoList.add(orderDto);
				}
				if(orderDto.getIsAccepted().equalsIgnoreCase("Y"))
				{
					Query query = manager.
						      createQuery("Select count(*) from BrngOrderDelivery a where a.brngOrder.id="+orderId);
							long count = (long)query.getSingleResult();
							if(count==0)
							{
								
							}
							else
							{
				BrngOrderDelivery brngOrderDelivery = manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id="+orderId,BrngOrderDelivery.class).
						getSingleResult();
				int loginId=brngOrderDelivery.getBrngUsrLogin().getId();
				BrngUsrLogin brngusrlogin= manager.createQuery("Select a From BrngUsrLogin a where a.id="+loginId,BrngUsrLogin.class).
						getSingleResult();
				int regId=brngusrlogin.getBrngUsrReg().getId();
				
				BrngUsrRegAttr brngusrregattr= manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id="+regId,BrngUsrRegAttr.class).
						getSingleResult();
				
				BrngServicemanLocationDtls brngservicemandlocationdtls=manager.createQuery("Select a From BrngServicemanLocationDtls a where a.brngUsrLogin.id="+loginId,BrngServicemanLocationDtls.class).
						getSingleResult();
				List<BrngUsrFiles> brngusrfiles=manager.createQuery("Select a From BrngUsrFiles a where a.brngUsrReg.id="+brngusrregattr.getBrngUsrReg().getId()+"order by a.effectiveDate desc ",BrngUsrFiles.class).
						getResultList();
				orderDto.setDriverName(brngusrregattr.getFirstName()+ " "+brngusrregattr.getLastName());
				orderDto.setDriverNumber(brngusrregattr.getPhoneNumber());
				orderDto.setDriverCurrentLat(brngservicemandlocationdtls.getLat());
				orderDto.setDriverCurrentLng(brngservicemandlocationdtls.getLng());
				orderDto.setDeliveryStatus(brngOrderDelivery.getBrngLkpOrderDelStatus().getDelStatus());
				orderDto.setDriverImage(brngusrfiles.get(0).getImage());
							}
				}
	}
	catch(Exception e)
	{
		return dtoList;
	}
						return dtoList;
			}


	
	public ArrayList<BigDecimal> calculatePrice(BigDecimal totalPrice )
	{
		System.out.println(" Price ");
		MathContext mc = new MathContext(4);
		BigDecimal constantDivisor=new BigDecimal("100");
		List<OrderDto> getPayvalue=getpayPercent();
		BigDecimal payPercent=getPayvalue.get(0).getPayPercent();
		ArrayList<BigDecimal> listOfPayValues=new ArrayList<>();
		listOfPayValues.add((constantDivisor.subtract(payPercent).multiply(totalPrice)).divide(constantDivisor));
		listOfPayValues.add((payPercent.multiply(totalPrice)).divide(constantDivisor));
		System.out.println(" listOfPayValues "+listOfPayValues);
		return listOfPayValues;
}
	
	public BigDecimal calculateGST(BigDecimal totalPrice )
	{
		System.out.println(" Price ");
		MathContext mc = new MathContext(4);
		BigDecimal constantDivisor=new BigDecimal("100");
		List<OrderDto> getPayvalue=getpayPercent();
		BigDecimal payPercent=new BigDecimal("18");
		
		BigDecimal gstAmount=payPercent.multiply(totalPrice).divide(constantDivisor);
		System.out.println(" gstAmount "+gstAmount);
		return gstAmount;
}
	
	
	public BigDecimal calculatePayuMoney(BigDecimal totalPrice )
	{
		System.out.println(" Price ");
		MathContext mc = new MathContext(4);
		BigDecimal constantDivisor=new BigDecimal("100");
		List<OrderDto> getPayvalue=getpayPercent();
		BigDecimal payPercent=new BigDecimal("2.9");
		
		BigDecimal payTxtCharge=payPercent.multiply(totalPrice).divide(constantDivisor);
		System.out.println(" payu txt charge "+payTxtCharge);
		return payTxtCharge;
}


	public HashMap<String,String> confirmPayment(Hashtable<String,String> inputDetails,String apiKey){
		int paidId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		String paidStatus="";
		try
		{
			if(getCancelStatus(Integer.parseInt(inputDetails.get("orderId")))==1)
			{
				message=UserMessages.getUserMessagesNew("OAC");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
			List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager.createQuery("Select a From BrngLkpIsPaid a",BrngLkpIsPaid.class).
					getResultList();
			List<OrderDeliveryDto> dtoList = new ArrayList();
			OrderDeliveryDto orderDeliveryDto = null;
			for(BrngLkpIsPaid brngLkpIsPaidType:brngLkpIsPaidTypes){
				if(brngLkpIsPaidType.getIsPaid().equalsIgnoreCase("Y"))
				{
					paidId=brngLkpIsPaidType.getId();
					paidStatus=brngLkpIsPaidType.getIsPaid();
					break;
				}
			}
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
			System.out.println( "  " + brngorder1.getBrngLkpIsPaid().getIsPaid());
			if(brngorder1.getBrngLkpIsPaid().getIsPaid().equalsIgnoreCase(paidStatus))
			{
				message=UserMessages.getUserMessagesNew("OAP");
				response.put("message", message);
				response.put("response", "7");
				return response;
			}

		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		BrngLkpPayType brnglkppaytype=manager.createQuery("Select a From BrngLkpPayType a where a.type= '"+inputDetails.get("type")+"'",BrngLkpPayType.class).getSingleResult();
		int payTypeId=brnglkppaytype.getId();
		System.out.println(" user Login Id"+userLoginId);
		BigDecimal payuTxtCharges =new BigDecimal("0");
		if(inputDetails.get("type").equalsIgnoreCase("C"))
		{
		BrngOrder brngOrder=manager.createQuery("Select a From BrngOrder a where a.id= "+Integer.parseInt(inputDetails.get("orderId"))+"",BrngOrder.class).getSingleResult();
		
		payuTxtCharges=calculatePayuMoney(brngOrder.getTotalPrice().add(brngOrder.getGst()));
		Query query = manager
				.createQuery("UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId,a.payuTxtCharges =:payTxtCharges where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("isPaid",paidId);
				
				
				query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
				query.setParameter("userLoginId",userLoginId);
				query.setParameter("payTxId",(inputDetails.get("payTxId")));
				query.setParameter("payTypeId",payTypeId);
				query.setParameter("payTxtCharges",payuTxtCharges);
				
				query.executeUpdate();
		}
		else
		{
	Query query = manager
			.createQuery("UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
			query.setParameter("isPaid",paidId);
			
			
			query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
			query.setParameter("userLoginId",userLoginId);
			query.setParameter("payTxId",(inputDetails.get("payTxId")));
			query.setParameter("payTypeId",payTypeId);
			
			query.executeUpdate();
			message=UserMessages.getUserMessagesNew("PD");
			response.put("message", message);
			response.put("response", "1");
			
		//transaction.commit();
		}	
			/*BrngOrderDelivery brngorderdelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id= '"+inputDetails.get("orderId")+"'",BrngOrderDelivery.class).getSingleResult();
			BrngUsrLogin brngusrlogin1=manager.createQuery("Select a From BrngUsrLogin a where a.id= '"+brngorderdelivery.getBrngUsrLogin().getId()+"'",BrngUsrLogin.class).getSingleResult();
		
			message=UserMessages.getUserMessagesNew("PD");
			response.put("message", message);
			response.put("response", "1");
			PushHelper pushhelper=new PushHelper();
			pushhelper.pushToUser("notifyservicemanoforders", brngusrlogin1.getPlayerId(), "Payment done by customer. Order No",Integer.toString(brngorder1.getId()));
		*/}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	public HashMap<String,String> confirmPaymentForVehicle(Hashtable<String,String> inputDetails,String apiKey){
		int paidId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		String paidStatus="";
		try
		{
			if(getCancelStatusForVehicles(Integer.parseInt(inputDetails.get("orderId")))==1)
			{
				message=UserMessages.getUserMessagesNew("OAC");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
			List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager.createQuery("Select a From BrngLkpIsPaid a",BrngLkpIsPaid.class).
					getResultList();
			List<OrderDeliveryDto> dtoList = new ArrayList();
			OrderDeliveryDto orderDeliveryDto = null;
			for(BrngLkpIsPaid brngLkpIsPaidType:brngLkpIsPaidTypes){
				if(brngLkpIsPaidType.getIsPaid().equalsIgnoreCase("Y"))
				{
					paidId=brngLkpIsPaidType.getId();
					paidStatus=brngLkpIsPaidType.getIsPaid();
					break;
				}
			}
			BrngVehicleOrder brngorder1=manager.createQuery("Select a From BrngVehicleOrder a where a.id= "+inputDetails.get("orderId"),BrngVehicleOrder.class).getSingleResult();
			System.out.println( "  " + brngorder1.getBrngLkpIsPaid().getIsPaid());
			if(brngorder1.getBrngLkpIsPaid().getIsPaid().equalsIgnoreCase(paidStatus))
			{
				message=UserMessages.getUserMessagesNew("OAP");
				response.put("message", message);
				response.put("response", "7");
				return response;
			}

		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		BrngLkpPayType brnglkppaytype=manager.createQuery("Select a From BrngLkpPayType a where a.type= '"+inputDetails.get("type")+"'",BrngLkpPayType.class).getSingleResult();
		int payTypeId=brnglkppaytype.getId();
		System.out.println(" user Login Id"+userLoginId);
		BigDecimal payuTxtCharges =new BigDecimal("0");
		if(inputDetails.get("type").equalsIgnoreCase("C"))
		{
			BrngVehicleOrder brngOrder=manager.createQuery("Select a From BrngVehicleOrder a where a.id= "+Integer.parseInt(inputDetails.get("orderId"))+"",BrngVehicleOrder.class).getSingleResult();
		
		payuTxtCharges=calculatePayuMoney(brngOrder.getTotalPrice().add(brngOrder.getGst()));
		Query query = manager
				.createQuery("UPDATE BrngVehicleOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId,a.payuTxtCharges =:payTxtCharges where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("isPaid",paidId);
				
				
				query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
				query.setParameter("userLoginId",userLoginId);
				query.setParameter("payTxId",(inputDetails.get("payTxId")));
				query.setParameter("payTypeId",payTypeId);
				query.setParameter("payTxtCharges",payuTxtCharges);
				
				query.executeUpdate();
		}
		else
		{
	Query query = manager
			.createQuery("UPDATE BrngVehicleOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
			query.setParameter("isPaid",paidId);
			
			
			query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
			query.setParameter("userLoginId",userLoginId);
			query.setParameter("payTxId",(inputDetails.get("payTxId")));
			query.setParameter("payTypeId",payTypeId);
			
			query.executeUpdate();
			message=UserMessages.getUserMessagesNew("PD");
			response.put("message", message);
			response.put("response", "1");
			
		//transaction.commit();
		}	
			/*BrngOrderDelivery brngorderdelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id= '"+inputDetails.get("orderId")+"'",BrngOrderDelivery.class).getSingleResult();
			BrngUsrLogin brngusrlogin1=manager.createQuery("Select a From BrngUsrLogin a where a.id= '"+brngorderdelivery.getBrngUsrLogin().getId()+"'",BrngUsrLogin.class).getSingleResult();
		
			message=UserMessages.getUserMessagesNew("PD");
			response.put("message", message);
			response.put("response", "1");
			PushHelper pushhelper=new PushHelper();
			pushhelper.pushToUser("notifyservicemanoforders", brngusrlogin1.getPlayerId(), "Payment done by customer. Order No",Integer.toString(brngorder1.getId()));
		*/}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	
	public HashMap<String,Object> generateCheckSum(Hashtable<String,String> inputDetails,String apiKey){
		int paidId=0;
		HashMap<String,Object> response =new HashMap<>();
		String message="";
		String paidStatus="";
		try
		{
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
			System.out.println( "  " + brngorder1.getBrngLkpIsPaid().getIsPaid());
			if(brngorder1.getBrngLkpIsPaid().getIsPaid().equalsIgnoreCase(paidStatus))
			{
				message=UserMessages.getUserMessagesNew("OAP");
				response.put("message", message);
				response.put("response", "7");
				return response;
			}
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		BrngUsrRegAttr regattr=manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id= "+brngusrreg.getId(),BrngUsrRegAttr.class).getSingleResult();
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, userId);
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

	BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
	//PaytmCheckSumGenerator paytmcg=new 	PaytmCheckSumGenerator();
	//TreeMap<String,String> checkSum=paytmcg.getCheckSum(inputDetails.get("orderId"), Integer.toString(brngusrreg.getId()), inputDetails.get("amount"),brngusrreg.getEmailId(), regattr.getPhoneNumber());
		
	response.put("checkSum", "0");
	response.put("response", "1");
	response.put("custId", Integer.toString(userId));
	response.put("phone", brngusrreg.getBrngUsrRegAttrs().get(0).getPhoneNumber());
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}


	
	
	public HashMap<String,String> retryOrder(Hashtable<String,String> inputDetails){
		int retryId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		String paidStatus="";
		try
		{
			System.out.println(" Order Id In Retry "+inputDetails.get("orderId"));
			System.out.println(" cancel status"+getCancelStatus(Integer.parseInt(inputDetails.get("orderId"))));
			if(getCancelStatus(Integer.parseInt(inputDetails.get("orderId")))==1)
			{
				message=UserMessages.getUserMessagesNew("OAC");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
			List<BrngLkpIsRetry> brngLkpIsretryTypes = manager.createQuery("Select a From BrngLkpIsRetry a",BrngLkpIsRetry.class).
					getResultList();
			List<OrderDeliveryDto> dtoList = new ArrayList();
			OrderDeliveryDto orderDeliveryDto = null;
			for(BrngLkpIsRetry brngLkpIsRetry:brngLkpIsretryTypes){
				if(brngLkpIsRetry.getIsRetry().equalsIgnoreCase("Y"))
				{
					retryId=brngLkpIsRetry.getId();
					//paidStatus=brngLkpIsPaidType.getIsPaid();
					break;
				}
			}
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
			System.out.println( "  " + brngorder1.getBrngLkpIsPaid().getIsPaid());
			if(brngorder1.getIsAccepted().getIsAccepted().equalsIgnoreCase("Y"))
			{
				message=UserMessages.getUserMessagesNew("OAA");
				response.put("message", message);
				response.put("response", "4");
				return response;
			}

		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		Query query = manager
			.createQuery("UPDATE BrngOrder a SET a.brnglkpisretry.id =:isRetry where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
			query.setParameter("isRetry",retryId);
			
			
			query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
			query.setParameter("userLoginId",userLoginId);
			
			query.executeUpdate();
			//BrngOrder brngordertemp=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
		//transaction.commit();
			PushHelper pushhelper=new PushHelper();
			pushhelper.pushToAllServiceMan(getListOfServiceMan(brngorder1.getFromLatitude(), brngorder1.getFromLongitude()), Integer.toString(brngorder1.getId()));
			message=UserMessages.getUserMessagesNew("OR");
			response.put("message", message);
			response.put("response", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	public HashMap<String,String> retryMultipleOrder(Hashtable<String,Object> inputDetails){
		int retryId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		String paidStatus="";
		try
		{
			ArrayList<Integer> listOfOrders=(ArrayList<Integer>)inputDetails.get("orderList");
			
			List<BrngLkpIsRetry> brngLkpIsretryTypes = manager.createQuery("Select a From BrngLkpIsRetry a",BrngLkpIsRetry.class).
					getResultList();
			for(BrngLkpIsRetry brngLkpIsRetry:brngLkpIsretryTypes){
				if(brngLkpIsRetry.getIsRetry().equalsIgnoreCase("Y"))
				{
					retryId=brngLkpIsRetry.getId();
					//paidStatus=brngLkpIsPaidType.getIsPaid();
					break;
				}
			}
			
			
			

		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		Query query =null;
		for(int i=0;i<listOfOrders.size();i++)
		{

			
			 query = manager
				.createQuery("UPDATE BrngOrder a SET a.brnglkpisretry.id =:isRetry where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("isRetry",retryId);
				
				
				query.setParameter("orderId",listOfOrders.get(i));
				query.setParameter("userLoginId",userLoginId);
				
				query.executeUpdate();
				
		}	//BrngOrder brngordertemp=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
		//transaction.commit();
			message=UserMessages.getUserMessagesNew("OR");
			response.put("message", message);
			response.put("response", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}
	
	public HashMap<String,String> completeTrip(Hashtable<String,String> inputDetails){
		int paidId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
		{
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();

			/*if(brngorder1.getBrngLkpIsPicked().getIsPicked().equalsIgnoreCase("Y"))
			{
				message=UserMessages.getUserMessages(7);
				response.put("message", message);
				response.put("response", "7");
				return response;
			}*/

		BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
				getSingleResult();
		int completeId=brnglkporderdelstatus.getId();
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		
		System.out.println(" user Login Id"+userLoginId);
	Query query = manager
			.createQuery("UPDATE BrngOrderDelivery a SET a.brngLkpOrderDelStatus.id =:iscomplete where a.brngOrder.id=:orderId");
			
			query.setParameter("orderId",inputDetails.get("orderId"));
			query.setParameter("iscomplete",completeId);
			
			
			query.executeUpdate();
		//transaction.commit();
				
				
			message=UserMessages.getUserMessagesNew("TC");
			response.put("message", message);
			response.put("response", "1");
			//PushHelper pushhelper=new PushHelper();
			//pushhelper.pushToUser("notifyservicemanoforders", brngorder1.getBrngUsrLogin().getPlayerId(), "Trip Completed",Integer.toString(brngorder1.getId()));
		//	pushhelper.pushToUser("notifybuyer", inputDetails.get("orderId"), "Trip Completed");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}
	
	public ArrayList<String> getListOfServiceMan(String fromLat,String fromLng)
	{
		double testRadius=200;
		StringBuffer listOfLogins=new StringBuffer("(");
		ArrayList<String> listOfLoginIds=new ArrayList<>();
		BrngLkpUsrType brnglkpusrtype=manager.createQuery("Select a From BrngLkpUsrType a where a.usrType= 'S'",BrngLkpUsrType.class).getSingleResult();
		int serviceManTypeId=brnglkpusrtype.getId();
		try
		{
			List<BrngUsrLogin> brngusrlogins =manager.createQuery("Select a From BrngUsrLogin a where a.logoutTime is NULL and a.brngLkpUsrType.id="+serviceManTypeId,BrngUsrLogin.class).getResultList();
			
			
			for(int i=0;i<brngusrlogins.size();i++)
			{
				if(i==brngusrlogins.size()-1)
				{
					listOfLogins=listOfLogins.append(brngusrlogins.get(i).getId()+")");
				}
				else
				{
					listOfLogins=listOfLogins.append(brngusrlogins.get(i).getId()+",");
				}
			}
			
			System.out.println(":    listOfLogins  "+listOfLogins);
			List<BrngServicemanLocationDtls> brngserviceManDetails = manager.createQuery("Select a From BrngServicemanLocationDtls a where a.brngUsrLogin.id in "+listOfLogins,BrngServicemanLocationDtls.class).getResultList();
					
			
			DistanceChecker distancechecker=new DistanceChecker();
			
			for(BrngServicemanLocationDtls BrngServicemanLocationDtl:brngserviceManDetails){
				Double distance=distancechecker.distanceBetween(fromLat,fromLng,BrngServicemanLocationDtl.getLat(),BrngServicemanLocationDtl.getLng());
				if(distance<=testRadius)
				{
					listOfLoginIds.add(BrngServicemanLocationDtl.getBrngUsrLogin().getPlayerId());
					/*BrngUpcomingOrder brngupcomingorder=new BrngUpcomingOrder();
					brngupcomingorder.setBrngOrder(brngorder);
					brngupcomingorder.setBrngUsrLogin(BrngServicemanLocationDtl.getBrngUsrLogin());
					brngupcomingorder.setStatus("O");
					manager.persist(brngupcomingorder);
*/				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("list....."+listOfLoginIds);
		return listOfLoginIds;
	}
	
	public ArrayList<String> getListOfVehicleServiceMan(String fromLat,String fromLng, int vehicleType)
	{
		double testRadius=200;
		StringBuffer listOfLogins=new StringBuffer("(");
		ArrayList<String> listOfLoginIds=new ArrayList<>();
		BrngLkpUsrType brnglkpusrtype=manager.createQuery("Select a From BrngLkpUsrType a where a.usrType= 'S'",BrngLkpUsrType.class).getSingleResult();
		int serviceManTypeId=brnglkpusrtype.getId();
		try
		{
			List<BrngUsrLogin> brngusrlogins =manager.createQuery("Select a From BrngUsrLogin a, BrngUsrReg b where a.logoutTime is NULL and a.brngLkpUsrType.id="+serviceManTypeId +" and a.brngUsrReg.id= b.id and b.brnglkpvehicleType.id="+vehicleType,BrngUsrLogin.class).getResultList();
			
			
			for(int i=0;i<brngusrlogins.size();i++)
			{
				if(i==brngusrlogins.size()-1)
				{
					listOfLogins=listOfLogins.append(brngusrlogins.get(i).getId()+")");
				}
				else
				{
					listOfLogins=listOfLogins.append(brngusrlogins.get(i).getId()+",");
				}
			}
			
			System.out.println(":    listOfLogins  "+listOfLogins);
			List<BrngServicemanLocationDtls> brngserviceManDetails = manager.createQuery("Select a From BrngServicemanLocationDtls a where a.brngUsrLogin.id in "+listOfLogins,BrngServicemanLocationDtls.class).getResultList();
					
			
			DistanceChecker distancechecker=new DistanceChecker();
			
			for(BrngServicemanLocationDtls BrngServicemanLocationDtl:brngserviceManDetails){
				Double distance=distancechecker.distanceBetween(fromLat,fromLng,BrngServicemanLocationDtl.getLat(),BrngServicemanLocationDtl.getLng());
				if(distance<=testRadius)
				{
					listOfLoginIds.add(BrngServicemanLocationDtl.getBrngUsrLogin().getPlayerId());
					/*BrngUpcomingOrder brngupcomingorder=new BrngUpcomingOrder();
					brngupcomingorder.setBrngOrder(brngorder);
					brngupcomingorder.setBrngUsrLogin(BrngServicemanLocationDtl.getBrngUsrLogin());
					brngupcomingorder.setStatus("O");
					manager.persist(brngupcomingorder);
*/				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("list....."+listOfLoginIds);
		return listOfLoginIds;
	}
	
	public HashMap<String,String> cancelOrderByCustomer(Hashtable<String,String> inputDetails){
		int paidId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
		{
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
			
			if(getCancelStatus(brngorder1.getId())==1)
			{
				message=UserMessages.getUserMessagesNew("OAC");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
			
			if(brngorder1.getBrngLkpIsPicked().getIsPicked().equalsIgnoreCase("Y"))
			{
				message=UserMessages.getUserMessagesNew("OAPI");
				response.put("message", message);
				response.put("response", "5");
				return response;
			}

		BrngLkpIsCancelled brnglkpiscancelled = manager.createQuery("Select a From BrngLkpIsCancelled a where a.isCancelled='Y'",BrngLkpIsCancelled.class).
				getSingleResult();
		int cancelledId=brnglkpiscancelled.getId();
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		
		System.out.println(" user Login Id"+userLoginId);
		Query query = manager
				.createQuery("UPDATE BrngOrder a SET a.brnglkpiscancelled.id =:iscancelled where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("iscancelled",cancelledId);
				query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
				query.setParameter("userLoginId",userLoginId);
			
			query.executeUpdate();
		//transaction.commit();
				
				
			message=UserMessages.getUserMessagesNew("OCS");
			response.put("message", message);
			response.put("response", "1");
			
			if(brngorder1.getIsAccepted().getIsAccepted().equalsIgnoreCase("Y"))
			{
			BrngOrderDelivery brngorderdelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id= '"+inputDetails.get("orderId")+"'",BrngOrderDelivery.class).getSingleResult();
			BrngUsrLogin brngusrlogin1=manager.createQuery("Select a From BrngUsrLogin a where a.id= '"+brngorderdelivery.getBrngUsrLogin().getId()+"'",BrngUsrLogin.class).getSingleResult();
		
			/*message=UserMessages.getUserMessages(1);
			response.put("message", message);
			response.put("response", "1");*/
	/*		PushHelper pushhelper=new PushHelper();
			pushhelper.pushToUser("notifyservicemanoforders", brngusrlogin1.getPlayerId(), "Order cancelled by customer. Order No",Integer.toString(brngorder1.getId()));
	*/		}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessages(-1);
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}
	
	
	public HashMap<String,String> rateTrip(Hashtable<String,String> inputDetails,String apiKey){
		int paidId=0;
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
		{
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+inputDetails.get("orderId"),BrngOrder.class).getSingleResult();
			
			if(getCancelStatus(brngorder1.getId())==1)
			{
				message=UserMessages.getUserMessagesNew("OAC");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
			if(brngorder1.getTripRating()!=-1)
			{
				message=UserMessages.getUserMessagesNew("TAR");
				response.put("message", message);
				response.put("response", "10");
				return response;
			}
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		int ret=checkAuthentication(apiKey, brngusrreg.getId());
System.out.println(ret +" ret");
if(ret==0)
{
	message=UserMessages.getUserMessagesNew("TE");
	response.put("response","0");
	response.put("message",message);
	return response;
}
else if(ret==-1)
{
	message=UserMessages.getUserMessagesNew("E");
	response.put("message", message);
	response.put("response", "-1");
	return response;

}

		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		
		
		System.out.println(" user Login Id"+userLoginId);
		Query query = manager
				.createQuery("UPDATE BrngOrder a SET a.tripRating =:rating where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
				query.setParameter("rating",Integer.parseInt(inputDetails.get("rating")));
				query.setParameter("orderId",Integer.parseInt(inputDetails.get("orderId")));
				query.setParameter("userLoginId",userLoginId);
			
			query.executeUpdate();
		//transaction.commit();
				
				
			message=UserMessages.getUserMessagesNew("RS");
			response.put("message", message);
			response.put("response", "1");
			
			if(brngorder1.getIsAccepted().getIsAccepted().equalsIgnoreCase("Y"))
			{
			BrngOrderDelivery brngorderdelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id= '"+inputDetails.get("orderId")+"'",BrngOrderDelivery.class).getSingleResult();
			BrngUsrLogin brngusrlogin1=manager.createQuery("Select a From BrngUsrLogin a where a.id= '"+brngorderdelivery.getBrngUsrLogin().getId()+"'",BrngUsrLogin.class).getSingleResult();
		
			/*message=UserMessages.getUserMessages(1);
			response.put("message", message);
			response.put("response", "1");*/
	/*		PushHelper pushhelper=new PushHelper();
			pushhelper.pushToUser("notifyservicemanoforders", brngusrlogin1.getPlayerId(), "Order Rated By Customer. Order No",Integer.toString(brngorder1.getId()));
	*/		}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessages(-1);
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}
	public String getCodes(String type,int code)
	{
		try
		{
			if(type.equalsIgnoreCase("A"))
			{
				BrngLkpIsAccepted brnglkp=manager.createQuery("Select a From BrngLkpIsAccepted a where a.id="+code,BrngLkpIsAccepted.class).
						getSingleResult();
				return brnglkp.getIsAccepted();
			}
			else if(type.equalsIgnoreCase("PI"))
			{
				BrngLkpIsPicked brnglkp=manager.createQuery("Select a From BrngLkpIsPicked a where a.id="+code,BrngLkpIsPicked.class).
						getSingleResult();
				return brnglkp.getIsPicked();
			}
			else if(type.equalsIgnoreCase("PA"))
			{
				BrngLkpIsPaid brnglkp=manager.createQuery("Select a From BrngLkpIsPaid a where a.id="+code,BrngLkpIsPaid.class).
						getSingleResult();
				return brnglkp.getIsPaid();
			}
			else if(type.equalsIgnoreCase("C"))
			{
				BrngLkpIsCancelled brnglkp=manager.createQuery("Select a From BrngLkpIsCancelled a where a.id="+code,BrngLkpIsCancelled.class).
						getSingleResult();
				return brnglkp.getIsCancelled();
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
		return "";
	}
	public String getPlayerIdForBuyer(String wod){
		System.out.println("order Id "+wod);
		int orderId=Integer.parseInt(wod);
		List<OrderDto> listOfOrders=getOrderDetailsById(orderId);
		
		System.out.println("player Id"+listOfOrders.get(0).getBrngOrder().getBrngUsrLogin().getPlayerId());
			return listOfOrders.get(0).getBrngOrder().getBrngUsrLogin().getPlayerId();
	}
	
	public int getCancelStatus(int orderId)
	{
		try
		{
			BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+orderId,BrngOrder.class).getSingleResult();
			if(brngorder1.getBrnglkpiscancelled().getIsCancelled().equalsIgnoreCase("Y"))
				return 1;
		}
		catch(Exception e)
		{
			return -1;
		}
		return 0;
	}
	
	public int getCancelStatusForVehicles(int orderId)
	{
		try
		{
			BrngVehicleOrder brngorder1=manager.createQuery("Select a From BrngVehicleOrder a where a.id= "+orderId,BrngVehicleOrder.class).getSingleResult();
			if(brngorder1.getBrnglkpiscancelled().getIsCancelled().equalsIgnoreCase("Y"))
				return 1;
		}
		catch(Exception e)
		{
			return -1;
		}
		return 0;
	}
	
	public String getPlayerIdForServiceMan(String wod){
		BrngOrderDelivery brngorderdelivery = manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id="+wod,BrngOrderDelivery.class).getSingleResult();
		return brngorderdelivery.getBrngOrder().getBrngUsrLogin().getBrngUsrReg().getPlayerId();
	}
	
	public int checkLimitForFree(int userLoginId){
		
		try
		{
		BrngGeneralLkp brnggeneralLkp=manager.createQuery("Select a From BrngGeneralLkp a where a.brngKey= 'free order limit'",BrngGeneralLkp.class).getSingleResult();
		String freeLimit = brnggeneralLkp.getValue();
		
		Query query = manager.
			      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId +" and brnglkpiscancelled='2'");
				long count = (long)query.getSingleResult();
				
				if(Integer.parseInt(freeLimit) > count)
				{
					return 1;
				}
				else
				{
					return 0;
				}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		
		}
	

	public int checkFirstOrderForFree(int userLoginId){
		
		long count=0;
		try
		{
		
		Query query = manager.
			      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId );
				count = (long)query.getSingleResult();
				if(count==0)
				{
					return 0;
				}
				else
				{
					return 1;
				}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		
		}

	public HashMap<String,String> validateCoupons(HashMap<String,String> inputDetails,String apiKey){
		
		long count=0;
		HashMap<String,String> response=new HashMap<>();
		String message="";
		try
		{
			BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			
			int ret=checkAuthentication(apiKey, brngusrreg.getId());
	System.out.println(ret +" ret");
	if(ret==0)
	{
		message=UserMessages.getUserMessagesNew("TE");
		response.put("response","0");
		response.put("message",message);
		return response;
	}
	else if(ret==-1)
	{
		message=UserMessages.getUserMessagesNew("E");
		response.put("message", message);
		response.put("response", "-1");
		return response;

	}
	
	try
	{
		BrngCorporateOffer brngcorporateoffer=manager.createQuery("select a from BrngCorporateOffer a where a.couponCode='"+inputDetails.get("couponCode")+"'",BrngCorporateOffer.class).getSingleResult();
		if(brngcorporateoffer.getRemainingAmount().compareTo(new BigDecimal( inputDetails.get("totalAmount"))) < 1)
		{
			response.put("status","0");
			response.put("message", UserMessages.getUserMessagesNew("CAS"));
		}
		else
		{
			response.put("status","1");
			response.put("offerPercent","100");
			response.put("maxDiscount","9999999999999999");
			response.put("message","Discount Applied");
		}
		
	}
	catch(NoResultException e)
	{

		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();

		int userLoginId=brngusrlogin.getId();
		Date today = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate= formatter.format(today);  
	    today= formatter.parse(strDate);
			List<Object[]> details = manager.createQuery("Select b.code,a.offerPercent,date(a.startDate),date(a.endDate),a.maxDiscount,a.maxTimes from BrngOffer a, BrngLkpOfferType b where a.offerType=b.id and a.name='"+inputDetails.get("couponCode") +"'").getResultList();
			if(details.size() ==0 )
			{
				response.put("status","0");
				response.put("message","Invalid Coupon");
				return response;
			}
			for (Object[] result : details)
			{
				String code=(String)result[0];
				String offerPercent=(String)result[1];
				Date startDate=(Date)result[2];
				Date endDate=(Date)result[3];
				String maxDiscount=(String)result[4];
				String maxTimes=(String)result[5];
				//Date todayDate=new Date();
					   
				if(today.after(endDate))
				{
					response.put("status","0");
					response.put("message", UserMessages.getUserMessagesNew("CE"));
					//return coupon expired
				}
				else if(today.before(startDate))
				{
					response.put("status","0");
					response.put("message", UserMessages.getUserMessagesNew("CNS"));
					//return coupon not started
				}
				
				else if(code.equalsIgnoreCase("DC"))
				{
					Query query = manager.
						      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId +" and a.brngOffer.name='"+inputDetails.get("couponCode")+"'" );
							count = (long)query.getSingleResult();
							if(!maxTimes.equalsIgnoreCase("NA"))
							{
							if(count < Long.parseLong(maxTimes))
							{
								response.put("status","1");
								response.put("offerPercent",offerPercent);
								response.put("maxDiscount",maxDiscount);
								response.put("message","Discount Applied");
							}
							else
							{
								response.put("status","0");
								response.put("message", UserMessages.getUserMessagesNew("CALU"));
							}
							}
							else
								
							{
								response.put("status","1");
								response.put("offerPercent",offerPercent);
								response.put("maxDiscount",maxDiscount);
								response.put("message","Discount Applied");
							}
					
				}
				
				else if(code.equalsIgnoreCase("FF"))
				{
					
					Query query = manager.
						      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId +" and a.brngOffer.name='"+inputDetails.get("couponCode")+"'" );
							count = (long)query.getSingleResult();
							if(!maxTimes.equalsIgnoreCase("NA"))
							{
							if(count < Long.parseLong(maxTimes))
							{
								response.put("status","1");
								response.put("offerPercent",offerPercent);
								response.put("maxDiscount",maxDiscount);
								response.put("message","Discount Applied");

							}
							else
							{
								response.put("status","0");
								response.put("message", UserMessages.getUserMessagesNew("CALU"));
								
							}
							}
							else
							{
								response.put("status","1");
								response.put("offerPercent",offerPercent);
								response.put("maxDiscount",maxDiscount);
								response.put("message","Discount Applied");
							}
							
					
				}
				
				else if(code.equalsIgnoreCase("DCO"))
				{
											Query query = manager.
						      createQuery("Select count(*) from BrngOrder a where a.brngUsrLogin.id="+userLoginId +" and a.brngOffer.name='"+inputDetails.get("couponCode")+"'" );
							count = (long)query.getSingleResult();
							if(count==0)
							{
								response.put("status","1");
								//response.put("message","FIRST_ORDER_FREE");
								response.put("offerPercent",offerPercent);
								response.put("maxDiscount",maxDiscount);
							}
							
					
					
				}

			}
					
		
	}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			response.put("message", UserMessages.getUserMessagesNew("E"));
			response.put("response", "-1");
	
			return response;
		}
		return response;
		
		}
	public int checkAuthentication(String token,int regId){
		
		
		try
		{
			System.out.println("token :"+token +" regId"+regId);
			Query query = manager.
				      createQuery("Select count(*) from BrngUsrLogin where brngUsrReg.id="+regId+" and token='"+token+"'");
				long	 count = (long)query.getSingleResult();
				
				if(count >0)
				{
					return 1;
				}
				else
				{
					return 0;
				}
								
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		
	}

}
