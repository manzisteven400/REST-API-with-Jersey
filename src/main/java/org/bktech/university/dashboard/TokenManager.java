package org.bktech.university.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;
import java.sql.Timestamp;




import org.bktech.university.dashboard.ejb.AuthTokenDAO;


public class TokenManager extends TimerTask{
	
	
	
	
	public void run()
	{
		
		// delete by previous day , delete expired tokens on the previous day
		
		AuthTokenDAO authTokenDAO = new AuthTokenDAO();

		Date date = new Date();
		DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd");
		cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
		String ctimeStamp = cdf.format(date);
		Calendar calendar = Globals.getFormattedCalendar(ctimeStamp);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		String dateCalendar = Globals.getDate(calendar);
		System.out.println("date calendar .............. "+dateCalendar);
		String [] splittedCalendar = dateCalendar.split("\\s+");				


		
		try{
			

			int result = authTokenDAO.deleteToken(splittedCalendar[0]);
			if(result>0)
			{
				System.out.println("token deleted ..............");

			}
			else
			{
				System.out.println("token not deleted ..............");

			}
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			
			System.out.println("delete throws an exception within the cron ..............");

		}
	}
	
	
	
}
