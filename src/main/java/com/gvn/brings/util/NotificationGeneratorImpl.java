package com.gvn.brings.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gvn.brings.dao.OrderDao;
import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngVehicleOrder;

public class NotificationGeneratorImpl {
	
	private NotificationGenerator ngenerator;
	
	public void registerNotificationGenerator(NotificationGenerator ngenerator) 
    { 
        this.ngenerator = ngenerator; 
    } 
	
	public void sendNotificationForBooking(final BrngOrder brngorder,final int orderId, final ArrayList<String> listOfServiceMen) 
    { 
  
        // An Async task always executes in new thread 
        new Thread(new Runnable() { 
            public void run() 
            { 
                // check if listener is registered. 
                if (ngenerator != null) { 
  
                    // invoke the callback method of class A 
                    try {
						ngenerator.generateNotificationForBooking(brngorder, orderId,listOfServiceMen);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                } 
            } 
        }).start(); 
    }
	
	
	public void sendNotificationForVehicleBooking(final BrngVehicleOrder brngorder,final int orderId, final ArrayList<String> listOfServiceMen) 
    { 
  
        // An Async task always executes in new thread 
        new Thread(new Runnable() { 
            public void run() 
            { 
                // check if listener is registered. 
                if (ngenerator != null) { 
  
                    // invoke the callback method of class A 
                    try {
						ngenerator.generateNotificationForVehicleBooking(brngorder, orderId,listOfServiceMen);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                } 
            } 
        }).start(); 
    }

}


