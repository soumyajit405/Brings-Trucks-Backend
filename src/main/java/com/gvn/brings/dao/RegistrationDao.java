package com.gvn.brings.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.util.SHAUtility;
import com.gvn.brings.dto.OrderDeliveryDto;
import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngBankDetails;
import com.gvn.brings.model.BrngGeneralLkp;
import com.gvn.brings.model.BrngLkpFilePath;
import com.gvn.brings.model.BrngLkpIsPaid;
import com.gvn.brings.model.BrngLkpOrderDelStatus;
import com.gvn.brings.model.BrngLkpOtpValidated;
import com.gvn.brings.model.BrngLkpPayType;
import com.gvn.brings.model.BrngLkpPayuDetails;
import com.gvn.brings.model.BrngLkpServicemanValidated;
import com.gvn.brings.model.BrngLkpUsrRegStatus;
import com.gvn.brings.model.BrngLkpUsrRegType;
import com.gvn.brings.model.BrngLkpUsrType;
import com.gvn.brings.model.BrngOrderDelivery;
import com.gvn.brings.model.BrngRegisterDeviceVersionLookup;
import com.gvn.brings.model.BrngRegisterMethodLookup;
import com.gvn.brings.model.BrngUsrAddress;
import com.gvn.brings.model.BrngUsrAddressAttr;
import com.gvn.brings.model.BrngUsrCode;
import com.gvn.brings.model.BrngUsrFiles;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrOtp;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.model.BrngUsrRegAttr;
import com.gvn.brings.model.BrngUsrWallet;
import com.gvn.brings.model.BrngUsrWalletAttr;
import com.gvn.brings.model.BrngVendorMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.gvn.brings.util.CommonUtility;
import com.gvn.brings.util.GenerateRefCode;
import com.gvn.brings.util.GoogleGeoHelper;
import com.gvn.brings.util.MailUtility;
import com.gvn.brings.util.OTPUtil;
import com.gvn.brings.util.UserMessages;

import java.nio.file.Files;
@Transactional
@Repository
public class RegistrationDao extends AbstractBaseDao{
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	ServletContext context;
	
	public List<RegistrationDto> getUserRegTypeList(){
		List<BrngLkpUsrRegType> brngLkpUsrRegTypes = manager.createQuery("Select a From BrngLkpUsrRegType a",BrngLkpUsrRegType.class).
				getResultList();
		List<RegistrationDto> dtoList = new ArrayList();
		RegistrationDto registrationDto = null;
		for(BrngLkpUsrRegType brngLkpUsrRegType:brngLkpUsrRegTypes){
			registrationDto = new RegistrationDto(brngLkpUsrRegType);
			dtoList.add(registrationDto);
		}
		return dtoList;
	}
	
	public List<RegistrationDto> getUserRegStatusList(){
		List<BrngLkpUsrRegStatus> brngLkpUsrRegStatuses = manager.createQuery("Select a From BrngLkpUsrRegStatus a",BrngLkpUsrRegStatus.class).
				getResultList();
		List<RegistrationDto> dtoList = new ArrayList();
		RegistrationDto registrationDto = null;
		for(BrngLkpUsrRegStatus brngLkpUsrRegStatus:brngLkpUsrRegStatuses){
			registrationDto = new RegistrationDto(brngLkpUsrRegStatus);
			dtoList.add(registrationDto);
		}
		return dtoList;
	}
	
	public List<RegistrationDto> getUserTypeList(){
		List<BrngLkpUsrType> brngLkpUsrTypes = manager.createQuery("Select a From BrngLkpUsrType a",BrngLkpUsrType.class).
				getResultList();
		List<RegistrationDto> dtoList = new ArrayList();
		RegistrationDto registrationDto = null;
		for(BrngLkpUsrType brngLkpUsrType:brngLkpUsrTypes){
			registrationDto = new RegistrationDto(brngLkpUsrType);
			dtoList.add(registrationDto);
		}
		return dtoList;
	}
	
	
	public HashMap<String,String> registerUser(BrngUsrRegAttr brngusrregAttr){
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		MailUtility mlu=new MailUtility();
		CommonUtility cm=new CommonUtility();
		try
		{
		boolean emailStatus=cm.isEmailValid(brngusrregAttr.getBrngUsrReg().getEmailId());
		//boolean passwordStatus=cm.isPasswordValid(brngusrregAttr.getBrngUsrReg().getPassword());
	/*	if(passwordStatus==false)
		{
			message=UserMessages.getUserMessagesNew("IP");
			response.put("response","2");
			response.put("message",message);
			return response;
		}
	*/	if(emailStatus==false)
		{
			message=UserMessages.getUserMessagesNew("IE");
			response.put("response","2");
			response.put("message",message);
			return response;
		}
		int checkEmailStatus=checkEmailExistence(brngusrregAttr.getBrngUsrReg().getEmailId());
		int checkPhoneStatus=checkPhoneExistence(brngusrregAttr.getPhoneNumber());
		if(checkEmailStatus==1)
		{
			message=UserMessages.getUserMessagesNew("D");
			response.put("response","2");
			response.put("message",message);
			return response;
		}
		else if(checkPhoneStatus==1)
		{
			message=UserMessages.getUserMessagesNew("DP");
			response.put("response","2");
			response.put("message",message);
			return response;
		}
		else if(checkEmailStatus==-1)
		{
			message=UserMessages.getUserMessagesNew("E");
			response.put("response","-1");
			response.put("message",message);
			return response;
		}
		//Commented for testing
		/*else if(mlu.checkValidMail(brngusrregAttr.getBrngUsrReg().getEmailId())==0)
		{
			message=UserMessages.getUserMessagesNew("NVM");
			response.put("response","-1");
			response.put("message",message);
			return response;
		}*/
		
		BrngLkpUsrRegStatus brngusrregstatus = manager.createQuery("Select a From BrngLkpUsrRegStatus a where a.statusType='"+brngusrregAttr.getBrngUsrReg().getBrngLkpUsrRegStatus().getStatusType()+"'",BrngLkpUsrRegStatus.class).getSingleResult();
		BrngLkpUsrRegType brngusrregtype = manager.createQuery("Select a From BrngLkpUsrRegType a where a.usrRegType='"+brngusrregAttr.getBrngUsrReg().getBrngLkpUsrRegType().getUsrRegType()+"'",BrngLkpUsrRegType.class).getSingleResult();
		BrngLkpOtpValidated brnglkpotpvalidated= manager.createQuery("Select a From BrngLkpOtpValidated a where a.code='N'",BrngLkpOtpValidated.class).getSingleResult();
		BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='N'",BrngLkpServicemanValidated.class).getSingleResult();
		BrngRegisterDeviceVersionLookup brngregisterdeviceversionlookup= manager.createQuery("Select a From BrngRegisterDeviceVersionLookup a where a.code='"+brngusrregAttr.getBrngUsrReg().getBrngRegDevVersion().getCode()+"'",BrngRegisterDeviceVersionLookup.class).getSingleResult();
		BrngRegisterMethodLookup brngregmethod= manager.createQuery("Select a From BrngRegisterMethodLookup a where a.code='"+brngusrregAttr.getBrngUsrReg().getBrngRegMethod().getCode()+"'",BrngRegisterMethodLookup.class).getSingleResult();
		Timestamp registeredTime=new Timestamp(System.currentTimeMillis());
		brngusrregAttr.getBrngUsrReg().setBrngLkpUsrRegStatus(brngusrregstatus);
		brngusrregAttr.getBrngUsrReg().setBrngLkpUsrRegType(brngusrregtype);
		brngusrregAttr.getBrngUsrReg().setBrngRegDevVersion(brngregisterdeviceversionlookup);
		brngusrregAttr.getBrngUsrReg().setBrngRegMethod(brngregmethod);
		
		brngusrregAttr.getBrngUsrReg().setRegisteredDate(registeredTime);
		brngusrregAttr.getBrngUsrReg().setBrnglkpotpvalidated(brnglkpotpvalidated);
		brngusrregAttr.getBrngUsrReg().setBrnglkpservicemanvalidated(brnglkpservicemanvalidated);
		brngusrregAttr.setEffectiveDate(registeredTime);
		
		System.out.println("6 : " + registeredTime);
	//	byte[] salt=SHAUtility.getSalt();
	//	brngusrregAttr.getBrngUsrReg().setPassword(SHAUtility.get_SHA_1_SecurePassword(brngusrregAttr.getBrngUsrReg().getPassword(), salt));
	
		//insert to brnguser
		manager.persist(brngusrregAttr.getBrngUsrReg());
		
		String email = brngusrregAttr.getBrngUsrReg().getEmailId();
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		brngusrregAttr.setBrngUsrReg(brngusrreg);
		System.out.println("1 : " + brngusrreg.getEmailId() + " : 2: " + brngusrreg.getId());
		//insert to brnguserattr
		manager.persist(brngusrregAttr);
		
		
		BrngUsrOtp brngusrotp=new BrngUsrOtp();
		brngusrotp.setEffectiveDate(registeredTime);
		brngusrotp.setBrngUsrReg(brngusrregAttr.getBrngUsrReg());
		OTPUtil otputil=new OTPUtil();
		brngusrotp.setOtpCode(otputil.sendOTP(brngusrregAttr.getPhoneNumber()));
		manager.persist(brngusrotp);
		
		/*System.out.println(" Code "+brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0));
		
		BrngUsrCode brngusrcode=brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0);
		if(brngusrcode.getRefCode()!="NA")
		{
			System.out.println("2 : " + brngusrcode.getRefCode() );
		}
		GenerateRefCode grf=new GenerateRefCode();
		String refCode=grf.generateRefCode(brngusrregAttr.getBrngUsrReg().getEmailId());
		System.out.println("refCode  1:" +refCode);
		BrngUsrCode brngusrcode1=new BrngUsrCode();
		brngusrcode1.setBrngUsrReg(brngusrreg);
		brngusrcode1.setDescription("Default");
		brngusrcode1.setEffectiveDate(registeredTime);
		brngusrcode.setRefCode(refCode);
		manager.persist(brngusrcode1);*/
	//	BrngUsrCode brngusrcode=new BrngUsrCode();
		/*BrngUsrCode brngUsrCode = manager.createQuery("Select a From BrngUsrCode a where a.refCode='"+brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0).getRefCode()+"'",BrngUsrCode.class).getSingleResult();
		int userRegId=0;
		userRegId=brngUsrCode.getBrngUsrReg().getId();
		System.out.println("user Id " +userRegId);
		brngUsrCode.setBrngUsrReg(brngusrregAttr.getBrngUsrReg());
		brngUsrCode.setEffectiveDate(registeredTime);
		brngUsrCode.setRefCode(refCode);
		System.out.println("refCode " +refCode);
		if(userRegId!=0)
		{
		brngUsrCode.setRefCodeId(userRegId);	
		}*/
		//transaction.commit();
		message=UserMessages.getUserMessagesNew("S");
		response.put("response","1");
		response.put("message",message);
		return response;
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessagesNew("E");
			response.put("response","-1");
			response.put("message",message);
			return response;
		}
		
	}
	
	
	
	
public List<RegistrationDto> getProfileDetails(String email,String apikey){
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		List<RegistrationDto> dtoList = new ArrayList();
		//OrderDeliveryDao odao=new OrderDeliveryDao();
		int userId=brngusrreg.getId();
		//UtilityDAO utildao=new UtilityDAO();
		int ret=checkAuthentication(apikey, userId);
		if(ret==0)
		{
			return  dtoList;
		}
		else if(ret==-1)
		{
			return dtoList;
		}
		System.out.println("userId : " + userId);
		List<BrngUsrRegAttr> brngUsrRegDetails = manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id="+userId,BrngUsrRegAttr.class).
				getResultList();
		
		RegistrationDto registrationDto = null;
		for(BrngUsrRegAttr brngUsrRegDetail:brngUsrRegDetails){
			System.out.println("ghhh"+brngUsrRegDetail.getFirstName());
			registrationDto = new RegistrationDto(brngUsrRegDetail);
			
			float totalDistance=getTotalDistance(email);
			registrationDto.setTotalDistance(totalDistance);
			Query query=manager.createQuery("select count(*) from BrngUsrFiles a where a.brngUsrReg.id="+userId);
			Long count=(Long)query.getSingleResult();
			if(count ==0)
			{
				registrationDto.setProfileChangeble(1);
			}
			else
			{
				registrationDto.setProfileChangeble(0);
			}
			dtoList.add(registrationDto);
		}
		
		
		
		return dtoList;
	}
	
