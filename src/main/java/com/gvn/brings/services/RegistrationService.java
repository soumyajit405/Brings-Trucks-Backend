package com.gvn.brings.services;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gvn.brings.dao.RegistrationDao;
import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngBankDetails;
import com.gvn.brings.model.BrngLkpOtpValidated;
import com.gvn.brings.model.BrngLkpUsrRegStatus;
import com.gvn.brings.model.BrngLkpUsrRegType;
import com.gvn.brings.model.BrngUsrAddress;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.model.BrngUsrRegAttr;
import com.gvn.brings.model.BrngUsrWalletAttr;
import com.gvn.brings.util.UserMessages;

@Service("registrationService")
public class RegistrationService extends AbstractBaseService{

	@Autowired
	private RegistrationDao registrationDao;
	
	public List<RegistrationDto> getUserRegType(){
		return registrationDao.getUserRegTypeList();
	}
	public List<RegistrationDto> getUserRegStatus(){
		return registrationDao.getUserRegStatusList();
	}
	public List<RegistrationDto> getUserType(){
		return registrationDao.getUserTypeList();
	}
	public HashMap<String,String> registerUser(BrngUsrRegAttr brngusrreg){
		return registrationDao.registerUser(brngusrreg);
	}
	public List<RegistrationDto> getProfileDetails(String email,String apiKey){
		return registrationDao.getProfileDetails(email,apiKey);
	}
	public HashMap<String,String> updateProfile(BrngUsrRegAttr brngusrregattr,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.updateProfile(brngusrregattr,apiKey);
	}
	
	public  HashMap<String,String>  checkUserStatus(String email,String apiKey) throws SQLException, ClassNotFoundException
	{
		return registrationDao.checkUserStatus(email,apiKey);
	}
	
	public HashMap<String,String> insertBankDetails(BrngBankDetails brngbankdetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.insertBankDetails(brngbankdetails,apiKey);
	}
	
	public  List<RegistrationDto>  getBankDetails(String email,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.getBankDetails(email,apiKey);
	}
	
	public  HashMap<String,String>  addMoney(BrngUsrWalletAttr brngusrwallet,String apikey) throws ClassNotFoundException, SQLException{
		return registrationDao.addMoney(brngusrwallet,apikey);
	}
	
	public  BigDecimal  checkBalance(Hashtable<String,String> inputDetails) throws ClassNotFoundException, SQLException{
		return registrationDao.checkBalance(inputDetails);
	}
	
	public  HashMap<String, String>  checkOTP(Hashtable<String,String> inputDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.checkOTP(inputDetails,apiKey);
	}
	
	public  HashMap<String, String>  editAddress(Hashtable<String,String> addressDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.editAddress(addressDetails,apiKey);
	}
	
	public  HashMap<String, String>  deleteAddress(Hashtable<String,String> addressDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.deleteAddress(Integer.parseInt(addressDetails.get("addressId")),addressDetails.get("email"),apiKey);
	}
	
	public  HashMap<String, String>  saveAddress(HashMap<String,String> addressDetails) throws ClassNotFoundException, SQLException{
		return registrationDao.saveAddress(addressDetails);
	}
	
	public  HashMap<String, String>  saveAddressByReference(HashMap<String,String> addressDetails) throws ClassNotFoundException, SQLException{
		return registrationDao.saveAddressByReference(addressDetails);
	}
	
	public  List<RegistrationDto>  getSavedAddress(Hashtable<String,String> inputDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.getSavedAddress(inputDetails.get("email"),apiKey);
	}
	
	public  List<RegistrationDto>  getSavedAddressById(Hashtable<String,String> inputDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.getSavedAddressById(inputDetails.get("email"),apiKey,Integer.parseInt(inputDetails.get("addressId")));
	}
	/*public  String  fileupload(File aadhar,File rc,File pan,File drivingLic,String email) throws ClassNotFoundException, SQLException, FileNotFoundException{
		return registrationDao.uploadFile(aadhar,rc,pan,drivingLic,email);
	}*/
	
	public  HashMap<String,String>  resendOtp(Hashtable<String,String> inputDetails) throws ClassNotFoundException, SQLException{
		return registrationDao.resendOtp(inputDetails.get("phone"));
	}
	
	public  HashMap<String,String>  fileupload(String email,CommonsMultipartFile[] fileUpload) throws ClassNotFoundException, SQLException, FileNotFoundException{
		return registrationDao.uploadFile(email,fileUpload);
	}
	
