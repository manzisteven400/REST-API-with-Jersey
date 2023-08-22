package org.bktech.university.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Map;
import java.util.HashMap;

import org.bktech.university.dashboard.ejb.StatisticsDAO;

public class CacheMemory {
	
	private static int startYear = 2019;
	public static Map<String, TransactionByInstitution> transactionsPerInst = new HashMap<String,TransactionByInstitution>();

	private StatisticsDAO statisticsDAO = new StatisticsDAO();
	
	public void cacheStatistics()
	{
		Date date = new Date();
		DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd");
		cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
		String ctimeStamp = cdf.format(date);
		int currentYear = Integer.parseInt(ctimeStamp.substring(0,4));
		String strCurrentMonth = String.valueOf(ctimeStamp.substring(5,7));
		int currentMonth = 0;
		if(strCurrentMonth.startsWith("0"))
		{
			currentMonth = Integer.parseInt(String.valueOf(ctimeStamp.substring(5,7).charAt(1)));

		}
		else
		{
			currentMonth = Integer.parseInt(String.valueOf(ctimeStamp.substring(5,7)));

			
		}
		while(startYear<=currentYear)
		{
			if(startYear == currentYear)
			{
				// cache for the current year
				cacheStatsCurrentYear(currentYear, currentMonth);
				break;
			}
			else
			{
				// cache for the previous year
				cacheStatsPreviousYear(startYear);
				startYear++;
			}
		}
		
		System.out.println("Loop ends.........................................");
	}
	
	public void cacheStatsPreviousYear(int year)
	{
		for(int i=1 ;i<=12; i++)
		{
			if(i<10)
			{
				String month = String.valueOf(year)+"-"+"0"+String.valueOf(i);
				List<Object[]> numberOfTransactions = statisticsDAO.getNumberOfTransactionsForCaching(month);
				for(Object [] o :numberOfTransactions)
				{
					TransactionByInstitution trx = new TransactionByInstitution();
					trx.setInstitutionId((Long)o[0]);
					trx.setNumberOfTransactions((Long)o[1]);
					String monthForMapping = month+"-"+String.valueOf((Long)o[0]);
					transactionsPerInst.put(monthForMapping, trx);
				}
				
				// save results for previous year in the map like 2019-01, 30
				
			}
			else
			{

				String month = String.valueOf(year)+"-"+String.valueOf(i);
				List<Object[]> numberOfTransactions = statisticsDAO.getNumberOfTransactionsForCaching(month);
				for(Object [] o :numberOfTransactions)
				{
					TransactionByInstitution trx = new TransactionByInstitution();
					trx.setInstitutionId((Long)o[0]);
					trx.setNumberOfTransactions((Long)o[1]);
					String monthForMapping = month+"-"+String.valueOf((Long)o[0]);
					transactionsPerInst.put(monthForMapping, trx);
				}
				
				// save results for previous year in the map like 2019-01, 30
				
			
				
			}
		}
	}
	
	public void cacheStatsCurrentYear(int year, int currentMonth)
	{
		for(int i=1 ;i<currentMonth; i++)
		{

			if(i<10)
			{

				String month = String.valueOf(year)+"-"+"0"+String.valueOf(i);
				List<Object[]> numberOfTransactions = statisticsDAO.getNumberOfTransactionsForCaching(month);
				for(Object [] o :numberOfTransactions)
				{
					TransactionByInstitution trx = new TransactionByInstitution();
					trx.setInstitutionId((Long)o[0]);
					trx.setNumberOfTransactions((Long)o[1]);
					String monthForMapping = month+"-"+String.valueOf((Long)o[0]);
					transactionsPerInst.put(monthForMapping, trx);
				}
				
				// save results for previous year in the map like 2019-01, 30
				
			
				
			}
			else
			{


				String month = String.valueOf(year)+"-"+String.valueOf(i);
				List<Object[]> numberOfTransactions = statisticsDAO.getNumberOfTransactionsForCaching(month);
				for(Object [] o :numberOfTransactions)
				{
					TransactionByInstitution trx = new TransactionByInstitution();
					trx.setInstitutionId((Long)o[0]);
					trx.setNumberOfTransactions((Long)o[1]);
					String monthForMapping = month+"-"+String.valueOf((Long)o[0]);
					transactionsPerInst.put(monthForMapping, trx);
				}
				
				// save results for previous year in the map like 2019-01, 30
				
			
				
			
				
				
			}
		
			
		}
	}
	
	public class TransactionByInstitution 
	{

		
		private Long institutionId;
		private Long numberOfTransactions;
		public Long getInstitutionId() {
			return institutionId;
		}
		public void setInstitutionId(Long institutionId) {
			this.institutionId = institutionId;
		}
		public Long getNumberOfTransactions() {
			return numberOfTransactions;
		}
		public void setNumberOfTransactions(Long numberOfTransactions) {
			this.numberOfTransactions = numberOfTransactions;
		}
		
		


	}

}