	public  HashMap<String,String>  updateProfile(BrngUsrRegAttr brngusrregattr,String apiKey) throws SQLException, ClassNotFoundException
	{
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
		{
			BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngusrregattr.getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			
			int ret=checkAuthentication(apiKey, userId);
			if(ret==0)
			{
				message=UserMessages.getUserMessagesNew("TE");
				response.put("response","0");
				response.put("message",message);
				return response;
			}
		String password = null;
		int checkPhoneStatus=checkPhoneExistenceForSelf(brngusrregattr.getPhoneNumber(),brngusrregattr.getBrngUsrReg().getEmailId());
		System.out.println("check phone status" +checkPhoneStatus);
		 if(checkPhoneStatus==1)
		{
			message=UserMessages.getUserMessagesNew("DPUP");
			response.put("response","1");
			response.put("message",message);
			return response;
		}
		 else if(checkPhoneStatus==-1)
			{
				message=UserMessages.getUserMessagesNew("E");
				response.put("response","-1");
				response.put("message",message);
				return response;
			}
		 else
		 {
			 String genderCode="";
			if(brngusrregattr.getGender().equalsIgnoreCase("M"))
			{
				genderCode="1";
			}
			else
			{
				genderCode="0";
			}
		System.out.println("userId : " + userId);
		Query query = manager
				.createQuery("UPDATE BrngUsrRegAttr a SET a.firstName = :firstName,a.lastName=:lastName,a.middleName=:middleName,a.phoneNumber=:phoneNumber,a.gender=:gender "
				+ "WHERE a.brngUsrReg.id= :id");
				query.setParameter("firstName", brngusrregattr.getFirstName());
				query.setParameter("lastName", brngusrregattr.getLastName());
				query.setParameter("middleName", brngusrregattr.getMiddleName());
				query.setParameter("phoneNumber", brngusrregattr.getPhoneNumber());
				query.setParameter("gender", genderCode);
				query.setParameter("id",userId);
				query.executeUpdate();
						
				message=UserMessages.getUserMessagesNew("U");
				response.put("response","1");
				response.put("message",message);
				}
		}
	catch(Exception e){
			e.printStackTrace();
			message=UserMessages.getUserMessagesNew("E");
			response.put("response","-1");
			response.put("message",message);
		}  
		 
		return response;
			
	}

	public  HashMap<String,String>  checkUserStatus(String email,String apiKey) throws SQLException, ClassNotFoundException
	{
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
		{
			BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			
			int ret=checkAuthentication(apiKey, userId);
			System.out.println("ret"+ret);
			if(ret==0)
			{
				message=UserMessages.getUserMessagesNew("TE");
				response.put("response","0");
				response.put("message",message);
				return response;
			}
			else if(ret ==-1)
			{
				message=UserMessages.getUserMessagesNew("E");
				response.put("response","-1");
				response.put("message",message);
			}
			else
			{
				response.put("response","1");
				response.put("message","Token Valid");
				return response;
			}
				}
	catch(Exception e){
			e.printStackTrace();
			message=UserMessages.getUserMessagesNew("E");
			response.put("response","-1");
			response.put("message",message);
		}  
		 
		return response;
			
	}