	public  HashMap<String,String>  getImageURL(Hashtable<String,String> inputDetails,String apiKey) throws ClassNotFoundException, SQLException{
		return registrationDao.getImageURL(inputDetails.get("email"),apiKey);
	}
	
	public  HashMap<String,String>  fileuploadTest(MultipartFile aadhar,MultipartFile vehiclerc,MultipartFile drivinglic,MultipartFile pvin,MultipartFile image,String email, int vehicleType) throws ClassNotFoundException, SQLException, FileNotFoundException{
		return registrationDao.uploadFileTest(aadhar,vehiclerc,drivinglic,pvin,image,email,vehicleType);
	}
	
	public  HashMap<String,String>  fileuploadServicemanByWeb(MultipartFile aadhar,MultipartFile vehiclerc,MultipartFile drivinglic,MultipartFile pvin,MultipartFile image,String email,String firstName,String lastName,String age,String gender,String phoneNumber,String password,String vid, int vehicleId) throws ClassNotFoundException, SQLException, FileNotFoundException{
		
		System.out.println(email + " "+password + " "+firstName + " "+lastName +" "+gender+ " "+phoneNumber+ " "+age);
		 
		
		BrngUsrReg brngusrreg=new BrngUsrReg();
		brngusrreg.setEmailId(email);
		brngusrreg.setPassword(password);
		brngusrreg.setPlayerId("NA");
		BrngLkpUsrRegType brnglkpusrregtype=new BrngLkpUsrRegType();
		brnglkpusrregtype.setUsrRegType("Non-Corporate");
		BrngLkpUsrRegStatus brngLkpUsrRegStatus=new BrngLkpUsrRegStatus();
		brngLkpUsrRegStatus.setStatusType("REGISTERED");
		brngusrreg.setBrngLkpUsrRegStatus(brngLkpUsrRegStatus);
		brngusrreg.setBrngLkpUsrRegType(brnglkpusrregtype);
		BrngUsrRegAttr brngusrregattr=new BrngUsrRegAttr();
		brngusrregattr.setBrngUsrReg(brngusrreg);
		brngusrregattr.setFirstName(firstName);
		brngusrregattr.setLastName(lastName);
		if(gender.equalsIgnoreCase("male"))
		{
			brngusrregattr.setGender("M");	
		}
		else
		{
			brngusrregattr.setGender("F");
		}
		
		brngusrregattr.setPhoneNumber(phoneNumber);
		brngusrregattr.setAge(Integer.parseInt(age));
		
		HashMap<String,String> response=registrationDao.registerServiceManByWebPage(brngusrregattr);
		if(!response.get("response").equalsIgnoreCase("2") && !response.get("response").equalsIgnoreCase("-1"))
		{
			if(!vid.equalsIgnoreCase("NA"))
			{
				System.out.println("ServiceMan Id "+response.get("response"));
				registrationDao.vendorServiceManMapping(Integer.parseInt(vid),Integer.parseInt(response.get("response")));
				
			}
			response=registrationDao.uploadFileTest(aadhar,vehiclerc,drivinglic,pvin,image,email,vehicleId);
			
			String message=UserMessages.getUserMessagesNew("S");
			response.put("response","1");
			response.put("message",message);
			
		}
		
		return response;
	}
	
	public  HashMap<String,String>  uploadImage(MultipartFile image,String email,String apiKey) throws ClassNotFoundException, SQLException, FileNotFoundException{
		return registrationDao.uploadImage(image,email,apiKey);
	}
	
	public  HashMap<String,String>  getPayuDetails() throws ClassNotFoundException, SQLException, FileNotFoundException{
		return registrationDao.getPayuDetails();
	}
	
	public HashMap<String, String> checkMobileNumberExistence(Hashtable<String,String> inputDetails){
			return registrationDao.checkMobileNumberExistence(inputDetails);
	}
	
	public HashMap<String, String> loginWithSocialMedia(Hashtable<String,String> inputDetails){
		return registrationDao.loginWithSocialMedia(inputDetails);
	}
	
	public HashMap<String,String> getTotalDistanceAndAmount(String email)
	{
		return registrationDao.getTotalDistanceAndAmount(email);
	}
	
	public HashMap<String,String> sendOTPForAddress(String phone,String link,String email,String apikey){
		{
			return registrationDao.sendOTPForAddress(phone,link,email,apikey);
		}
	}
	
}
