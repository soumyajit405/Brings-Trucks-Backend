package com.gvn.brings.util;

import java.util.Calendar;
import java.util.Date;

public class GenerateRefCode {

	
	public String generateRefCode(String email)
	{
		RandomTimeGenerator rd=new RandomTimeGenerator();
		String suffixCode=rd.randomAlphaNumeric(4);
		StringBuffer refCode=new StringBuffer("");
		refCode.append(email.substring(0,4));
		refCode.append(suffixCode.substring(0, 4));
		return refCode.toString();
	}
	
	public static void main(String args[])
	
	{
		Date date =new Date("2011/08/08 18:08:08");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);

	}
}