	public HashMap<String,String> insertBankDetails(BrngBankDetails brngbankdetails,String apiKey){
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		Timestamp registeredTime =null;
		BrngUsrReg brngusrreg=null;
		//System.out.println("6 : " + registeredTime);
		try
		{
		/* transaction = manager.getTransaction();
		transaction.begin();*/
		//manager.persist(brngusrreg);
		//brngusrregattr.setBrngUsrReg(brngusrreg);
			 registeredTime =new Timestamp(System.currentTimeMillis());
			 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngbankdetails.getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			
			UtilityDAO utildao=new UtilityDAO();
			int ret=utildao.checkAuthentication(apiKey, userId);
			if(ret==0)
			{
				message=UserMessages.getUserMessagesNew("TE");
				response.put("response","0");
				response.put("message",message);
				return response;
			}
			
			//BrngBankDetails brngbankdetailstemp=manager.createQuery("Select a From BrngBankDetails a where a.brngUsrReg.id= "+userId,BrngBankDetails.class).getSingleResult();
			System.out.println("......  "+brngbankdetails);
			
			brngbankdetails.setEffectiveDate(registeredTime);
			brngbankdetails.setBrngUsrReg(brngusrreg);
		manager.merge(brngbankdetails);
				/*Query query = manager
			.createQuery("UPDATE BrngBankDetails a SET a.bankName = :bankName,a.ifscCode=:ifscCode,a.accountNumber=:accountNumber,a.accountName=:accountName,a.branch=:branch,a.effectiveDate=:effectiveDate "
			+ "WHERE a.brngUsrReg.id= :id");
			query.setParameter("bankName", brngbankdetails.getBankName());
			query.setParameter("ifscCode", brngbankdetails.getIfscCode());
			query.setParameter("accountNumber", brngbankdetails.getAccountNumber());
			query.setParameter("accountName", brngbankdetails.getAccountName());
			query.setParameter("branch", brngbankdetails.getBranch());
			query.setParameter("effectiveDate", registeredTime);
			query.setParameter("id", userId);*/
		//	query.executeUpdate();
				
			
		
		//transaction.commit();
			message=UserMessages.getUserMessagesNew("AB");
			response.put("message", message);
			response.put("response", "1");
		}
		catch (NoResultException nre) {
			brngbankdetails.setEffectiveDate(registeredTime);
			brngbankdetails.setBrngUsrReg(brngusrreg);
		manager.merge(brngbankdetails);
		message=UserMessages.getUserMessagesNew("E");
		response.put("message", message);
		response.put("response", "1");
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
	
public List<RegistrationDto> getBankDetails(String email,String apiKey){
		
		
		Timestamp registeredTime =null;
		BrngUsrReg brngusrreg=null;
		List<RegistrationDto> dtoList = new ArrayList();
		//System.out.println("6 : " + registeredTime);
		try
		{
		/* transaction = manager.getTransaction();
		transaction.begin();*/
		//manager.persist(brngusrreg);
		//brngusrregattr.setBrngUsrReg(brngusrreg);
			 registeredTime =new Timestamp(System.currentTimeMillis());
			 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			UtilityDAO utildao=new UtilityDAO();
			int ret=utildao.checkAuthentication(apiKey, userId);
			if(ret==0)
			{
			return dtoList;
			}
			List<BrngBankDetails> brngbankdetails = manager.createQuery("Select a From BrngBankDetails a where a.brngUsrReg.id="+userId+" order by a.effectiveDate desc",BrngBankDetails.class).
					getResultList();
			
			if(brngbankdetails.size() > 0)
			{
			RegistrationDto registrationDto = null;
			//for(BrngBankDetails brngbankdetail:brngbankdetails){
				//System.out.println("ghhh"+brngUsrRegDetail.getFirstName());
				registrationDto = new RegistrationDto(brngbankdetails.get(0));
				dtoList.add(registrationDto);
		//	}
				
			}
		}
		
		
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			return dtoList;
		}
		return dtoList;
		
	}

public HashMap<String,String> addMoney(BrngUsrWalletAttr brngusrwalletattr,String apiKey){
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+brngusrwalletattr.getBrngUsrWallet().getBrngUsrReg().getEmailId()+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		UtilityDAO utildao=new UtilityDAO();
		int ret=utildao.checkAuthentication(apiKey, userId);
		if(ret==0)
		{
			message=UserMessages.getUserMessagesNew("TE");
			response.put("response","0");
			response.put("message",message);
			return response;
		}
		Query query = manager.
			      createQuery("Select count(*) from BrngUsrWallet a where a.brngUsrReg.id="+userId);
				long count = (long)query.getSingleResult();
				Timestamp updateTime=new Timestamp(System.currentTimeMillis());
				if(count>0)
				{
					
					int walletId=0;
					BigDecimal sum=new BigDecimal("0");
				List<BrngUsrWalletAttr> listbrngUsrWalletAttr=manager.createQuery("Select a From BrngUsrWalletAttr a where a.brngUsrWallet.brngUsrReg.id= "+userId +" and a.status='S'",BrngUsrWalletAttr.class).getResultList();
				System.out.println("size "+ listbrngUsrWalletAttr.size());
				List<BrngUsrWalletAttr> brngusrwalletattrtemp=manager.createQuery("Select a From BrngUsrWalletAttr a where a.brngUsrWallet.brngUsrReg.id= "+userId ,BrngUsrWalletAttr.class).getResultList();
				walletId=brngusrwalletattrtemp.get(0).getBrngUsrWallet().getId();
				if(listbrngUsrWalletAttr.size()>0)
				{
				//System.out.println(" :  "+listbrngUsrWalletAttr.get(0).getBrngUsrWallet().getId());
				
				for(int i=0;i<listbrngUsrWalletAttr.size();i++)
				{
					sum=sum.add(listbrngUsrWalletAttr.get(i).getTranAmt());
				}
				}
				brngusrwalletattr.getBrngUsrWallet().setBrngUsrReg(brngusrreg);
				brngusrwalletattr.getBrngUsrWallet().setId(walletId);
				brngusrwalletattr.setEffectiveDate(updateTime);
				brngusrwalletattr.setPayTxnId(brngusrwalletattr.getPayTxnId());
				brngusrwalletattr.setStatus(brngusrwalletattr.getStatus());
				manager.merge(brngusrwalletattr);
				if(brngusrwalletattr.getStatus().equalsIgnoreCase("S"))
				{
					sum=sum.add(brngusrwalletattr.getTranAmt());
				}
				
				//brngusrwalletattr.getBrngUsrWallet().setCuurAmt(sum);
				query = manager.
					      createQuery("update BrngUsrWallet a set a.currAmt=:currAmt where a.brngUsrReg.id="+userId);
				query.setParameter("currAmt", sum);
				query.executeUpdate();
				System.out.println("TXN AMOUNT"+brngusrwalletattr.getTranAmt());
				System.out.println("---"+brngusrwalletattr.getTranAmt().compareTo(BigDecimal.ZERO));
				if(brngusrwalletattr.getTranAmt().compareTo(BigDecimal.ZERO) < 0)
				{
					System.out.println("Inside if");
					BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
					
					int userLoginId=brngusrlogin.getId();
					int paidId = 0;
					BrngLkpPayType brnglkppaytype=manager.createQuery("Select a From BrngLkpPayType a where a.type= 'W'",BrngLkpPayType.class).getSingleResult();
					int payTypeId=brnglkppaytype.getId();
					List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager.createQuery("Select a From BrngLkpIsPaid a",BrngLkpIsPaid.class).
							getResultList();
					List<OrderDeliveryDto> dtoList = new ArrayList();
					OrderDeliveryDto orderDeliveryDto = null;
					for(BrngLkpIsPaid brngLkpIsPaidType:brngLkpIsPaidTypes){
						if(brngLkpIsPaidType.getIsPaid().equalsIgnoreCase("Y"))
						{
							paidId=brngLkpIsPaidType.getId();
							//paidStatus=brngLkpIsPaidType.getIsPaid();
							break;
						}
					}
					System.out.println(" Paid Id "+paidId);
					 query = manager
							.createQuery("UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
							query.setParameter("isPaid",paidId);
							
							
							query.setParameter("orderId",brngusrwalletattr.getBrngOrder().getId());
							query.setParameter("userLoginId",userLoginId);
							query.setParameter("payTxId","NA");
							query.setParameter("payTypeId",payTypeId);
							
							query.executeUpdate();
					
				}

				
				}
				else
				{
					brngusrwalletattr.getBrngUsrWallet().setBrngUsrReg(brngusrreg);
					if(brngusrwalletattr.getStatus().equalsIgnoreCase("S"))
					{
						brngusrwalletattr.getBrngUsrWallet().setCuurAmt(brngusrwalletattr.getTranAmt());
						
					}
					else
					{
					brngusrwalletattr.getBrngUsrWallet().setCuurAmt(BigDecimal.ZERO);;
					}
					manager.merge(brngusrwalletattr.getBrngUsrWallet());
					BrngUsrWallet brngusrwallet= manager.
						      createQuery("Select a from BrngUsrWallet a where a.brngUsrReg.id="+userId,BrngUsrWallet.class).getSingleResult();
					brngusrwalletattr.setBrngUsrWallet(brngusrwallet);
					brngusrwalletattr.setEffectiveDate(updateTime);
					manager.merge(brngusrwalletattr);
								}
		
		
	
	//transaction.commit();
				message=UserMessages.getUserMessagesNew("WUS");
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
	

public BigDecimal checkBalance(Hashtable<String,String> inputDetails){
	

	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	//System.out.println("6 : " + registeredTime);
	try
	{
	
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		
		System.out.println("  : "+userId);
		BrngUsrWallet brngusrwallet=manager.createQuery("select a From BrngUsrWallet a where a.brngUsrReg.id="+userId,BrngUsrWallet.class).getSingleResult();
		
		BigDecimal amount=brngusrwallet.getCuurAmt();
		
		
	
	//transaction.commit();
	return amount;
	}
	catch (NoResultException nre) {
	
		BigDecimal amount=new BigDecimal("0");
	return amount;
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		//return 0;
	}
	return null;
	
	}

public HashMap<String, String> checkOTP(Hashtable<String,String> inputDetails,String apiKey){
	
	HashMap<String,String> response =new HashMap<>();
	BrngUsrReg brngusrreg=null;
	String message="";
	//System.out.println("6 : " + registeredTime);
	try
	{
	
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
		
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

		
		System.out.println("  : "+userId +inputDetails.get("otp"));
		Query query = manager.
			      createQuery("Select count(*) from BrngUsrOtp a where a.brngUsrReg.id="+userId+" and a.otpCode ='"+inputDetails.get("otp")+"'");
					System.out.println("query "+query);
				long count = (long)query.getSingleResult();
				System.out.println("count "+count);
				if(count>0)
				{
					BrngLkpOtpValidated brnglkpotpvalidated=manager.createQuery("Select a From BrngLkpOtpValidated a where a.code='Y'",BrngLkpOtpValidated.class).getSingleResult();
					query = manager
							.createQuery("UPDATE BrngUsrReg a SET a.brnglkpotpvalidated = :brngotpvalidated  where a.id= :id ");
					query.setParameter("brngotpvalidated", brnglkpotpvalidated);
					
					query.setParameter("id",userId);
						query.executeUpdate();
					message=UserMessages.getUserMessagesNew("OM");
					response.put("response","1");
					response.put("message",message);
				
				}
				else
				{
					message=UserMessages.getUserMessagesNew("ONM");
					response.put("response","9");
					response.put("message",message);
				}
	
		
		
	//transaction.commit();
	
	}
	catch (NoResultException nre) {
	
		System.out.println("  Test:1 ");
		message=UserMessages.getUserMessagesNew("ONM");
		response.put("response","8");
		response.put("message",message);
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		
		System.out.println("  Test2: ");
		//transaction.rollback();
		//return 0;
	}
	return response;
	
	}

public HashMap<String, String> checkMobileNumberExistence(Hashtable<String,String> inputDetails){
	
	HashMap<String,String> response =new HashMap<>();
	BrngUsrRegAttr brngusrreg=null;
	String message="";
	//System.out.println("6 : " + registeredTime);
	try
	{
		BrngUsrRegAttr brngusrregattr=manager.createQuery("select a from BrngUsrRegAttr a where a.phoneNumber='"+inputDetails.get("phone")+"'",BrngUsrRegAttr.class).getSingleResult();

	Timestamp generatedTime=new Timestamp(System.currentTimeMillis());
	
		int status=checkPhoneExistence(inputDetails.get("phone"));
		if(status==1)
		{
			//BrngUsrRegAttr brngusrregattr=manager.createQuery("select a from BrngUsrRegAttr a where a.phoneNumber='"+inputDetails.get("phone")+"'",BrngUsrRegAttr.class).getSingleResult();
			BrngUsrOtp brngusrotp=new BrngUsrOtp();
			brngusrotp.setEffectiveDate(generatedTime);
			brngusrotp.setBrngUsrReg(brngusrregattr.getBrngUsrReg());
			OTPUtil otputil=new OTPUtil();
			if(inputDetails.get("phone").equalsIgnoreCase("8885807393") || inputDetails.get("phone").equalsIgnoreCase("9154748948") || inputDetails.get("phone").equalsIgnoreCase("9000164024"))
			{
				brngusrotp.setOtpCode("1111");
			}
			else
			{
			brngusrotp.setOtpCode(otputil.sendOTP(brngusrregattr.getPhoneNumber()));
			}
			//brngusrotp.setOtpCode("1111");
			manager.persist(brngusrotp);
			message=UserMessages.getUserMessagesNew("OSS");
			response.put("message", message);
			response.put("response", "1");	
			
			
		}
		else if(status ==0)
		{
			message=UserMessages.getUserMessagesNew("NR");
			response.put("message", message);
			response.put("response", "3");	
			
		}
		else
		{
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");

		}
			
	//transaction.commit();
	
	}
	catch (NoResultException nre) {
	
		nre.printStackTrace();
		System.out.println("  Test:1 ");
		message=UserMessages.getUserMessagesNew("NR");
		response.put("message", message);
		response.put("response", "3");	
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		
		System.out.println("  Test2: ");
		//transaction.rollback();
		//return 0;
	}
	return response;
	
	}

public HashMap<String, String> loginWithSocialMedia(Hashtable<String,String> inputDetails){
	
	HashMap<String,String> response =new HashMap<>();
	
	String message="";
	//System.out.println("6 : " + registeredTime);
	try
	{
	Timestamp loginTime=new Timestamp(System.currentTimeMillis());
	BrngUsrReg brngusrreg=manager.createQuery("select a from BrngUsrReg a where a.emailId='"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
	//UtilityDAO utildao=new UtilityDAO();
			int status=checkEmailExistence(inputDetails.get("email"));
		if(status==1)
		{
			//BrngUsrReg brngusrreg=manager.createQuery("select a from BrngUsrReg a where a.emailId='"+inputDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
			BrngUsrRegAttr brngusrregattr=manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id= "+brngusrreg.getId(),BrngUsrRegAttr.class).getSingleResult();
			if(inputDetails.get("usrType").equalsIgnoreCase("S") && brngusrreg.getBrnglkpservicemanvalidated().getCode().equalsIgnoreCase("N"))
			{
				message=UserMessages.getUserMessagesNew("PUP");
				response.put("message", message);
				response.put("response", "13");
				response.put("email",brngusrregattr.getBrngUsrReg().getEmailId());
				return response;
			}
			Query query = manager.
				      createQuery("Select count(*) from BrngUsrLogin where brngUsrReg.id="+brngusrreg.getId());
					long count = (long)query.getSingleResult();
					String token=SHAUtility.generateRandomToken();
					if(count>0)
					{
						
						BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a  where a.usrType='"+inputDetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
						System.out.println("inside If " );
					 query = manager
								.createQuery("UPDATE BrngUsrLogin a SET a.loginTime = :loginTime,a.playerId=:playerId,a.logoutTime=:logoutTime,a.brngLkpUsrType.id=:type,a.token=:token "
								+ "WHERE a.brngUsrReg.id= :id");
								query.setParameter("id", brngusrreg.getId());
								query.setParameter("loginTime",loginTime);
								query.setParameter("playerId",inputDetails.get("playerId"));
								query.setParameter("logoutTime",null);
								query.setParameter("type",brnglkpusrtype.getId());
								query.setParameter("token",token);
								
									query.executeUpdate();
					}
					else
					{
						
						BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a where a.usrType='"+inputDetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
						
						System.out.println("inside else " );
						BrngUsrLogin brngusrlogin=new BrngUsrLogin();
						brngusrlogin.setPlayerId(inputDetails.get("playerId"));
						brngusrlogin.setBrngUsrReg(brngusrreg);
						brngusrlogin.setBrngLkpUsrType(brnglkpusrtype);
						//System.out.println(brngusrlogin.getBrngUsrReg().getId());
						brngusrlogin.setLoginTime(loginTime);
						brngusrlogin.setToken(token);
						System.out.println("before setting " );
						//brngusrlogin.setBrngUsrReg(brngusrlogin.getBrngUsrReg());
						manager.persist(brngusrlogin);
					}
					message=UserMessages.getUserMessagesNew("LS");
					response.put("message", message);
					response.put("response", "1");
					response.put("phone", brngusrregattr.getPhoneNumber());
					response.put("fullName", brngusrregattr.getFirstName()+" "+brngusrregattr.getLastName());
					response.put("token",token);
					
	}
			
		else if(status ==0)
		{
			message=UserMessages.getUserMessagesNew("NR");
			response.put("message", message);
			response.put("response", "3");	
			
		}
		else
		{
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");

		}
			
	//transaction.commit();
	
	}
	catch(NoResultException e)
	{
		message=UserMessages.getUserMessagesNew("NR");
		response.put("message", message);
		response.put("response", "3");	
	return response;	
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		
		System.out.println("  Test2: ");
		//transaction.rollback();
		//return 0;
	}
	return response;
	
	}

	public int checkEmailExistence(String mail)
	{
		try
		{
			System.out.println("mail :"+mail);
			Query query = manager.
				      createQuery("Select count(*) from BrngUsrReg a where  a.emailId='"+mail+"'");
					long count = (long)query.getSingleResult();
					if(count>0)
					{
						return 1;
					}
					else
					{
					return 0;	
					}
		//BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+mail+"'",BrngUsrReg.class).getSingleResult();
		}
		catch(Exception nre)
		{
			nre.printStackTrace();
			return -1;
		}
		//return 1;
		//brngusrregAttr.setBrngUsrReg(brngusrreg);
	}
	
	
	public int checkPhoneExistence(String phone)
	{
		try
		{
			System.out.println("phone :"+phone);
			Query query = manager.
				      createQuery("Select count(*) from BrngUsrRegAttr a where  a.phoneNumber='"+phone+"'");
					long count = (long)query.getSingleResult();
					if(count>0)
					{
						return 1;
					}
					else
					{
					return 0;	
					}
		//BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+mail+"'",BrngUsrReg.class).getSingleResult();
		}
		catch(Exception nre)
		{
			nre.printStackTrace();
			return -1;
		}
		//return 1;
		//brngusrregAttr.setBrngUsrReg(brngusrreg);
	}
	
	public int checkPhoneExistenceForSelf(String phone,String email)
	{
		try
		{
			System.out.println("phone :"+phone);
			Query query = manager.
				      createQuery("Select count(*) from BrngUsrRegAttr a, BrngUsrReg b where a.brngUsrReg.id=b.id and a.phoneNumber='"+phone+"' and b.emailId <> '"+email+"'");
					long count = (long)query.getSingleResult();
					if(count>0)
					{
						return 1;
					}
					else
					{
					return 0;	
					}
		//BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+mail+"'",BrngUsrReg.class).getSingleResult();
		}
		catch(Exception nre)
		{
			nre.printStackTrace();
			return -1;
		}
		//return 1;
		//brngusrregAttr.setBrngUsrReg(brngusrreg);
	}
	
	public HashMap<String,String> editAddress(Hashtable<String,String> addressDetails,String apiKey){
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		Timestamp registeredTime =null;
		BrngUsrReg brngusrreg=null;
		BrngUsrAddress brngusraddressTemp;
		//System.out.println("6 : " + registeredTime);
		try
		{
		/* transaction = manager.getTransaction();
		transaction.begin();*/
		//manager.persist(brngusrreg);
		//brngusrregattr.setBrngUsrReg(brngusrreg);
			 registeredTime =new Timestamp(System.currentTimeMillis());
			 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+addressDetails.get("email")+"'",BrngUsrReg.class).getSingleResult();
			
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

			Query query = manager
					.createQuery("UPDATE BrngUsrAddress a SET a.savedName = :savedName,a.address=:address,a.placeId=:placeId,a.lat=:lat,a.lng=:lng where "
					+ "a.id= :addressId and a.brngUsrReg.id= "+userId);
					query.setParameter("savedName", addressDetails.get("savedName"));
					query.setParameter("address",addressDetails.get("address"));
					
					query.setParameter("lat",addressDetails.get("lat"));
					query.setParameter("lng",addressDetails.get("lng"));
					query.setParameter("placeId",addressDetails.get("placeId"));
					query.setParameter("addressId",Integer.parseInt(addressDetails.get("addressId")));
					
					
					
						query.executeUpdate();
		try
		{
			BrngUsrAddressAttr brngusraddressattr=manager.createQuery("select a from BrngUsrAddressAttr a where a.brngUsrAddressId="+Integer.parseInt(addressDetails.get("addressId")),BrngUsrAddressAttr.class ).getSingleResult();
			query = manager
					.createQuery("UPDATE BrngUsrAddressAttr a SET a.firstName = :firstName,a.lastName=:lastName,a.fullAddress=:fullAddress,a.phoneNumber=:phone where "
					+ "a.brngUsrAddressId= :addressId");
					query.setParameter("firstName", addressDetails.get("firstName"));
					query.setParameter("lastName",addressDetails.get("lastName"));
					
					query.setParameter("fullAddress",addressDetails.get("fullAddress"));
					query.setParameter("phone",addressDetails.get("phoneNumber"));
					
					query.setParameter("addressId",Integer.parseInt(addressDetails.get("addressId")));
					
					
					
						query.executeUpdate();
			

		}
		catch(NoResultException e)
		{
			BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
			brngusraddressattr.setFirstName(addressDetails.get("firstName"));
			brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
			brngusraddressattr.setLastName(addressDetails.get("lastName"));
			brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
			brngusraddressattr.setBrngUsrAddressId(Integer.parseInt(addressDetails.get("addressId")));
			manager.persist(brngusraddressattr);
			
		}
						
					message=UserMessages.getUserMessagesNew("AA");
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
	
public HashMap<String,String> saveAddress(HashMap<String,String> addressDetails){
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		Timestamp registeredTime =null;
		BrngUsrReg brngusrreg=null;
		BrngUsrAddress brngusraddressTemp;
		//System.out.println("6 : " + registeredTime);
		try
		{
		/* transaction = manager.getTransaction();
		transaction.begin();*/
		//manager.persist(brngusrreg);
		//brngusrregattr.setBrngUsrReg(brngusrreg);
			 registeredTime =new Timestamp(System.currentTimeMillis());
			 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+addressDetails.get("emailId")+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
		/*	int ret=checkAuthentication(apiKey, userId);
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
*/			
			Query query=manager.createQuery("select id from BrngUsrAddress order by id desc");
			List<Integer> countList=query.getResultList();
			//Long c=new Long(countList.get(0));
			if(countList.size() == 0)
			{
				 query=manager.createQuery("select count(*) from BrngUsrAddress");
				//List<Integer> countList=query.getResultList();
				//Long c=new Long(countList.get(0));
				
				 long count = (long)query.getSingleResult();
				BrngUsrAddress brngusraddress=new BrngUsrAddress();	
				brngusraddress.setId((int)count+1);
				brngusraddress.setAddress(addressDetails.get("addressDetails"));
				brngusraddress.setLat(addressDetails.get("lat"));
				brngusraddress.setLng(addressDetails.get("lng"));
				brngusraddress.setPlaceId(addressDetails.get("placeId"));
				brngusraddress.setSavedName(addressDetails.get("savedName"));
				brngusraddress.setBrngUsrReg(brngusrreg);
				manager.persist(brngusraddress);
				//manager.flush();
				//int count= brngusraddress.getId();
				System.out.println("Count After Inserting"+count);
				
				BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
				brngusraddressattr.setFirstName(addressDetails.get("firstName"));
				brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
				brngusraddressattr.setLastName(addressDetails.get("lastName"));
				brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
				brngusraddressattr.setBrngUsrAddressId((int)count+1);
				manager.persist(brngusraddressattr);
				
						message=UserMessages.getUserMessagesNew("AA");
						response.put("message", message);
						response.put("response", "1");
						return response;
			}
			int count=countList.get(0).intValue()+1;
			BrngUsrAddress brngusraddress=new BrngUsrAddress();	
			brngusraddress.setId(count);
			brngusraddress.setAddress(addressDetails.get("address"));
			brngusraddress.setLat(addressDetails.get("lat"));
			brngusraddress.setLng(addressDetails.get("lng"));
			brngusraddress.setPlaceId(addressDetails.get("placeId"));
			brngusraddress.setSavedName(addressDetails.get("savedName"));
			brngusraddress.setBrngUsrReg(brngusrreg);
			manager.persist(brngusraddress);
			
			BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
			brngusraddressattr.setFirstName(addressDetails.get("firstName"));
			brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
			brngusraddressattr.setLastName(addressDetails.get("lastName"));
			brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
			brngusraddressattr.setBrngUsrAddressId(count);
			manager.persist(brngusraddressattr);
			/*brngusraddressTemp=manager.createQuery("Select a From BrngUsrAddress a where a.brngUsrReg.id= "+userId+" and (a.savedName='"+brngusraddress.getSavedName()+"' or a.address='"+brngusraddress.getAddress()+"')",BrngUsrAddress.class).getSingleResult();
			//int userId=brngusrreg.getId();
			Query query = manager
					.createQuery("UPDATE BrngUsrAddress a SET a.savedName = :savedName,a.address=:address,a.lat=:lat,a.lng=:lng where "
					+ "a.savedName= :savedName and a.brngUsrReg.id= "+userId);
					query.setParameter("savedName", brngusraddress.getSavedName());
					query.setParameter("address",brngusraddress.getAddress());
					GoogleGeoHelper googlegeohelper=new GoogleGeoHelper();
					HashMap<String,String> latLng=googlegeohelper.getLatLng(brngusraddress.getAddress());
					query.setParameter("lat",latLng.get("lat"));
					query.setParameter("lng",latLng.get("lng"));
					
					
					
						query.executeUpdate();
			*/
		
					message=UserMessages.getUserMessagesNew("AA");
					response.put("message", message);
					response.put("response", "1");
		}
		
		catch(NoResultException nre)
		{
			Query query=manager.createQuery("select count(*) from BrngUsrAddress ");
			//List<Integer> countList=query.getResultList();
			//Long c=new Long(countList.get(0));
			
			long count = (long)query.getSingleResult();
			BrngUsrAddress brngusraddress=new BrngUsrAddress();	
			brngusraddress.setId((int)count+1);
			brngusraddress.setAddress(addressDetails.get("addressDetails"));
			brngusraddress.setLat(addressDetails.get("lat"));
			brngusraddress.setLng(addressDetails.get("lng"));
			brngusraddress.setPlaceId(addressDetails.get("placeId"));
			brngusraddress.setSavedName(addressDetails.get("savedName"));
			brngusraddress.setBrngUsrReg(brngusrreg);
			manager.persist(brngusraddress);
			//manager.flush();
			//int count= brngusraddress.getId();
			System.out.println("Count After Inserting"+count);
			
			BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
			brngusraddressattr.setFirstName(addressDetails.get("firstName"));
			brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
			brngusraddressattr.setLastName(addressDetails.get("lastName"));
			brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
			brngusraddressattr.setBrngUsrAddressId((int)count+1);
			manager.persist(brngusraddressattr);
			
					message=UserMessages.getUserMessagesNew("AA");
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
	
public HashMap<String,String> saveAddressByReference(HashMap<String,String> addressDetails){
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	BrngUsrAddress brngusraddressTemp;
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+addressDetails.get("emailId")+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		Query query=manager.createQuery("select id from BrngUsrAddress order by id desc");
		List<Integer> countList=query.getResultList();
		//Long c=new Long(countList.get(0));
		
		int count=countList.get(0).intValue()+1;
		BrngUsrAddress brngusraddress=new BrngUsrAddress();	
		brngusraddress.setId(count);
		brngusraddress.setAddress(addressDetails.get("addressDetails"));
		brngusraddress.setLat(addressDetails.get("lat"));
		brngusraddress.setLng(addressDetails.get("lng"));
		brngusraddress.setPlaceId(addressDetails.get("placeId"));
		brngusraddress.setSavedName(addressDetails.get("savedName"));
		brngusraddress.setBrngUsrReg(brngusrreg);
		manager.persist(brngusraddress);
		
		BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
		brngusraddressattr.setFirstName(addressDetails.get("firstName"));
		brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
		brngusraddressattr.setLastName(addressDetails.get("lastName"));
		brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
		brngusraddressattr.setBrngUsrAddressId(count);
		manager.persist(brngusraddressattr);
		
		brngusraddress.setBrngUsrReg(brngusrreg);
		manager.persist(brngusraddress);
		/*brngusraddressTemp=manager.createQuery("Select a From BrngUsrAddress a where a.brngUsrReg.id= "+userId+" and (a.savedName='"+brngusraddress.getSavedName()+"' or a.address='"+brngusraddress.getAddress()+"')",BrngUsrAddress.class).getSingleResult();
		//int userId=brngusrreg.getId();
		Query query = manager
				.createQuery("UPDATE BrngUsrAddress a SET a.savedName = :savedName,a.address=:address,a.lat=:lat,a.lng=:lng where "
				+ "a.savedName= :savedName and a.brngUsrReg.id= "+userId);
				query.setParameter("savedName", brngusraddress.getSavedName());
				query.setParameter("address",brngusraddress.getAddress());
				GoogleGeoHelper googlegeohelper=new GoogleGeoHelper();
				HashMap<String,String> latLng=googlegeohelper.getLatLng(brngusraddress.getAddress());
				query.setParameter("lat",latLng.get("lat"));
				query.setParameter("lng",latLng.get("lng"));
				
				
				
					query.executeUpdate();
		*/
	
				message=UserMessages.getUserMessagesNew("AA");
				response.put("message", message);
				response.put("response", "1");
	}
	
	catch(NoResultException nre)
	{
		Query query=manager.createQuery("select id from BrngUsrAddress order by id desc");
		List<Integer> countList=query.getResultList();
		//Long c=new Long(countList.get(0));
		
		int count=countList.get(0).intValue()+1;
		BrngUsrAddress brngusraddress=new BrngUsrAddress();	
		brngusraddress.setId(count);
		brngusraddress.setAddress(addressDetails.get("addressDetails"));
		brngusraddress.setLat(addressDetails.get("lat"));
		brngusraddress.setLng(addressDetails.get("lng"));
		brngusraddress.setPlaceId(addressDetails.get("placeId"));
		brngusraddress.setSavedName(addressDetails.get("savedName"));
		brngusraddress.setBrngUsrReg(brngusrreg);
		manager.persist(brngusraddress);
		
		BrngUsrAddressAttr brngusraddressattr=new BrngUsrAddressAttr();
		brngusraddressattr.setFirstName(addressDetails.get("firstName"));
		brngusraddressattr.setFullAddress(addressDetails.get("fullAddress"));
		brngusraddressattr.setLastName(addressDetails.get("lastName"));
		brngusraddressattr.setPhoneNumber(addressDetails.get("phoneNumber"));
		brngusraddressattr.setBrngUsrAddressId(count);
		manager.persist(brngusraddressattr);
		
		brngusraddress.setBrngUsrReg(brngusrreg);
		manager.persist(brngusraddress);
		
				message=UserMessages.getUserMessagesNew("AA");
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


public HashMap<String,String> deleteAddress(int addressId,String email,String apiKey){
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	BrngUsrAddress brngusraddressTemp;
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
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

		
		Query query = manager
				.createQuery("delete BrngUsrAddress a where a.id="+addressId+" and a.brngUsrReg.id= "+userId);
					query.executeUpdate();
					
					try
					{
						BrngUsrAddressAttr brngusraddressattr=manager.createQuery("select a from BrngUsrAddressAttr a where a.brngUsrAddressId="+addressId,BrngUsrAddressAttr.class ).getSingleResult();
						query=manager
						.createQuery("delete BrngUsrAddressAttr a where a.brngUsrAddressId="+addressId);
							query.executeUpdate();
				
					}
					catch(NoResultException e)
					{
						
					}
				
					
	
				message=UserMessages.getUserMessagesNew("ADS");
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


/*public String uploadFile(File aadhar,File rc,File pan,File drivingLic,String email) throws FileNotFoundException{
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		 
		
	      try {
		//System.out.println("6 : " + registeredTime);
		BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='S'",BrngLkpFilePath.class).getSingleResult();
		
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
		
		String path=brnglkpfilepath.getFilePath();
		
		int userRegId=brngusrreg.getId(); 
		System.out.println(path);
		
		File file = new File(path+""+userRegId+"/aadhar");
        file.mkdir();
       // file = new File(path+""+userRegId+"/vehiclerc/");
       // file.mkdir();
       // file = new File(path+""+userRegId+"/pan/");
      //  file.mkdir();
        file = new File(path+""+userRegId+"/aadhar/"+aadhar.getName());
       // file.mkdir();
        //file = new File(path+""+userRegId+"/driving_lic/");
      //  file.mkdir();
        System.out.println("Adddhar "+aadhar.getTotalSpace());
        FileOutputStream out = new FileOutputStream(new File(path+""+userRegId+"/aadhar/"+aadhar.getName()));  
        CommonUtility cmu=new CommonUtility();
        cmu.copyFileUsingStream(aadhar, file);
      //  Files.copy(, dest.toPath());
        
        byte[] buffer = new byte[1024];

        FileInputStream inputStream = 
            new FileInputStream(aadhar);

        // read fills buffer with data and returns
        // the number of bytes read (which of course
        // may be less than the buffer size, but
        // it will never be more).
        int total = 0;
        int nRead = 0;
        while((nRead = inputStream.read(buffer)) != -1) {
            // Convert to String so we can display it.
            // Of course you wouldn't want to do this with
            // a 'real' binary file.
           // System.out.println(new String(buffer));
            total += nRead;
            out.write(buffer,0,nRead);
        }   
System.out.println("total"+total);
        inputStream.close();
       // out.flush();  
        out.close(); 
        in = new FileInputStream(aadhar);
        out = new FileOutputStream(path+""+userRegId+"/aadhar/"+aadhar.getName());
        int c;
        while ((c = in.read()) != -1) {
           out.write(c);
        }
        System.out.println("Adddhar1 "+aadhar.getTotalSpace());
        in.close();
        out.close();
        in = new FileInputStream(pan);
        out = new FileOutputStream(path+""+userRegId+"/pan/"+pan.getName());
        c=0;
        while ((c = in.read()) != -1) {
           out.write(c);
        }
        in.close();
        out.close();
        in = new FileInputStream(drivingLic);
        out = new FileOutputStream(path+""+userRegId+"/driving_lic/"+drivingLic.getName());
        c=0;
        while ((c = in.read()) != -1) {
           out.write(c);
        }
        in.close();
        out.close();
        in = new FileInputStream(rc);
        out = new FileOutputStream(path+""+userRegId+"/vehiclerc/"+rc.getName());
        c=0;
        while ((c = in.read()) != -1) {
           out.write(c);
        }
        in.close();
        out.close();
        //saveNameInDB(userRegId, aadhar.getName(), drivingLic.getName(), rc.getName(), pan.getName());
					message=UserMessages.getUserMessages(1);
					response.put("message", message);
					response.put("response", "1");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			message=UserMessages.getUserMessages(-1);
			response.put("message", message);
			response.put("response", "-1");
		}
	     
		return null;
	}

*/
public List<RegistrationDto> getSavedAddress(String email,String apiKey){
		
		
		Timestamp registeredTime =null;
		BrngUsrReg brngusrreg=null;
		//System.out.println("6 : " + registeredTime);
		try
		{
		/* transaction = manager.getTransaction();
		transaction.begin();*/
		//manager.persist(brngusrreg);
		//brngusrregattr.setBrngUsrReg(brngusrreg);
			 registeredTime =new Timestamp(System.currentTimeMillis());
			 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
			
			int userId=brngusrreg.getId();
			int ret=checkAuthentication(apiKey, brngusrreg.getId());
			System.out.println(ret +" ret");
			if(ret==0)
			{
				return null;
			}
			else if(ret==-1)
			{
				return null;
			}

			List<BrngUsrAddress> brngusraddresses = manager.createQuery("Select a From BrngUsrAddress a where a.brngUsrReg.id="+userId +" order by id desc",BrngUsrAddress.class).
					getResultList();
			List<RegistrationDto> dtoList = new ArrayList();
			RegistrationDto registrationDto = null;
			for(BrngUsrAddress brngusraddress:brngusraddresses){
				//System.out.println("ghhh"+brngUsrRegDetail.getFirstName());
				registrationDto = new RegistrationDto(brngusraddress);
				try
				{
					BrngUsrAddressAttr brngusraddressattr=manager.createQuery("select a from BrngUsrAddressAttr a where a.brngUsrAddressId="+brngusraddress.getId(),BrngUsrAddressAttr.class).getSingleResult();
					registrationDto.setFirstNamea(brngusraddressattr.getFirstName());
					registrationDto.setLastNamea(brngusraddressattr.getLastName());
					registrationDto.setPhoneNumbera(brngusraddressattr.getPhoneNumber());
					registrationDto.setAddressIda(brngusraddressattr.getBrngUsrAddressId());
					registrationDto.setFullAddressa(brngusraddressattr.getFullAddress());
				}
				catch(NoResultException e)
				{
				e.printStackTrace();	
				}
				
				dtoList.add(registrationDto);
			}
			return dtoList;
		
		}
		
		
		catch(Exception e)
		{
			e.printStackTrace();
			//transaction.rollback();
			return null;
		}
		
	}


public List<RegistrationDto> getSavedAddressById(String email,String apiKey,int addressId){
	
	
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		int ret=checkAuthentication(apiKey, brngusrreg.getId());
		System.out.println(ret +" ret");
		if(ret==0)
		{
			return null;
		}
		else if(ret==-1)
		{
			return null;
		}

		List<BrngUsrAddress> brngusraddresses = manager.createQuery("Select a From BrngUsrAddress a where a.brngUsrReg.id="+userId +" and a.id="+addressId,BrngUsrAddress.class).
				getResultList();
		List<RegistrationDto> dtoList = new ArrayList();
		RegistrationDto registrationDto = null;
		for(BrngUsrAddress brngusraddress:brngusraddresses){
			//System.out.println("ghhh"+brngUsrRegDetail.getFirstName());
			registrationDto = new RegistrationDto(brngusraddress);
			try
			{
				BrngUsrAddressAttr brngusraddressattr=manager.createQuery("select a from BrngUsrAddressAttr a where a.brngUsrAddressId="+brngusraddress.getId(),BrngUsrAddressAttr.class).getSingleResult();
				registrationDto.setFirstNamea(brngusraddressattr.getFirstName());
				registrationDto.setLastNamea(brngusraddressattr.getLastName());
				registrationDto.setPhoneNumbera(brngusraddressattr.getPhoneNumber());
				registrationDto.setAddressIda(brngusraddressattr.getBrngUsrAddressId());
				registrationDto.setFullAddressa(brngusraddressattr.getFullAddress());
			}
			catch(NoResultException e)
			{
				
			}

			dtoList.add(registrationDto);
		}
		return dtoList;
	
	}
	
	
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		return null;
	}
	
}

public HashMap<String,String> resendOtp(String phone){
	
	
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 registeredTime =new Timestamp(System.currentTimeMillis());
		 BrngUsrRegAttr brngusrregattr=manager.createQuery("Select a From BrngUsrRegAttr a where a.phoneNumber= '"+phone+"'",BrngUsrRegAttr.class).getSingleResult();
		 //brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
			//BrngUsrRegAttr brngusrregattr=manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id= '"+userId+"'",BrngUsrRegAttr.class).getSingleResult();
		OTPUtil otputil=new OTPUtil();
		BrngUsrOtp brngusrotp=new BrngUsrOtp();
		brngusrotp.setEffectiveDate(registeredTime);
		brngusrotp.setBrngUsrReg(brngusrregattr.getBrngUsrReg());
		
		brngusrotp.setOtpCode(otputil.sendOTP(brngusrregattr.getPhoneNumber()));
		manager.persist(brngusrotp);
				message=UserMessages.getUserMessagesNew("ORS");
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


public HashMap<String,String> sendOTPForAddress(String phone,String link,String email,String apikey){
	
	
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		int userId=brngusrreg.getId();
		//UtilityDAO utildao=new UtilityDAO();
		int ret=checkAuthentication(apikey, userId);
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

		OTPUtil otputil=new OTPUtil();
		otputil.sendOTPForAddress(phone, link,brngusrreg.getEmailId(), brngusrreg.getBrngUsrRegAttrs().get(0).getFirstName());
		
				message=UserMessages.getUserMessagesNew("MSS");
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

public int saveNameInDB(int usrRegId,String aadhar,String vehiclerc,String pvin,String drivinglic,String image,Timestamp registeredTime){
	
	
	Timestamp uploadedTime =null;

	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		uploadedTime =new Timestamp(System.currentTimeMillis());
		 BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='H'",BrngLkpFilePath.class).getSingleResult();
		
		String filePath=brnglkpfilepath.getFilePath();
		
		 BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.id="+usrRegId,BrngUsrReg.class).getSingleResult();
		// CommonsMultipartFile aFile =fileUpload[0];
		BrngUsrFiles brngusrfiles=new BrngUsrFiles();
		brngusrfiles.setAadhar(filePath+""+usrRegId+"_"+registeredTime+"/aadhar/"+aadhar);
		//aFile =fileUpload[3];
		brngusrfiles.setDriving_lic(filePath+""+usrRegId+"_"+registeredTime+"/driving_lic/"+drivinglic);
		//aFile =fileUpload[2];
		brngusrfiles.setPvin(filePath+""+usrRegId+"_"+registeredTime+"/pvin/"+pvin);
		//aFile =fileUpload[1];
		brngusrfiles.setVehiclerc(filePath+""+usrRegId+"_"+registeredTime+"/vehiclerc/"+vehiclerc);
		//aFile =fileUpload[1];
		brngusrfiles.setImage(filePath+""+usrRegId+"_"+registeredTime+"/image/"+image);
		brngusrfiles.setEffectiveDate(uploadedTime);
		brngusrfiles.setBrngUsrReg(brngusrreg);
		
		
		manager.merge(brngusrfiles);
		return 1;
	}
	
	
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		return -1;
	}
	
}


public int saveImageInDB(int usrRegId,String image){
	
	
	Timestamp uploadedTime =null;
	uploadedTime =new Timestamp(System.currentTimeMillis());
	//System.out.println("6 : " + registeredTime);
	try
	{
	/* transaction = manager.getTransaction();
	transaction.begin();*/
	//manager.persist(brngusrreg);
	//brngusrregattr.setBrngUsrReg(brngusrreg);
		 BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='H'",BrngLkpFilePath.class).getSingleResult();
			
			String filePath=brnglkpfilepath.getFilePath();
			
		 Query query = manager.
			      createQuery("Select count(*) from BrngUsrFiles a where a.brngUsrReg.id="+usrRegId);
				long count = (long)query.getSingleResult();
				
				if(count>0)
				{
					 query = manager
							.createQuery("UPDATE BrngUsrFiles a SET a.image = :imageUrl,a.effectiveDate=:effectiveDate "
							+ "WHERE a.brngUsrReg.id= :usrRegId");	
					query.setParameter("imageUrl",filePath+""+usrRegId+"/image/"+image );
					query.setParameter("usrRegId",usrRegId);
					query.setParameter("effectiveDate",uploadedTime);
					
					
					query.executeUpdate();
				}
				else
				{
	
		
		 BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.id="+usrRegId,BrngUsrReg.class).getSingleResult();
		// CommonsMultipartFile aFile =fileUpload[0];
		BrngUsrFiles brngusrfiles=new BrngUsrFiles();
		
		brngusrfiles.setImage(filePath+""+usrRegId+"/image/"+image);
		brngusrfiles.setEffectiveDate(uploadedTime);
		brngusrfiles.setBrngUsrReg(brngusrreg);
		
		
		manager.merge(brngusrfiles);
				}
		return 1;
	}
	
	
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		return -1;
	}
	
}

public HashMap<String,String> uploadFile(String email,CommonsMultipartFile[] fileUpload) throws FileNotFoundException{
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	
      try {
	//System.out.println("6 : " + registeredTime);
	BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='S'",BrngLkpFilePath.class).getSingleResult();
	
	BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
	
	String path=brnglkpfilepath.getFilePath();
	
	int userRegId=brngusrreg.getId(); 
	System.out.println(userRegId);
	System.out.println(path);
	System.out.println("q1 : "+path+""+userRegId+"\\aadhar\\");
	File file = new File(path+""+userRegId+"\\aadhar\\");
    file.mkdirs();
     file = new File(path+""+userRegId+"\\vehiclerc\\");
    file.mkdirs();
    file = new File(path+""+userRegId+"\\pvin\\");
    file.mkdirs();
   // file = new File(path+""+userRegId+"/aadhar/"+aadhar.getName());
   // file.mkdir();
    file = new File(path+""+userRegId+"\\driving_lic\\");
    file.mkdirs();
    file = new File(path+""+userRegId+"\\image\\");
    file.mkdirs();
    System.out.println("after file upload ");
    CommonsMultipartFile aFile =fileUpload[0];
         
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\aadhar\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[1];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\vehiclerc\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[2];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\pvin\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[3];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\driving_lic\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[4];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\image\\" + aFile.getOriginalFilename()));
    }
       
  //  saveNameInDB(userRegId, fileUpload);
				message=UserMessages.getUserMessages(1);
				response.put("message", message);
				response.put("response", "1");
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
public HashMap<String,String> getImageURL(String email,String apiKey){
	
	HashMap<String,String> response=new HashMap<>();
	Timestamp registeredTime =null;
	BrngUsrReg brngusrreg=null;
	String message="";
	System.out.println("6 : " + registeredTime);
	try
	{
		 brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
			
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

			
		Query query = manager.
			      createQuery("Select count(*) from BrngUsrFiles where brngUsrReg.id="+userId);
				long count = (long)query.getSingleResult();
		if(count>0)
		{
		
		List<BrngUsrFiles> brngusrfiles=manager.createQuery("Select a From BrngUsrFiles a where a.brngUsrReg.id="+userId+" order by a.effectiveDate desc",BrngUsrFiles.class).getResultList();
		response.put("response", brngusrfiles.get(0).getImage());
		}
		else
		{
			response.put("response", null);
		}
	
	}
	
	
	catch(Exception e)
	{
		e.printStackTrace();
		response.put("response", "-1");
		//transaction.rollback();
		
	}
	return response;
}
public HashMap<String,String> uploadFileTest(MultipartFile aadhar,MultipartFile vehiclerc,MultipartFile drivinglic,MultipartFile pvin,MultipartFile image,String email, int vehicleType) throws FileNotFoundException{
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	
      try {
    	  BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
    	  int userRegId=brngusrreg.getId(); 
	//System.out.println("6 : " + registeredTime);
    	  Query query = manager.
			      createQuery("Select count(*) from BrngUsrFiles a where a.brngUsrReg.id="+userRegId);
				long count = (long)query.getSingleResult();
				
				/*if(count > 0)
				{
					message=UserMessages.getUserMessages(14);
					response.put("message", message);
					response.put("response", "14");
					return response;
				}*/
	BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='S'",BrngLkpFilePath.class).getSingleResult();
	
	
	BufferedOutputStream outputStream =null;
	String path=brnglkpfilepath.getFilePath();
	
	
	
	
	System.out.println(userRegId);
	System.out.println(path);
	System.out.println("q1 : "+path+""+userRegId+"\\aadhar\\");
	Timestamp registeredTime=new Timestamp(System.currentTimeMillis());
	//File file = new File(path+""+userRegId);
	//file.mkdirs();
	File file = new File(path+""+userRegId+"_"+1+"/aadhar/");
    file.mkdirs();
     file = new File(path+""+userRegId+"_"+1+"/vehiclerc/");
    file.mkdirs();
    file = new File(path+""+userRegId+"_"+1+"/pvin/");
    file.mkdirs();
   // file = new File(path+""+userRegId+"/aadhar/"+aadhar.getName());
   // file.mkdir();
    file = new File(path+""+userRegId+"_"+1+"/driving_lic/");
    file.mkdirs();
    file = new File(path+""+userRegId+"_"+1+"/image/");
    file.mkdirs();
    System.out.println("after file upload ");
    /*CommonsMultipartFile aFile =fileUpload[0];
         
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\aadhar\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[1];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\vehiclerc\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[2];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\pvin\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[3];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\driving_lic\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[4];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\image\\" + aFile.getOriginalFilename()));
    }*/
    
    if (!aadhar.getOriginalFilename().isEmpty()) {
         outputStream = new BufferedOutputStream(
              new FileOutputStream(
                    new File(path+""+userRegId+"_"+1+"/aadhar/", aadhar.getOriginalFilename())));
        outputStream.write(aadhar.getBytes());
        outputStream.flush();
        outputStream.close();
    }
        if (!vehiclerc.getOriginalFilename().isEmpty()) {
             outputStream = new BufferedOutputStream(
                  new FileOutputStream(
                        new File(path+""+userRegId+"_"+1+"/vehiclerc/", vehiclerc.getOriginalFilename())));
            outputStream.write(vehiclerc.getBytes());
            outputStream.flush();
            outputStream.close();
        }
         if (!pvin.getOriginalFilename().isEmpty()) {
                 outputStream = new BufferedOutputStream(
                      new FileOutputStream(
                            new File(path+""+userRegId+"_"+1+"/pvin/", pvin.getOriginalFilename())));
                outputStream.write(pvin.getBytes());
                outputStream.flush();
                outputStream.close();
         }
          if (!drivinglic.getOriginalFilename().isEmpty()) {
                     outputStream = new BufferedOutputStream(
                          new FileOutputStream(
                                new File(path+""+userRegId+"_"+1+"/driving_lic/", drivinglic.getOriginalFilename())));
                    outputStream.write(drivinglic.getBytes());
                    outputStream.flush();
                    outputStream.close();
          }
           if (!image.getOriginalFilename().isEmpty()) {
                         outputStream = new BufferedOutputStream(
                              new FileOutputStream(
                                    new File(path+""+userRegId+"_"+1+"/image/", image.getOriginalFilename())));
                        outputStream.write(image.getBytes());
                        outputStream.flush();
                        outputStream.close();
           }
          
       
    saveNameInDB(userRegId,aadhar.getOriginalFilename(),vehiclerc.getOriginalFilename(),pvin.getOriginalFilename(),drivinglic.getOriginalFilename(),image.getOriginalFilename(),registeredTime);
				
				//BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='Y'",BrngLkpServicemanValidated.class).getSingleResult();
				
				
				BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='N'",BrngLkpServicemanValidated.class).getSingleResult();
				 query = manager
						 
						.createQuery("UPDATE BrngUsrReg a SET a.brnglkpservicemanvalidated.id = :validatedId, a.brnglkpvehicleType.id =:vehicleType "
						+ "WHERE a.id= :id");
				 
				 
				query.setParameter("validatedId",brnglkpservicemanvalidated.getId() );
				query.setParameter("id",userRegId);
				query.setParameter("vehicleType", vehicleType);
				
				query.executeUpdate();
				message=UserMessages.getUserMessages(1);
				response.put("message", message);
				response.put("response", "1");
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

public HashMap<String,String> fileuploadServicemanByWeb(MultipartFile aadhar,MultipartFile vehiclerc,MultipartFile drivinglic,MultipartFile pvin,MultipartFile image,String email) throws FileNotFoundException{
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	
      try {
    	  BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
    	  int userRegId=brngusrreg.getId(); 
	//System.out.println("6 : " + registeredTime);
    	  Query query = manager.
			      createQuery("Select count(*) from BrngUsrFiles a where a.brngUsrReg.id="+userRegId);
				long count = (long)query.getSingleResult();
				
				/*if(count > 0)
				{
					message=UserMessages.getUserMessages(14);
					response.put("message", message);
					response.put("response", "14");
					return response;
				}*/
	BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='S'",BrngLkpFilePath.class).getSingleResult();
	
	
	BufferedOutputStream outputStream =null;
	String path=brnglkpfilepath.getFilePath();
	
	
	
	
	System.out.println(userRegId);
	System.out.println(path);
	System.out.println("q1 : "+path+""+userRegId+"\\aadhar\\");
	Timestamp registeredTime=new Timestamp(System.currentTimeMillis());
	File file = new File(path+""+userRegId);
	file.mkdirs();
	 file = new File(path+""+userRegId+"_"+registeredTime+"/aadhar/");
    file.mkdirs();
     file = new File(path+""+userRegId+"_"+registeredTime+"/vehiclerc/");
    file.mkdirs();
    file = new File(path+""+userRegId+"_"+registeredTime+"/pvin/");
    file.mkdirs();
   // file = new File(path+""+userRegId+"/aadhar/"+aadhar.getName());
   // file.mkdir();
    file = new File(path+""+userRegId+"_"+registeredTime+"/driving_lic/");
    file.mkdirs();
    file = new File(path+""+userRegId+"_"+registeredTime+"/image/");
    file.mkdirs();
    System.out.println("after file upload ");
    /*CommonsMultipartFile aFile =fileUpload[0];
         
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\aadhar\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[1];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\vehiclerc\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[2];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\pvin\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[3];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\driving_lic\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[4];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\image\\" + aFile.getOriginalFilename()));
    }*/
    
    if (!aadhar.getOriginalFilename().isEmpty()) {
         outputStream = new BufferedOutputStream(
              new FileOutputStream(
                    new File(path+""+userRegId+"_"+registeredTime+"/aadhar/", aadhar.getOriginalFilename())));
        outputStream.write(aadhar.getBytes());
        outputStream.flush();
        outputStream.close();
    }
        if (!vehiclerc.getOriginalFilename().isEmpty()) {
             outputStream = new BufferedOutputStream(
                  new FileOutputStream(
                        new File(path+""+userRegId+"_"+registeredTime+"/vehiclerc/", vehiclerc.getOriginalFilename())));
            outputStream.write(vehiclerc.getBytes());
            outputStream.flush();
            outputStream.close();
        }
         if (!pvin.getOriginalFilename().isEmpty()) {
                 outputStream = new BufferedOutputStream(
                      new FileOutputStream(
                            new File(path+""+userRegId+"_"+registeredTime+"/pvin/", pvin.getOriginalFilename())));
                outputStream.write(pvin.getBytes());
                outputStream.flush();
                outputStream.close();
         }
          if (!drivinglic.getOriginalFilename().isEmpty()) {
                     outputStream = new BufferedOutputStream(
                          new FileOutputStream(
                                new File(path+""+userRegId+"_"+registeredTime+"/driving_lic/", drivinglic.getOriginalFilename())));
                    outputStream.write(drivinglic.getBytes());
                    outputStream.flush();
                    outputStream.close();
          }
           if (!image.getOriginalFilename().isEmpty()) {
                         outputStream = new BufferedOutputStream(
                              new FileOutputStream(
                                    new File(path+""+userRegId+"_"+registeredTime+"/image/", image.getOriginalFilename())));
                        outputStream.write(image.getBytes());
                        outputStream.flush();
                        outputStream.close();
           }
          
       
    saveNameInDB(userRegId,aadhar.getOriginalFilename(),vehiclerc.getOriginalFilename(),pvin.getOriginalFilename(),drivinglic.getOriginalFilename(),image.getOriginalFilename(),registeredTime);
				
				BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='Y'",BrngLkpServicemanValidated.class).getSingleResult();
				 query = manager
						.createQuery("UPDATE BrngUsrReg a SET a.brnglkpservicemanvalidated.id = :validatedId "
						+ "WHERE a.id= :id");	
				query.setParameter("validatedId",brnglkpservicemanvalidated.getId() );
				query.setParameter("id",userRegId);
				
				
				query.executeUpdate();
				message=UserMessages.getUserMessages(1);
				response.put("message", message);
				response.put("response", "1");
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

public HashMap<String,String> uploadImage(MultipartFile image,String email,String apiKey) throws FileNotFoundException{
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	CommonUtility cm=new CommonUtility();
	  try {
		  BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
		  int userRegId=brngusrreg.getId(); 
	      
    	  int ret=checkAuthentication(apiKey, userRegId);
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

    		
    	 int checkExtension=cm.checkExtension(image.getOriginalFilename());
    	 if(checkExtension==2)
    	 {
    		 message=UserMessages.getUserMessagesNew("FEE");
    		response.put("response","1");
    		response.put("message",message);
    		return response;
    		 
    	 }
	//System.out.println("6 : " + registeredTime);
    	 
	//System.out.println("6 : " + registeredTime);
    	 
	BrngLkpFilePath brnglkpfilepath=manager.createQuery("Select a From BrngLkpFilePath a where a.type='S'",BrngLkpFilePath.class).getSingleResult();
	
	//BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId='"+email+"'",BrngUsrReg.class).getSingleResult();
	BufferedOutputStream outputStream =null;
	String path=brnglkpfilepath.getFilePath();
	//int userRegId=brngusrreg.getId(); 
	
	
	
	System.out.println(userRegId);
	System.out.println(path);
	System.out.println("q1 : "+path+""+userRegId+"\\aadhar\\");
	//File file = new File(path+""+userRegId);
	
	File file = new File(path+""+userRegId+"/image/");
    file.mkdirs();
    System.out.println("after file upload ");
    /*CommonsMultipartFile aFile =fileUpload[0];
         
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\aadhar\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[1];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\vehiclerc\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[2];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\pvin\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[3];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\driving_lic\\" + aFile.getOriginalFilename()));
    }
        aFile =fileUpload[4];
        
        if (!aFile.getOriginalFilename().equals("")) {
            aFile.transferTo(new File(path+""+userRegId+"\\image\\" + aFile.getOriginalFilename()));
    }*/
    
  
           if (!image.getOriginalFilename().isEmpty()) {
                         outputStream = new BufferedOutputStream(
                              new FileOutputStream(
                                    new File(path+""+userRegId+"/image/", image.getOriginalFilename())));
                        outputStream.write(image.getBytes());
                        outputStream.flush();
                        outputStream.close();
           }
          
       
   // saveNameInDB(userRegId,aadhar.getOriginalFilename(),vehiclerc.getOriginalFilename(),pvin.getOriginalFilename(),drivinglic.getOriginalFilename(),image.getOriginalFilename());
			saveImageInDB(userRegId, image.getOriginalFilename())	;
				/*BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='Y'",BrngLkpServicemanValidated.class).getSingleResult();
				Query query = manager
						.createQuery("UPDATE BrngUsrReg a SET a.brnglkpservicemanvalidated.id = :validatedId "
						+ "WHERE a.id= :id");	
				query.setParameter("validatedId",brnglkpservicemanvalidated.getId() );
				query.setParameter("id",userRegId);
				
				
				query.executeUpdate();*/
				message=UserMessages.getUserMessages(1);
				response.put("message", message);
				response.put("response", "1");
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

public float getTotalDistance(String email)
{
	System.out.println("Email"+email);
	float totalDistance=0;
	try{
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		System.out.println("userId"+userId);
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		System.out.println("Login Id"+userLoginId);
		BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
				getSingleResult();
		int completeId=brnglkporderdelstatus.getId();
		List<BrngOrderDelivery> brngOrderDelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngUsrLogin.id= "+userLoginId+" and a.brngLkpOrderDelStatus.id="+completeId,BrngOrderDelivery.class).getResultList();
		for(int i=0;i<brngOrderDelivery.size();i++ )
		{
			System.out.println(brngOrderDelivery.get(i).getBrngOrder().getTotalDistance());
			//BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+brngOrderDelivery.get(i).getBrngOrder().getTotalDistance(),BrngOrder.class).getResultList();
			totalDistance=totalDistance+brngOrderDelivery.get(i).getBrngOrder().getTotalDistance().floatValue();
		}
			
	//	BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+orderId,BrngOrder.class).getResultList();
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		//return -1;
	}
	return totalDistance;
}

public HashMap<String,String> getTotalDistanceAndAmount(String email)
{
	HashMap<String,String> response=new HashMap<>();
	System.out.println("Email"+email);
	float totalDistance=0;
	float totalAmount=0;
	try{
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
		
		int userId=brngusrreg.getId();
		System.out.println("userId"+userId);
		BrngUsrLogin brngusrlogin=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userId+"'",BrngUsrLogin.class).getSingleResult();
		
		int userLoginId=brngusrlogin.getId();
		System.out.println("Login Id"+userLoginId);
		BrngLkpOrderDelStatus brnglkporderdelstatus = manager.createQuery("Select a From BrngLkpOrderDelStatus a where a.delStatus='Y'",BrngLkpOrderDelStatus.class).
				getSingleResult();
		int completeId=brnglkporderdelstatus.getId();
		List<BrngOrderDelivery> brngOrderDelivery=manager.createQuery("Select a From BrngOrderDelivery a where a.brngUsrLogin.id= "+userLoginId+" and a.brngLkpOrderDelStatus.id="+completeId,BrngOrderDelivery.class).getResultList();
		for(int i=0;i<brngOrderDelivery.size();i++ )
		{
			System.out.println(brngOrderDelivery.get(i).getBrngOrder().getTotalDistance());
			//BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+brngOrderDelivery.get(i).getBrngOrder().getTotalDistance(),BrngOrder.class).getResultList();
			totalDistance=totalDistance+brngOrderDelivery.get(i).getBrngOrder().getTotalDistance().floatValue();
			totalAmount=totalAmount+brngOrderDelivery.get(i).getBrngOrder().getServicePrice().floatValue();
		}
			
		
		response.put("distance", Double.toString(Math.ceil(totalDistance)));
		response.put("amount", Double.toString(Math.ceil(totalAmount)));
		response.put("key", "200");
	//	BrngOrder brngorder1=manager.createQuery("Select a From BrngOrder a where a.id= "+orderId,BrngOrder.class).getResultList();
		
	}
	catch(Exception e)
	{
		response.put("key", "500");
		e.printStackTrace();
		//return -1;
	}
	return response;
}

public HashMap<String,String> getPayuDetails() throws FileNotFoundException{
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	 
	
      try {
    	
	List<BrngLkpPayuDetails> brnglkppayudetails=manager.createQuery("Select a From BrngLkpPayuDetails a order by a.id",BrngLkpPayuDetails.class).getResultList();
	
	
	BufferedOutputStream outputStream =null;
	response.put("URL",brnglkppayudetails.get(0).getPayuValue());
	response.put("KEY",brnglkppayudetails.get(1).getPayuValue());
	response.put("SALT",brnglkppayudetails.get(2).getPayuValue());
	
	BrngGeneralLkp brnggenerallkp=manager.createQuery("select a from BrngGeneralLkp a where a.brngKey='weight_allowed'",BrngGeneralLkp.class ).getSingleResult();
	response.put("weightAllowed",brnggenerallkp.getValue());
	
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


public HashMap<String,String> registerServiceManByWebPage(BrngUsrRegAttr brngusrregAttr){
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	MailUtility mlu=new MailUtility();
	try
	{
	int checkEmailStatus=checkEmailExistence(brngusrregAttr.getBrngUsrReg().getEmailId());
	int checkPhoneStatus=checkPhoneExistence(brngusrregAttr.getPhoneNumber());
	if(checkEmailStatus==1)
	{
		message=UserMessages.getUserMessagesNew("D");
		response.put("response","2");
		response.put("message",message);
		return response;
	}
	else if(checkPhoneStatus==1)
	{
		message=UserMessages.getUserMessagesNew("DP");
		response.put("response","2");
		response.put("message",message);
		return response;
	}
	else if(checkEmailStatus==-1)
	{
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		return response;
	}
	//Commented for testing
	/*else if(mlu.checkValidMail(brngusrregAttr.getBrngUsrReg().getEmailId())==0)
	{
		message=UserMessages.getUserMessagesNew("NVM");
		response.put("response","-1");
		response.put("message",message);
		return response;
	}*/
	
	BrngLkpUsrRegStatus brngusrregstatus = manager.createQuery("Select a From BrngLkpUsrRegStatus a where a.statusType='"+brngusrregAttr.getBrngUsrReg().getBrngLkpUsrRegStatus().getStatusType()+"'",BrngLkpUsrRegStatus.class).getSingleResult();
	BrngLkpUsrRegType brngusrregtype = manager.createQuery("Select a From BrngLkpUsrRegType a where a.usrRegType='"+brngusrregAttr.getBrngUsrReg().getBrngLkpUsrRegType().getUsrRegType()+"'",BrngLkpUsrRegType.class).getSingleResult();
	BrngLkpOtpValidated brnglkpotpvalidated= manager.createQuery("Select a From BrngLkpOtpValidated a where a.code='N'",BrngLkpOtpValidated.class).getSingleResult();
	BrngLkpServicemanValidated brnglkpservicemanvalidated= manager.createQuery("Select a From BrngLkpServicemanValidated a where a.code='N'",BrngLkpServicemanValidated.class).getSingleResult();
	Timestamp registeredTime=new Timestamp(System.currentTimeMillis());
	brngusrregAttr.getBrngUsrReg().setBrngLkpUsrRegStatus(brngusrregstatus);
	brngusrregAttr.getBrngUsrReg().setBrngLkpUsrRegType(brngusrregtype);
	
	brngusrregAttr.getBrngUsrReg().setRegisteredDate(registeredTime);
	brngusrregAttr.getBrngUsrReg().setBrnglkpotpvalidated(brnglkpotpvalidated);
	brngusrregAttr.getBrngUsrReg().setBrnglkpservicemanvalidated(brnglkpservicemanvalidated);
	brngusrregAttr.setEffectiveDate(registeredTime);
	
	System.out.println("6 : " + registeredTime);
	
	//insert to brnguser
	manager.persist(brngusrregAttr.getBrngUsrReg());
	
	String email = brngusrregAttr.getBrngUsrReg().getEmailId();
	BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrReg a where a.emailId= '"+email+"'",BrngUsrReg.class).getSingleResult();
	brngusrregAttr.setBrngUsrReg(brngusrreg);
	System.out.println("1 : " + brngusrreg.getEmailId() + " : 2: " + brngusrreg.getId());
	int userId=brngusrreg.getId();
	//insert to brnguserattr
	manager.persist(brngusrregAttr);
	
	
	/*BrngUsrOtp brngusrotp=new BrngUsrOtp();
	brngusrotp.setEffectiveDate(registeredTime);
	brngusrotp.setBrngUsrReg(brngusrregAttr.getBrngUsrReg());
	OTPUtil otputil=new OTPUtil();
	brngusrotp.setOtpCode(otputil.sendOTP(brngusrregAttr.getPhoneNumber()));
	manager.persist(brngusrotp);*/
	
	/*System.out.println(" Code "+brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0));
	
	BrngUsrCode brngusrcode=brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0);
	if(brngusrcode.getRefCode()!="NA")
	{
		System.out.println("2 : " + brngusrcode.getRefCode() );
	}
	GenerateRefCode grf=new GenerateRefCode();
	String refCode=grf.generateRefCode(brngusrregAttr.getBrngUsrReg().getEmailId());
	System.out.println("refCode  1:" +refCode);
	BrngUsrCode brngusrcode1=new BrngUsrCode();
	brngusrcode1.setBrngUsrReg(brngusrreg);
	brngusrcode1.setDescription("Default");
	brngusrcode1.setEffectiveDate(registeredTime);
	brngusrcode.setRefCode(refCode);
	manager.persist(brngusrcode1);*/
//	BrngUsrCode brngusrcode=new BrngUsrCode();
	/*BrngUsrCode brngUsrCode = manager.createQuery("Select a From BrngUsrCode a where a.refCode='"+brngusrregAttr.getBrngUsrReg().getBrngUsrCodes().get(0).getRefCode()+"'",BrngUsrCode.class).getSingleResult();
	int userRegId=0;
	userRegId=brngUsrCode.getBrngUsrReg().getId();
	System.out.println("user Id " +userRegId);
	brngUsrCode.setBrngUsrReg(brngusrregAttr.getBrngUsrReg());
	brngUsrCode.setEffectiveDate(registeredTime);
	brngUsrCode.setRefCode(refCode);
	System.out.println("refCode " +refCode);
	if(userRegId!=0)
	{
	brngUsrCode.setRefCodeId(userRegId);	
	}*/
	//transaction.commit();
	message=UserMessages.getUserMessagesNew("S");
	response.put("response",Integer.toString(userId));
	response.put("message",message);
	return response;
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		return response;
	}
	
}



public HashMap<String,String> vendorServiceManMapping(int vendorId,int servicemanId){
	
	HashMap<String,String> response =new HashMap<>();
	String message="";
	MailUtility mlu=new MailUtility();
	try
	{
	
	
	//Commented for testing
	/*else if(mlu.checkValidMail(brngusrregAttr.getBrngUsrReg().getEmailId())==0)
	{
		message=UserMessages.getUserMessagesNew("NVM");
		response.put("response","-1");
		response.put("message",message);
		return response;
	}*/
	
	BrngVendorMapping brngvendormapping = new BrngVendorMapping();
	
	Timestamp registeredTime=new Timestamp(System.currentTimeMillis());
	brngvendormapping.setUsrRegId(servicemanId);
	brngvendormapping.setVendorId(vendorId);
	brngvendormapping.setRegisteredDate(registeredTime);
	
	
	//insert to brnguser
	manager.persist(brngvendormapping);
	
	
	message=UserMessages.getUserMessagesNew("S");
	response.put("response","1");
	response.put("message",message);
	return response;
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		//transaction.rollback();
		message=UserMessages.getUserMessagesNew("E");
		response.put("response","-1");
		response.put("message",message);
		return response;
	}
	
}

public int checkAuthentication(String token,int regId){
	
	
	try
	{
		System.out.println("token :"+token +" regId"+regId);
		Query query = getManager().
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
