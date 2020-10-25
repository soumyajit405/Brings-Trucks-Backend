package com.gvn.brings.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gvn.brings.model.BrngOrder;
import com.gvn.brings.model.BrngVehicleOrder;

public interface NotificationGenerator {
	
	void generateNotificationForBooking(BrngOrder brngorder ,int orderId, ArrayList<String> listOfServiceMen) throws ClassNotFoundException, SQLException;

	void generateNotificationForVehicleBooking(BrngVehicleOrder brngorder ,int orderId, ArrayList<String> listOfServiceMen) throws ClassNotFoundException, SQLException;
}
