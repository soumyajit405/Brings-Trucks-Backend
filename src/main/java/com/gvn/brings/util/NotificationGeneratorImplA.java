package com.gvn.brings.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gvn.brings.dao.OrderDao;
import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngVehicleOrder;

public class NotificationGeneratorImplA implements NotificationGenerator {
 
		  
	    @Override
		public void generateNotificationForBooking(BrngOrder brngorder, int orderId, ArrayList<String> listOfServiceMen) throws ClassNotFoundException, SQLException {
	    	PushHelper pushhelper=new PushHelper();
	    	OrderDao orderdao=new OrderDao();
			pushhelper.pushToAllServiceMan(listOfServiceMen, Integer.toString(orderId));
			
		}

	    @Override
		public void generateNotificationForVehicleBooking(BrngVehicleOrder brngorder, int orderId, ArrayList<String> listOfServiceMen) throws ClassNotFoundException, SQLException {
	    	PushHelper pushhelper=new PushHelper();
	    	OrderDao orderdao=new OrderDao();
			pushhelper.pushToAllServiceMan(listOfServiceMen, Integer.toString(orderId));
			
		}
} 

