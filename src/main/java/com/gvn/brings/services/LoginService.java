package com.gvn.brings.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvn.brings.dao.LoginDao;
import com.gvn.brings.dao.RegistrationDao;
import com.gvn.brings.dto.RegistrationDto;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrReg;


@Service("loginService")
public class LoginService extends AbstractBaseService{

	@Autowired
	private LoginDao loginDao;
	
	public HashMap<String,String> loginUser(Hashtable<String,String> logindetails){
		return loginDao.loginUser(logindetails);
	}
	
	public HashMap<String,String> sendPasswordToMail(BrngUsrReg brngUsrReg) throws SQLException{
		return loginDao.sendPasswordToMail(brngUsrReg);
	}
	
	public HashMap<String,String> changePassword(BrngUsrReg brngUsrReg) throws SQLException, ClassNotFoundException{
		return loginDao.changePassword(brngUsrReg);
	}
	public HashMap<String,String> logoutUser(BrngUsrReg brngUsrReg,String apiKey) throws SQLException, ClassNotFoundException{
		return loginDao.logoutUser(brngUsrReg,apiKey);
	}
}
