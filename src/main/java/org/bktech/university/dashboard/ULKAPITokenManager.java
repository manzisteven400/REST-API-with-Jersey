package org.bktech.university.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;

import org.bktech.university.dashboard.Globals;

public class ULKAPITokenManager  extends TimerTask{
	

	public void run()
	{
		try
		{
			Date date = new Date();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
		    String currentTime = formatter.format(date);
			
		    for(Map.Entry<String,String> entry:Globals.tokens.entrySet())
			{
				String key = entry.getKey();
				String tokenExpiryTime = Globals.tokens.get(key);
				
				if(Globals.getSQLDate(tokenExpiryTime).before(Globals.getSQLDate(currentTime)))
				{
					Globals.tokens.remove(key);
					
					//System.out.println("Token deleted "+tokenExpiryTime);
				}
				
			}
			
			
		}
		catch(Throwable e)
		{
			
		}
	}



}
