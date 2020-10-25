package com.gvn.brings.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class UserMessages {

	public static String getUserMessages(int code)
	{
		if(code==0)
		{
			return "Your request has been failed. Please check the values sent.";
			
		}
		else if(code==1)
		{
			return "Your request is succesfully processed";
		}
		
		else if(code==-1)
		{
			return "Your request failed due to internal error. Please try again";
		}
		
		else if(code==2)
		{
			return "There seems to be duplicate value for the same parameter.";
		}
		else if(code==3)
		{
			return "You have not registered. Please register first.";
		}
		else if(code==4)
		{
			return "Order is already accepted. ";
		}
		else if(code==5)
		{
			return "Order is already picked. ";
		}
		else if(code==6)
		{
			return "Order is already delivered. ";
		}
		
		else if(code==7)
		{
			return "Order is already paid. ";
		}
		else if(code==8)
		{
			return "OTP is not matching. ";
		}
		else if(code==9)
		{
			return "OTP is not generated. ";
		}
		else if(code==10)
		{
			return "Order is already cancelled. ";
		}
		else if(code==11)
		{
			return "Otp not verified. ";
		}
		else if(code==12)
		{
			return "Delivery Code not matching. ";
		}
		else if(code==13)
		{
			return "Please upload your proofs to get the orders. ";
		}
		else if(code==14)
		{
			return "Files are already uploaded .";
		}
		else if(code==15)
		{
			return "PickUp Code not matching. ";
		}
		
		
		return "";
	}
	
	
	public static String getUserMessagesNew(String code)
	{
		if(code.equalsIgnoreCase("D"))
		{
			return "Email already used.Sign in with new email.";
			
		}
		if(code.equalsIgnoreCase("DP"))
		{
			return "Phone number already used.Sign in with new phone number.";
			
		}
		if(code.equalsIgnoreCase("DPUP"))
		{
			return "Phone number already used.Update new mobile number.";
			
		}
		else if(code.equalsIgnoreCase("S"))
		{
			return "Successfully registered.Log in now.";
		}
		
		else if(code.equalsIgnoreCase("E"))
		{
			return "Internal Server Error.";
		}
		
		else if(code.equalsIgnoreCase("TE"))
		{
			return "Token Expired.";
		}
		
		else if(code.equalsIgnoreCase("U"))
		{
			return "Profile updated successfully.";
		}
		else if(code.equalsIgnoreCase("AB"))
		{
			return "Added bank successfully.";
		}
		else if(code.equalsIgnoreCase("UW"))
		{
			return "Updated Wallet successfully.";
		}
		else if(code.equalsIgnoreCase("OM"))
		{
			return "OTP Verfied.";
		}
		else if(code.equalsIgnoreCase("ONG"))
		{
			return "OTP not generated.";
		}
		else if(code.equalsIgnoreCase("ONM"))
		{
			return "OTP not matching.";
		}
		else if(code.equalsIgnoreCase("AA"))
		{
			return "Address added successfully.";
		}
		else if(code.equalsIgnoreCase("ADS"))
		{
			return "Address deleted successfully.";
		}
		else if(code.equalsIgnoreCase("AUS"))
		{
			return "Address updated successfully.";
		}
		else if(code.equalsIgnoreCase("ORS"))
		{
			return "OTP Resent successfully.";
		}
		else if(code.equalsIgnoreCase("OSS"))
		{
			return "OTP Sent successfully.";
		}
		else if(code.equalsIgnoreCase("MSS"))
		{
			return "Message Sent successfully.";
		}
		
		else if(code.equalsIgnoreCase("ONV"))
		{
			return "OTP not verified.";
		}
		else if(code.equalsIgnoreCase("PUP"))
		{
			return "Please upload your proofs to get the orders.";
		}
		else if(code.equalsIgnoreCase("LS"))
		{
			return "Logged in.";
		}
		else if(code.equalsIgnoreCase("NR"))
		{
			return "You have not registered.Register now.";
		}
		else if(code.equalsIgnoreCase("PSM"))
		{
			return "Password sent to mail/phone.";
		}
		else if(code.equalsIgnoreCase("PCS"))
		{
			return "Password changed successfully.";
		}
		else if(code.equalsIgnoreCase("LOS"))
		{
			return "Logged out.";
		}
		else if(code.equalsIgnoreCase("OPS"))
		{
			return "Order received.";
		}
		else if(code.equalsIgnoreCase("OAC"))
		{
			return "Order already cancelled.";
		}
		else if(code.equalsIgnoreCase("OAP"))
		{
			return "Order already paid.";
		}
		else if(code.equalsIgnoreCase("PD"))
		{
			return "Payment done.";
		}
		else if(code.equalsIgnoreCase("OAA"))
		{
			return "Order already accepted.";
		}
		else if(code.equalsIgnoreCase("OR"))
		{
			return "Order retried.";
		}
		else if(code.equalsIgnoreCase("TC"))
		{
			return "Trip completed.";
		}
		else if(code.equalsIgnoreCase("OAPI"))
		{
			return "Order is already picked.";
		}
		else if(code.equalsIgnoreCase("OCS"))
		{
			return "Order cancelled successfully.";
		}
		else if(code.equalsIgnoreCase("OAS"))
		{
			return "Order accepted successfully.";
		}
		else if(code.equalsIgnoreCase("PNM"))
		{
			return "PickUp Code not matching.";
		}
		else if(code.equalsIgnoreCase("OPIS"))
		{
			return "Order picked successfully.";
		}
		else if(code.equalsIgnoreCase("ONP"))
		{
			return "Payment not done.";
		}
		else if(code.equalsIgnoreCase("ONPI"))
		{
			return "Order not picked.";
		}
		else if(code.equalsIgnoreCase("DCNM"))
		{
			return "Delivery Code not matching. ";
		}
		else if(code.equalsIgnoreCase("NVM"))
		{
			return "Not a valid mail.";
		}
		else if(code.equalsIgnoreCase("RS"))
		{
			return "Rated successfully.";
		}
		else if(code.equalsIgnoreCase("TAR"))
		{
			return "Delivery already rated.";
		}
		else if(code.equalsIgnoreCase("WUS"))
		{
			return "Wallet updated successfully.";
		}
		else if(code.equalsIgnoreCase("FEE"))
		{
			return "Please upload an image(.jpeg,.jpg,.png).";
		}
		else if(code.equalsIgnoreCase("IE"))
		{
			return "Please enter valid email.";
		}
		else if(code.equalsIgnoreCase("IP"))
		{
			return "Password should be between 6 to 12 characters.";
		}
		else if(code.equalsIgnoreCase("IP"))
		{
			return "Password should be between 6 to 12 characters.";
		}
		else if(code.equalsIgnoreCase("SOAP"))
		{
			return "Same order already placed. Please try with different values.";
		}
		else if(code.equalsIgnoreCase("AIQ"))
		{
			return "You already have undelivered orders.Please complete those first.";
		}
		else if(code.equalsIgnoreCase("ARFW"))
		{
			return "You have already applied. We will get back to you soon.";
		}
		
		else if(code.equalsIgnoreCase("RFW"))
		{
			return "Thankyou. We will get back to you soon.";
		}
		
		else if(code.equalsIgnoreCase("ARFW"))
		{
			return "You have already applied. We will get back to you soon.";
		}
		
		else if(code.equalsIgnoreCase("USS"))
		{
			return "User Subscribed. We will get regular updates from us.";
		}
		
		else if(code.equalsIgnoreCase("UAS"))
		{
			return "User already Subscribed. We will get back to you soon.";
		}
		
		else if(code.equalsIgnoreCase("CE"))
		{
			return "Coupon Expired";
		}
		
		else if(code.equalsIgnoreCase("CNS"))
		{
			return "Coupon Not Started.";
		}
		
		else if(code.equalsIgnoreCase("CALU"))
		{
			return "Coupon Already Used.";
		}
		
		else if(code.equalsIgnoreCase("CAS"))
		{
			return "Coupon Amount Not Sufficient";
		}
		
		/*else if(code==7)
		{
			return "Order is already paid. ";
		}
		else if(code==8)
		{
			return "OTP is not matching. ";
		}
		else if(code==9)
		{
			return "OTP is not generated. ";
		}
		else if(code==10)
		{
			return "Order is already cancelled. ";
		}
		
		else if(code==12)
		{
			return "Delivery Code not matching. ";
		}
		else if(code==13)
		{
			return "Please upload your proofs to get the orders. ";
		}
		else if(code==14)
		{
			return "Files are already uploaded .";
		}
		else if(code==15)
		{
			return "PickUp Code not matching. ";
		}*/
		
		
		return "";
	}
	
	
}
