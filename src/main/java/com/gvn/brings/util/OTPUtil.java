package com.gvn.brings.util;



import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OTPUtil {
	public String  sendOTP(String phoneNo) throws ClassNotFoundException, SQLException {
		/*
		 * this method is used to send push messages to all users
		 */
		
		String otPassword=CommonUtility.generateOTPCode();
		try {
//			String userId="Brings";
//			String password="123456";
//			String senderId="JEWELS";
//		//	String mobileNo="9154748948";
			String message="Your OTP for Brings is ";
			String userId="gvnsoftech";
			String password="okvl6017OK";
			String senderId="BRINGF";
			message=message+otPassword;
			
	   String jsonResponse;
			   
			 
			  // con.setRequestProperty("Authorization", "Basic YzY0NTVkZDAtZmZiZi00ZjExLTg5ZjgtYmQ2OTBlNGU3ZTdi");
	  // URL obj = new URL("http://trans.msg360.in/websms/sendsms.aspx?userid="+userId+"&password="+password+"&sender="+senderId+"&mobileno="+phoneNo+"&msg="+URLEncoder.encode(message, "UTF-8").replace("+", "%20"));
	   URL obj = new URL("http://nimbusit.biz/api/SmsApi/SendSingleApi?UserID="+userId+"&Password="+password+"&SenderID="+senderId+"&Phno="+phoneNo+"&Msg="+URLEncoder.encode(message, "UTF-8").replace("+", "%20"));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
			  

			
		
			} catch(Throwable t) {
			   t.printStackTrace();
			}
return otPassword;	
	}
	
	public String  sendOTPForAddress(String phoneNo,String link,String email,String name) throws ClassNotFoundException, SQLException {
		/*
		 * this method is used to send push messages to all users
		 */
		System.out.println("Link For Address "+link);
		try {
			System.out.println(" Link Encoded "+URLEncoder.encode(link+email, "US-ASCII").replace("+", "%20"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String otPassword=CommonUtility.generateOTPCode();
		try {
			String userId="Brings";
			String password="123456";
			String senderId="JEWELS";
		//	String mobileNo="9154748948";
			String message="Your friend "+name+ " has requested you to send your address. Please follow below link :";
			
			
			message=message+otPassword;
			
	   String jsonResponse;
			   
			 
			  // con.setRequestProperty("Authorization", "Basic YzY0NTVkZDAtZmZiZi00ZjExLTg5ZjgtYmQ2OTBlNGU3ZTdi");
	   URL obj = new URL("http://trans.msg360.in/websms/sendsms.aspx?userid="+userId+"&password="+password+"&sender="+senderId+"&mobileno="+phoneNo+"&msg="+URLEncoder.encode(link+email, "US-ASCII").replace("+", "%20"));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
			  

			
		
			} catch(Throwable t) {
			   t.printStackTrace();
			}
return otPassword;	
	}

	public String  sendOTP(String phoneNo,String tempPassword) throws ClassNotFoundException, SQLException {
		/*
		 * this method is used to send push messages to all users
		 */
		
		String otPassword=CommonUtility.generateOTPCode();
		try {
			String userId="Brings";
			String password="123456";
			String senderId="JEWELS";
		//	String mobileNo="9154748948";
			String message="Your temporary password for Brings is "+tempPassword;
			
		//	message=message+otPassword;
			
	   String jsonResponse;
			   
			 
			  // con.setRequestProperty("Authorization", "Basic YzY0NTVkZDAtZmZiZi00ZjExLTg5ZjgtYmQ2OTBlNGU3ZTdi");
	   URL obj = new URL("http://trans.msg360.in/websms/sendsms.aspx?userid="+userId+"&password="+password+"&sender="+senderId+"&mobileno="+phoneNo+"&msg="+URLEncoder.encode(message, "UTF-8").replace("+", "%20"));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
			  

			
		
			} catch(Throwable t) {
			   t.printStackTrace();
			}
return otPassword;	
	}

	
	public String  sendOTPForOrder(String phoneNo,String code,String type) throws ClassNotFoundException, SQLException {
		/*
		 * this method is used to send push messages to all users
		 */
		
		String otPassword=CommonUtility.generateOTPCode();
		try {
			String userId="Brings";
			String password="123456";
			String senderId="JEWELS";
		//	String mobileNo="9154748948";
			String message="";
			if(type.equalsIgnoreCase("pickup"))
			{
				message="Your pickup code for Brings is "+code;
			}
			else
			{
				message="Your delivery code for Brings is "+code;
			}
			
		//	message=message+otPassword;
			
	   String jsonResponse;
			   
			 
			  // con.setRequestProperty("Authorization", "Basic YzY0NTVkZDAtZmZiZi00ZjExLTg5ZjgtYmQ2OTBlNGU3ZTdi");
	   URL obj = new URL("http://trans.msg360.in/websms/sendsms.aspx?userid="+userId+"&password="+password+"&sender="+senderId+"&mobileno="+phoneNo+"&msg="+URLEncoder.encode(message, "UTF-8").replace("+", "%20"));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
			  

			
		
			} catch(Throwable t) {
			   t.printStackTrace();
			}
return otPassword;	
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		OTPUtil otputil=new OTPUtil();
		otputil.sendOTP("8309019593");
	}

}
