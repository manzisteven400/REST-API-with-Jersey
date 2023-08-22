package org.bktech.university.dashboard;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Globals {
	
	public static Map<String, String> tokens;

	
	public static Map<Integer, String> months = new HashMap<Integer, String>();

	public Globals()
	{
		
		tokens = new HashMap<String, String>();

		months.put(1,"January");
		months.put(2,"February");
		months.put(3,"March");
		months.put(4,"April");
		months.put(5,"May");
		months.put(6,"June");
		months.put(7,"July");
		months.put(8,"August");
		months.put(9,"September");
		months.put(10,"October");
		months.put(11,"November");
		months.put(12,"December");
		
	
	}
	
	public static void getValue()
	{


		System.out.println("Data is retrieved .................");
	
	
	}
	
	public static String getTimeInReverse(String timeStamp)
	{
	    	StringBuffer buffer = new StringBuffer();
	    	
	    	for(int i=timeStamp.length()-1; i>=0; i--)
	    	{
	    		
	    		buffer.append(timeStamp.charAt(i));
	    	}
	    	
	    	
	    	return buffer.toString();
	}
	
	public static String getTimeFromCookie (String cookie)
	{
    	    StringBuffer buffer = new StringBuffer();

	    	int j =0;
	    	
	    	for(int i=cookie.length()-1; i>=0; i--)
	    	{
	    		j++;
	    	    buffer.append(cookie.charAt(i));
	    		if(j==14) break;
	    	}
	    	
	    	
	    	return buffer.toString();
	}
	
	
	
	
	
	public static Calendar getCalendar(String timeStamp)
	{
		
		Calendar calendar = null;
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date dateCalendar = df.parse(timeStamp);
			calendar = Calendar.getInstance();
			calendar.setTime(dateCalendar);
			
	    }
		catch(ParseException ex)
		{
			
		}
		
		return calendar;
		
	}
	
	public static String getDate(Calendar calendar)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        String date = df.format(calendar.getTime());
        
        return date;
	
		

	}
	

	
	public static java.sql.Timestamp getSQLDate(String date){
		
		SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date2 = null;
		java.sql.Timestamp sqlDate = null;
		try
		{
			date2 = date1.parse(date);
			
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			return sqlDate;
			
		}
		
		sqlDate = new java.sql.Timestamp(date2.getTime());
		return sqlDate;
		
	}


	
	public static String getFormattedTimestamp(String timeStamp)
	{
		return timeStamp.substring(0,4)+"-"+timeStamp.substring(4,6)+"-"+timeStamp.substring(6,8)+" "+timeStamp.substring(8,10)+":"+timeStamp.substring(10,12)+":"+timeStamp.substring(12,14);
		
	}
	
	public static Calendar getFormattedCalendar(String date)
	{

		
		Calendar calendar = null;
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Date dateCalendar = df.parse(date);
			calendar = Calendar.getInstance();
			calendar.setTime(dateCalendar);
			
	    }
		catch(ParseException ex)
		{
			
		}
		
		return calendar;
		
	
	}

}
