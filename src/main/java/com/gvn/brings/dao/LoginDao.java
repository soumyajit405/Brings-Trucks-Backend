package com.gvn.brings.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.dto.OrderDto;
import com.gvn.brings.model.BrngBankDetails;
import com.gvn.brings.model.BrngLkpIsPaid;
import com.gvn.brings.model.BrngLkpPayPercent;
import com.gvn.brings.model.BrngLkpUsrRegType;
import com.gvn.brings.model.BrngLkpUsrType;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrOtp;
import com.gvn.brings.model.BrngUsrPassChange;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.model.BrngUsrRegAttr;
import com.gvn.brings.util.CommonUtility;
import com.gvn.brings.util.MailUtility;
import com.gvn.brings.util.OTPUtil;
import com.gvn.brings.util.PushHelper;
import com.gvn.brings.util.SHAUtility;
import com.gvn.brings.util.TempPasswordUtil;
import com.gvn.brings.util.UserMessages;

@Transactional
@Repository
public class LoginDao extends AbstractBaseDao{

	@PersistenceContext
    private EntityManager manager;
	public HashMap<String,String> loginUser(Hashtable<String,String> logindetails){
		String message="";
		HashMap<String,String> response=new HashMap<>();
		try
		{
			
			CommonUtility cm=new CommonUtility();
			
			//	boolean emailStatus=cm.isEmailValid(logindetails.get("emailId"));
				
				
			/*	if(emailStatus==false)
				{
					message=UserMessages.getUserMessagesNew("IE");
					response.put("response","2");
					response.put("message",message);
					return response;
				}
				boolean passwordStatus=cm.isPasswordValid(logindetails.get("password"));
				if(passwordStatus==false)
				{
					message=UserMessages.getUserMessagesNew("IP");
					response.put("response","2");
					response.put("message",message);
					return response;
				}*/
			BrngUsrRegAttr brngusrregattr=manager.createQuery("Select a From BrngUsrRegAttr a where a.phoneNumber= '"+logindetails.get("phoneNumber")+"'",BrngUsrRegAttr.class).getSingleResult();
			
			int userId=brngusrregattr.getBrngUsrReg().getId();
			System.out.println("userId : " + userId);
			
			/*if(brngusrreg1.getBrnglkpotpvalidated().getCode().equalsIgnoreCase("N"))
			{
				message=UserMessages.getUserMessagesNew("ONV");
				response.put("message", message);
				response.put("response", "11");
				return response;
			}
			*/
			if(logindetails.get("usrType").equalsIgnoreCase("S") && brngusrregattr.getBrngUsrReg().getBrnglkpservicemanvalidated().getCode().equalsIgnoreCase("N"))
			{
				message=UserMessages.getUserMessagesNew("PUP");
				response.put("message", message);
				response.put("response", "13");
				response.put("email",brngusrregattr.getBrngUsrReg().getEmailId());
				return response;
			}
			/*byte[] salt=SHAUtility.getSalt();
			String userPassword =SHAUtility.get_SHA_1_SecurePassword(logindetails.get("password"),salt);
			*/
			long count=0;
			try
			{
			List<BrngUsrOtp> brngotpdetails = manager.createQuery("Select a From BrngUsrOtp a where a.brngUsrReg.id="+userId+" order by effectiveDate desc",BrngUsrOtp.class).
					getResultList();
			System.out.println(brngotpdetails.get(0).getOtpCode());
			System.out.println(logindetails.get("otp"));
			if(logindetails.get("otp") .equalsIgnoreCase( brngotpdetails.get(0).getOtpCode()))
			{
				count=1;
			}
			
			}
			catch(NoResultException e)
			{
				message=UserMessages.getUserMessagesNew("NR");
				response.put("message", message);
				response.put("response", "3");	
			return response;	
			}
			System.out.println("count"+count);
			if(brngusrregattr.getPhoneNumber().equalsIgnoreCase(logindetails.get("phoneNumber")) && count >=1)
			//if(brngusrreg1.getEmailId().equalsIgnoreCase(logindetails.get("emailId")) && brngusrreg1.getPassword().equalsIgnoreCase(logindetails.get("password")))
			{
				 Timestamp loginTime=new Timestamp(System.currentTimeMillis());
					Query query = manager.
						      createQuery("Select count(*) from BrngUsrLogin where brngUsrReg.id="+userId);
							 count = (long)query.getSingleResult();
							 String token=SHAUtility.generateRandomToken();
							if(count>0)
							{
								
								System.out.println("player Id "+logindetails.get("playerId"));
								BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a  where a.usrType='"+logindetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
								System.out.println("inside If " );
							 query = manager
										.createQuery("UPDATE BrngUsrLogin a SET a.loginTime = :loginTime,a.playerId=:playerId,a.logoutTime=:logoutTime,a.brngLkpUsrType.id=:type,a.token=:token "
										+ "WHERE a.brngUsrReg.id= :id");
										query.setParameter("id", userId);
										query.setParameter("loginTime",loginTime);
										query.setParameter("playerId",logindetails.get("playerId"));
										query.setParameter("logoutTime",null);
										query.setParameter("type",brnglkpusrtype.getId());
										query.setParameter("token",token);
										
											query.executeUpdate();
							}
							else
							{
								System.out.println("player Id "+logindetails.get("playerId"));
								BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a where a.usrType='"+logindetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
								
								System.out.println("inside else " );
								BrngUsrLogin brngusrlogin=new BrngUsrLogin();
								brngusrlogin.setPlayerId(logindetails.get("playerId"));
								BrngUsrReg brngusrreg=new BrngUsrReg();
								brngusrreg.setId(userId);
								brngusrlogin.setBrngUsrReg(brngusrreg);
								brngusrlogin.setBrngLkpUsrType(brnglkpusrtype);
								brngusrlogin.setToken(token);
								//System.out.println(brngusrlogin.getBrngUsrReg().getId());
								brngusrlogin.setLoginTime(loginTime);
								System.out.println("before setting " );
								//brngusrlogin.setBrngUsrReg(brngusrlogin.getBrngUsrReg());
								manager.persist(brngusrlogin);
							}
							message=UserMessages.getUserMessagesNew("LS");
							response.put("message", message);
							response.put("response", "1");
							response.put("email", brngusrregattr.getBrngUsrReg().getEmailId());
							response.put("phone", brngusrregattr.getPhoneNumber());
							response.put("fullName", brngusrregattr.getFirstName()+" "+brngusrregattr.getLastName());
							response.put("token", token);
							
			}
			
			else
			{
				message=UserMessages.getUserMessagesNew("ONM");
				response.put("message", message);
				response.put("response", "0");
				
			}
			
			/*else
			{
				Query query = manager.
					      createQuery("Select count(*) from BrngUsrPassChange a where a.brngUsrReg.emailId= '"+logindetails.get("emailId")+"'");
						long count = (long)query.getSingleResult();
						
						if(count>0)
						{
				BrngUsrPassChange brngusrchangepass=manager.createQuery("Select a From BrngUsrPassChange a where a.brngUsrReg.emailId= '"+logindetails.get("emailId")+"' order by a.effectiveDate desc limit 1 ",BrngUsrPassChange.class).getSingleResult();
				String pwd=brngusrchangepass.getPassword();
				if(brngusrreg1.getEmailId().equalsIgnoreCase(logindetails.get("emailId")) && pwd.equalsIgnoreCase(logindetails.get("password")))
				{
					Timestamp loginTime=new Timestamp(System.currentTimeMillis());
					 query = manager.
						      createQuery("Select count(*) from BrngUsrLogin where brngUsrReg.id="+userId);
							 count = (long)query.getSingleResult();
							
							if(count>0)
							{
								BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a  where a.usrType='"+logindetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
								System.out.println("inside If " );
							 query = manager
										.createQuery("UPDATE BrngUsrLogin a SET a.loginTime = :loginTime,a.playerId=:playerId,a.logoutTime=:logoutTime,a.brngLkpUsrType.id=:type "
										+ "WHERE a.brngUsrReg.id= :id");
										query.setParameter("id", userId);
										query.setParameter("loginTime",loginTime);
										query.setParameter("playerId",logindetails.get("playerId"));
										query.setParameter("logoutTime",null);
										query.setParameter("type",brnglkpusrtype.getId());
										
											query.executeUpdate();
							}
							else
							{
								BrngLkpUsrType brnglkpusrtype = manager.createQuery("Select a From BrngLkpUsrType a where a.usrType='"+logindetails.get("usrType")+"'",BrngLkpUsrType.class).getSingleResult();
								
								System.out.println("inside else " );
								BrngUsrLogin brngusrlogin=new BrngUsrLogin();
								brngusrlogin.setPlayerId(logindetails.get("playerId"));
								BrngUsrReg brngusrreg=new BrngUsrReg();
								brngusrreg.setId(userId);
								brngusrlogin.setBrngUsrReg(brngusrreg);
								brngusrlogin.setBrngLkpUsrType(brnglkpusrtype);
								//System.out.println(brngusrlogin.getBrngUsrReg().getId());
								brngusrlogin.setLoginTime(loginTime);
								System.out.println("before setting " );
								//brngusrlogin.setBrngUsrReg(brngusrlogin.getBrngUsrReg());
								manager.persist(brngusrlogin);
							}
							
							//
							PushHelper pushhelper=new PushHelper();
							pushhelper.pushTest(logindetails.get("playerId"), "Welcome");
							message=UserMessages.getUserMessages(1);
							response.put("message", message);
							response.put("response", "1");
							
				}
				else
				{
					message=UserMessages.getUserMessages(0);
					response.put("message", message);
					response.put("response", "0");
				}
						}
				else
				{
				message=UserMessages.getUserMessages(0);
				response.put("message", message);
				response.put("response", "0");
				}
			}*/
			
		
		}
		catch(NoResultException nre)
		{
			message=UserMessages.getUserMessagesNew("NR");
			response.put("message", message);
			response.put("response", "3");	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
		
	}
	
	public  HashMap<String,String> sendPasswordToMail(BrngUsrReg brngusrreg) throws SQLException{
		
		HashMap<String,String> response =new HashMap<>();
		String message="";
		CommonUtility cm=new CommonUtility();
		try
		{
			boolean emailStatus=cm.isEmailValid(brngusrreg.getEmailId());
			
			
			if(emailStatus==false)
			{
				message=UserMessages.getUserMessagesNew("IE");
				response.put("response","2");
				response.put("message",message);
				return response;
			}
			String email=brngusrreg.getEmailId();
		String password = null;
		Query query = manager.
			      createQuery("SELECT id  FROM BrngUsrReg WHERE emailId = '"+email+"'");
		List<Integer> results = query.getResultList();
				if(results.size()>0)
				{
					   Date date = new Date();
		               
		                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		               
		                //to convert Date to String, use format method of SimpleDateFormat class.
		                String strDate = dateFormat.format(date);
					TempPasswordUtil gtp=new TempPasswordUtil();
					// password=gtp.getPassword();
					String tempPassword=gtp.getPassword();
					 byte[] salt=SHAUtility.getSalt();
					 password=SHAUtility.get_SHA_1_SecurePassword(tempPassword, salt);
					 BrngUsrPassChange brngUsrPassChange =new BrngUsrPassChange();
					 BrngUsrReg brngUsrReg=new BrngUsrReg();
					 brngUsrReg.setId(results.get(0));
					 brngUsrPassChange.setBrngUsrReg(brngUsrReg);
					 brngUsrPassChange.setEffectiveDate(strDate);
					 brngUsrPassChange.setPassword(password);
					 
					 manager.persist(brngUsrPassChange);
					 
					 List<BrngUsrRegAttr> brngUsrRegDetails = manager.createQuery("Select a From BrngUsrRegAttr a where a.brngUsrReg.id="+results.get(0),BrngUsrRegAttr.class).
								getResultList();
					 System.out.println("Phone " +brngUsrRegDetails.get(0).getPhoneNumber());
					 MailUtility mailutility=new  MailUtility();
					 mailutility.sendEmail(email, tempPassword);
					 OTPUtil otputil=new OTPUtil();
					 
					 otputil.sendOTP(brngUsrRegDetails.get(0).getPhoneNumber(),tempPassword);
					 message=UserMessages.getUserMessagesNew("PSM");
						response.put("message", message);
						response.put("response", "1");
						query = manager
								.createQuery("UPDATE BrngUsrReg a SET a.password = :password "
								+ "WHERE a.emailId= :email");
						 query.setParameter("password", password);
							query.setParameter("email",email);
							query.executeUpdate();
				}
				else
				{
					message=UserMessages.getUserMessagesNew("NR");
					response.put("message", message);
					response.put("response", "3");
				}
		
				
			
	
		}catch(Exception e){
			e.printStackTrace();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}  
		finally{
			
		}  
		
		return response;	 
	}
	
	
	public 	HashMap<String,String> changePassword(BrngUsrReg brngusrreg) throws SQLException, ClassNotFoundException
	{
		HashMap<String,String> response =new HashMap<>();
		String message="";
		CommonUtility cm=new CommonUtility();
		try
		{
			boolean passwordStatus=cm.isPasswordValid(brngusrreg.getPassword());
			if(passwordStatus==false)
			{
				message=UserMessages.getUserMessagesNew("IP");
				response.put("response","2");
				response.put("message",message);
				return response;
			}
		String password = null;
		Query query = manager
				.createQuery("UPDATE BrngUsrReg a SET a.password = :password "
				+ "WHERE a.emailId= :email");
		byte[] salt=SHAUtility.getSalt();
		brngusrreg.setPassword(SHAUtility.get_SHA_1_SecurePassword(brngusrreg.getPassword(), salt));
				query.setParameter("password", brngusrreg.getPassword());
				query.setParameter("email",brngusrreg.getEmailId());
				query.executeUpdate();
				message=UserMessages.getUserMessagesNew("PCS");
				response.put("message", message);
				response.put("response", "1");
				}
	catch(Exception e){
			e.printStackTrace();
			message=UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}  
		finally{
			
		} 
		return response;
			
	}
	
	
	public 	HashMap<String,String> logoutUser(BrngUsrReg brngUsrReg,String apiKey) throws SQLException, ClassNotFoundException
	{	
		HashMap<String,String> response =new HashMap<>();
		String message="";
		try
	{
	String password = null;
	Timestamp logOutTime=new Timestamp(System.currentTimeMillis());
	Query query = manager.
		      createQuery("SELECT id  FROM BrngUsrReg WHERE emailId = '"+brngUsrReg.getEmailId()+"'");
	List<Integer> results = query.getResultList();
	
	int ret=checkAuthentication(apiKey,results.get(0));
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

	 query = manager
			.createQuery("UPDATE BrngUsrLogin a SET a.logoutTime =:time,a.playerId=:playerId ,a.token=:token"
			+ "WHERE a.brngUsrReg.id= :id");
			query.setParameter("time", logOutTime);
			query.setParameter("id",results.get(0));
			query.setParameter("playerId","NA");
			query.setParameter("token","NA");
			query.executeUpdate();
			message=UserMessages.getUserMessagesNew("LOS");
			response.put("message", message);
			response.put("response", "1");
			}
catch(Exception e){
		e.printStackTrace();
		message=UserMessages.getUserMessagesNew("E");
		response.put("message", message);
		response.put("response", "-1");
	}  
	finally{
		
	} 

return response;
	}
	
	public int checkLoginExistence(int userRegId)
	{
		try
		{
		BrngUsrReg brngusrreg=manager.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '"+userRegId+"' and a.logoutTime='NA'",BrngUsrReg.class).getSingleResult();
		}
		catch(NoResultException nre)
		{
			return 1;
		}
		return 0;
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
