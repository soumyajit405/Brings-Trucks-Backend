package com.gvn.brings.web.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.services.LoginService;
import com.gvn.brings.services.RegistrationService;

@RestController
public class LoginController extends AbstractBaseController{

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = REST+"usrLogin", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String>  loginuser(@RequestBody Hashtable<String,String> logindetails){
		return loginService.loginUser(logindetails);
	}
	
	@RequestMapping(value = REST+"tempPassword", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String>  sendPasswordToMail(@RequestBody BrngUsrReg brngUsrReg) throws SQLException{
		System.out.println(brngUsrReg.getEmailId());
		return loginService.sendPasswordToMail(brngUsrReg);
	}
	
	@RequestMapping(value = REST+"changePassword", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String>  changePassword(@RequestBody BrngUsrReg brngUsrReg) throws SQLException, ClassNotFoundException{
		System.out.println(brngUsrReg.getEmailId());
		return loginService.changePassword(brngUsrReg);
	}
	
	@RequestMapping(value = REST+"logoutUser", method = RequestMethod.POST,headers="Accept=application/json")
	public HashMap<String,String>  logoutUser(@RequestBody BrngUsrReg brngUsrReg,@RequestHeader(value="api-key") String apiKey) throws SQLException, ClassNotFoundException{
		System.out.println(brngUsrReg.getEmailId());
		return loginService.logoutUser(brngUsrReg,apiKey);
	}

}
