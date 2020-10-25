package com.gvn.brings.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.dto.TestDto;
import com.gvn.brings.model.BrngLkpFilePath;
import com.gvn.brings.model.BrngLkpPayuDetails;
import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngOrderDelivery;
import com.gvn.brings.model.BrngTest;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrReg;


@Repository
@Transactional
public class CommonDBHelper extends AbstractBaseDao {
	
	@PersistenceContext
    private EntityManager manager;
	
	public String getPlayerIdForBuyer(String wod){
		System.out.println("order Id"+wod);
		BrngOrder brngOrders = manager.createQuery("Select a From BrngOrder a where a.id="+wod,BrngOrder.class).getSingleResult();
		System.out.println("player Id"+brngOrders.getBrngUsrLogin().getBrngUsrReg().getPlayerId());
			return brngOrders.getBrngUsrLogin().getPlayerId();
	}
	
	public String getPlayerIdForServiceMan(String wod){
		BrngOrderDelivery brngorderdelivery = manager.createQuery("Select a From BrngOrderDelivery a where a.brngOrder.id="+wod,BrngOrderDelivery.class).getSingleResult();
		return brngorderdelivery.getBrngOrder().getBrngUsrLogin().getBrngUsrReg().getPlayerId();
	}
	
	public ArrayList<String> getEmailFromLoginId(String listOfIds){
		ArrayList<String> listOfPlayerIds=new ArrayList<>();
		List<BrngUsrLogin> brngusrlogins = manager.createQuery("Select a From BrngUsrLogin a where a.id in "+listOfIds,BrngUsrLogin.class).getResultList();
		
		for(BrngUsrLogin brngusrlogin:brngusrlogins)
		{
			listOfPlayerIds.add(brngusrlogin.getPlayerId());
		}
		return listOfPlayerIds;
	}
	
	public  String getFilePath()
	{
		BrngLkpFilePath brnglkpfilepath=getManager().createQuery("Select a From BrngLkpFilePath",BrngLkpFilePath.class).getSingleResult();
		return brnglkpfilepath.getFilePath();
	}
	
	
	


}
