package org.bktech.university.dashboard.resources;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Cookie;

import org.bktech.university.dashboard.Globals;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bktech.university.dashboard.CacheMemory;
import org.bktech.university.dashboard.CacheMemory.TransactionByInstitution;
import org.bktech.university.dashboard.ejb.StatisticsDAO;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.ejb.AuthTokenDAO;
import org.bktech.university.dashboard.models.AuthToken;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArray;

@Path("statistics")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Statistics {
	
	@Inject
	StatisticsDAO statisticsDAO;
	
	@Inject
	AuthTokenDAO authTokenDAO;
	
	@GET
	@Path("/institution/{institutionId}")
	@Produces("application/json")
	public Response getGeneralStatsByInstitution(@Context HttpHeaders headers, @PathParam("institutionId") Long institutionId)throws UnAuthorizedExceptionMapper 
	{
		
		boolean isCookieAvailable = false;
		String cookieValue = null;
		JsonObject jsonObject = null;
		
		System.out.println("Cookies .......");

		for(String name : headers.getCookies().keySet())
		{
			if(name.trim().equals("Authorization"))
			{
				isCookieAvailable = true;
				Cookie cookie = headers.getCookies().get(name);
                cookieValue = cookie.getValue();
			}
			
			//Cookie cookie = headers.getCookies().get(name);
		   //System.out.println("Cookie name: "+name);
		   //System.out.println("Cookie value: "+cookie.getValue());
		}
		
		if(isCookieAvailable)
		{
			String cookieTimestamp = Globals.getTimeFromCookie(cookieValue);
			String formattedCookieTimestamp = Globals.getFormattedTimestamp(cookieTimestamp);
			System.out.println("retrieved cookie: "+cookieTimestamp);

			System.out.println("formatted time: "+formattedCookieTimestamp);

			Timestamp timeStamp = Globals.getSQLDate(formattedCookieTimestamp);
			AuthToken authToken = authTokenDAO.getTokenObject(cookieValue);
			if(authToken!=null)
			{
				Timestamp tokenExpiryDate = authToken.getExpiryTime();
				Date date = new Date();
				DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
				String ctimeStamp = cdf.format(date);
			    Timestamp currentTime = Globals.getSQLDate(ctimeStamp);
				if(tokenExpiryDate.after(currentTime))
				{
					Long students = statisticsDAO.getNumberOfStudentsByInstitution(institutionId);
					Long transactions = statisticsDAO.getNumberOfTransactionsByInstitution(institutionId);
					Double paidAmount = statisticsDAO.getTotalPaidAmountByInstitution(institutionId);
					Double mobileMoney = statisticsDAO.getTotalPaidAmountByChannelAndInstitution("MTN mobile money channel" ,institutionId);
					Double bank = statisticsDAO.getTotalPaidAmountByChannelAndInstitution("O2C" ,institutionId);

				            jsonObject = Json.createObjectBuilder()
							.add("students", students)
							.add("transactions",transactions)
							.add("paidAmount",paidAmount)
                            .add("mobileMoney",mobileMoney)
                            .add("bank",bank)

							.build();
					

				}
				else
				{
					throw new UnAuthorizedExceptionMapper("Invalid cookie");

				}
			}
			else
			{
				throw new UnAuthorizedExceptionMapper("Invalid cookie");

			}
			
			
		
		}
		else
		{
			

			throw new UnAuthorizedExceptionMapper("No valid session found");

		
		}
		
		
		/**
		JsonObject jsonObject = null;
		
		if(cookie.trim().equalsIgnoreCase("manzi"))
		{
			  jsonObject = Json.createObjectBuilder()
				.add("message", "cookie received")
				.build();
			
		}
		else
		{

			  jsonObject = Json.createObjectBuilder()
				.add("message", cookie)
				.build();
			
		
		}
		**/

		return Response.status(Status.OK)
			       .entity(jsonObject.toString())
			       .build();
	}
	
	@GET
	@Path("/powerbi")
	@Produces("application/json")
	public Response getPowerBIStats(@Context HttpHeaders headers) throws UnAuthorizedExceptionMapper
	{
		JsonArray jsonArray = null;
		
		String authKey = headers.getHeaderString("Authorization");

		if(authKey == null)
		{


			throw new UnAuthorizedExceptionMapper("Authorization key not found");
		
        
		}
		else if(!authKey.trim().equalsIgnoreCase("UNIVDWZX23ku!"))
		{


			throw new UnAuthorizedExceptionMapper("Invalid authorization key");
		
        
		}
		else
		{

		    List<Object[]> numberOfStudents = statisticsDAO.getNumberOfStudents();
			java.util.Map<String, Long> studentsPerInst = new java.util.HashMap<String,Long>(numberOfStudents.size());
			for(Object [] o :numberOfStudents)
			{
				studentsPerInst.put((String)o[0], (Long)o[1]);
			}
			
			List<Object[]> numberOfTransactions = statisticsDAO.getNumberOfTransactions();
			java.util.Map<String, Long> transactionsPerInst = new java.util.HashMap<String,Long>(numberOfTransactions.size());
			for(Object [] o :numberOfTransactions)
			{
				transactionsPerInst.put((String)o[0], (Long)o[1]);
			}
			
			List<Object[]> totalPaidAmount = statisticsDAO.getTotalPaidAmount();
			java.util.Map<String, Double> totalPaidAmountPerInst = new java.util.HashMap<String,Double>(totalPaidAmount.size());
			for(Object [] o :totalPaidAmount)
			{
				totalPaidAmountPerInst.put((String)o[0], (Double)o[1]);
			}
			
			List<Object[]> totalPaidAmountFromMTN = statisticsDAO.getTotalPaidAmountByChannel("MTN mobile money channel");
			java.util.Map<String, Double> totalPaidAmountFromMTNPerInst = new java.util.HashMap<String,Double>(totalPaidAmountFromMTN.size());
			for(Object [] o :totalPaidAmountFromMTN)
			{
				totalPaidAmountFromMTNPerInst.put((String)o[0], (Double)o[1]);
			}
			
			List<Object[]> totalPaidAmountFromBank = statisticsDAO.getTotalPaidAmountByChannel("O2C");
			java.util.Map<String, Double> totalPaidAmountFromBankPerInst = new java.util.HashMap<String,Double>(totalPaidAmountFromBank.size());
			for(Object [] o :totalPaidAmountFromBank)
			{
				totalPaidAmountFromBankPerInst.put((String)o[0], (Double)o[1]);
			}
			
			
			JsonArrayBuilder universityStats = Json.createArrayBuilder();

			
			for(java.util.Map.Entry<String, Long> entry : studentsPerInst.entrySet())
			{
				JsonObjectBuilder jsonObject = Json.createObjectBuilder();
				jsonObject.add("university", entry.getKey());	
				jsonObject.add("students", entry.getValue());	
				jsonObject.add("transactions", transactionsPerInst.get(entry.getKey()));
				jsonObject.add("amount", totalPaidAmountPerInst.get(entry.getKey()));
				jsonObject.add("mobilemoney", totalPaidAmountFromMTNPerInst.get(entry.getKey()));
				jsonObject.add("bank", totalPaidAmountFromBankPerInst.get(entry.getKey()));

	            universityStats.add(jsonObject);
				
			 }
			
			 jsonArray = universityStats.build();

			
			
		
		}
		
		return Response.status(Status.OK)
			       .entity(jsonArray.toString())
			       .build();
		
	}
	
	@GET
	@Path("/paymentservice/{institutionId}")
	@Produces("application/json")
	public Response getGeneralStatsPerServicePerInstitution(@Context HttpHeaders headers, @PathParam("institutionId") Long institutionId)throws UnAuthorizedExceptionMapper
	{
		
      
		boolean isCookieAvailable = false;
		String cookieValue = null;
		JsonArray jsonArray = null;
	    
		for(String name : headers.getCookies().keySet())
		{
			if(name.trim().equals("Authorization"))
			{
				isCookieAvailable = true;
				Cookie cookie = headers.getCookies().get(name);
                cookieValue = cookie.getValue();
			}
		}
		
		if(isCookieAvailable)
		{
			String cookieTimestamp = Globals.getTimeFromCookie(cookieValue);
			String formattedCookieTimestamp = Globals.getFormattedTimestamp(cookieTimestamp);
			System.out.println("retrieved cookie: "+cookieTimestamp);

			System.out.println("formatted time: "+formattedCookieTimestamp);

			Timestamp timeStamp = Globals.getSQLDate(formattedCookieTimestamp);
			AuthToken authToken = authTokenDAO.getTokenObject(cookieValue);
			if(authToken!=null)
			{
				Timestamp tokenExpiryDate = authToken.getExpiryTime();
				Date date = new Date();
				DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
				String ctimeStamp = cdf.format(date);
			    Timestamp currentTime = Globals.getSQLDate(ctimeStamp);
				if(tokenExpiryDate.after(currentTime))
				{
					List<Object[]> totalPaidAmount = statisticsDAO.getTotalPaidAmountByService(institutionId);
					java.util.Map<String, Double> totalPaidAmountPerService = new java.util.HashMap<String,Double>(totalPaidAmount.size());
					for(Object [] o :totalPaidAmount)
					{
						if(totalPaidAmountPerService.keySet().isEmpty())
						{
							totalPaidAmountPerService.put((String)o[0], (Double)o[1]);

						}
						else
						{

							for(String pytPurpose :totalPaidAmountPerService.keySet())

							{
								if(((String)o[0]).equals(pytPurpose))
								{
									Double paidAmount = totalPaidAmountPerService.get((String)o[0]);
									totalPaidAmountPerService.remove((String)o[0]);
									totalPaidAmountPerService.put((String)o[0], (Double)o[1]+paidAmount);
                                    break;
								}
								else
								{
									totalPaidAmountPerService.put((String)o[0], (Double)o[1]);
									break;

								}
							}
							
						
						}
						
					}
					
					List<Object[]> totalPaidAmountPerSubServ = statisticsDAO.getTotalPaidAmountBySubService(institutionId);
					java.util.Map<String, Double> totalPaidAmountPerSubService = new java.util.HashMap<String,Double>(totalPaidAmount.size());
					for(Object [] o :totalPaidAmountPerSubServ)
					{
						if(totalPaidAmountPerSubService.keySet().isEmpty())
						{
							totalPaidAmountPerSubService.put((String)o[0], (Double)o[1]);

						}
						else
						{

							
							for(String pytPurpose:totalPaidAmountPerSubService.keySet())
							{
								if(((String)o[0]).equals(pytPurpose))
								{
									Double paidAmount = totalPaidAmountPerSubService.get((String)o[0]);
									totalPaidAmountPerSubService.remove((String)o[0]);
									totalPaidAmountPerSubService.put((String)o[0], (Double)o[1]+paidAmount);
                                    break;
								}
								
								else
								{
									totalPaidAmountPerSubService.put((String)o[0], (Double)o[1]);
                                    break;
								}
								
								
							}

							
						
						}
						
						
					}
					
					JsonArrayBuilder universityStats = Json.createArrayBuilder();

					for(java.util.Map.Entry<String, Double> entry : totalPaidAmountPerService.entrySet())
					{
						JsonObjectBuilder jsonObject = Json.createObjectBuilder();
						jsonObject.add("service", entry.getKey());	
						jsonObject.add("amount", entry.getValue());	
						

			            universityStats.add(jsonObject);
						
					 }
					
					for(java.util.Map.Entry<String, Double> entry : totalPaidAmountPerSubService.entrySet())
					{
						JsonObjectBuilder jsonObject = Json.createObjectBuilder();
						jsonObject.add("service", entry.getKey());	
						jsonObject.add("amount", entry.getValue());	
						

			            universityStats.add(jsonObject);
						
					 }
					
					 jsonArray = universityStats.build();
					
					
					
				}
				else
				{
					throw new UnAuthorizedExceptionMapper("Invalid cookie");

				}
			}
			else
			{
				throw new UnAuthorizedExceptionMapper("Invalid cookie");

			}
			
		}
		else
		{
			throw new UnAuthorizedExceptionMapper("No valid session found");

		}
		
	
		
		
		return Response.status(Status.OK)
			       .entity(jsonArray.toString())
			       .build();
		
	}
	
	@GET
	@Path("/paymentstatsbymonth/{institutionId}/{year}")
	@Produces("application/json")
	public Response getPaymentStatsPerMonthPerInstitution(@Context HttpHeaders headers, @PathParam("institutionId") Long institutionId,  @PathParam("year") String year)throws UnAuthorizedExceptionMapper
	{
		boolean isCookieAvailable = false;
		String cookieValue = null;
		JsonArray jsonArray = null;

        for(String name : headers.getCookies().keySet())
		{
			if(name.trim().equals("Authorization"))
			{
				isCookieAvailable = true;
				Cookie cookie = headers.getCookies().get(name);
                cookieValue = cookie.getValue();
			}
			
		}
		
		if(isCookieAvailable)
		{
			String cookieTimestamp = Globals.getTimeFromCookie(cookieValue);
			String formattedCookieTimestamp = Globals.getFormattedTimestamp(cookieTimestamp);
			System.out.println("retrieved cookie: "+cookieTimestamp);

			System.out.println("formatted time: "+formattedCookieTimestamp);

			Timestamp timeStamp = Globals.getSQLDate(formattedCookieTimestamp);
			AuthToken authToken = authTokenDAO.getTokenObject(cookieValue);
			if(authToken!=null)
			{
				Timestamp tokenExpiryDate = authToken.getExpiryTime();
				Date date = new Date();
				DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
				String ctimeStamp = cdf.format(date);
			    Timestamp currentTime = Globals.getSQLDate(ctimeStamp);
				if(tokenExpiryDate.after(currentTime))
				{
					// put your logic here
			    	Map<String, TransactionByInstitution> transactionsPerInst = CacheMemory.transactionsPerInst;

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
				    if(Integer.parseInt(year) == currentYear)
				    {

				    	if(!transactionsPerInst.isEmpty())
				    	{
							JsonArrayBuilder universityStats = Json.createArrayBuilder();

				    		for(int i=1 ;i<=currentMonth; i++)
							{
				    			JsonObjectBuilder jsonObject = Json.createObjectBuilder();

					    		if(i<10)
					    		{
					    			if(i!=currentMonth)
					    			{
					    				String searchTerm = year+"-"+"0"+String.valueOf(i)+"-"+String.valueOf(institutionId);
						    			CacheMemory.TransactionByInstitution trx = transactionsPerInst.get(searchTerm);
						    			Long transactions = Long.valueOf(0);
						    			if(trx!= null)
						    			{
						    				transactions = trx.getNumberOfTransactions();
						    			}
										jsonObject.add(Globals.months.get(i),transactions);
							    		universityStats.add(jsonObject);
					    			}
					    			else
					    			{
					    				String queryTerm = year+"-"+"0"+String.valueOf(i);
					    				Long numberOfTransactions = statisticsDAO.getNumberOfTransactionsByMonthByInstitution(queryTerm,institutionId);
					    				if(numberOfTransactions!=0L)
					    				{
											jsonObject.add(Globals.months.get(i), numberOfTransactions);
								    		universityStats.add(jsonObject);


					    				}
					    				else
					    				{
											jsonObject.add(Globals.months.get(i), 0);
								    		universityStats.add(jsonObject);


					    				}
					    				
					    			}
					    			

					    		}
					    		
					    		else
					    		{

					    			if(i!=currentMonth)
					    			{
					    				String searchTerm = year+"-"+String.valueOf(i)+"-"+String.valueOf(institutionId);
						    			CacheMemory.TransactionByInstitution trx = transactionsPerInst.get(searchTerm);
						    			Long transactions = Long.valueOf(0);
						    			if(trx!= null)
						    			{
						    				transactions = trx.getNumberOfTransactions();
						    			}
										jsonObject.add(Globals.months.get(i), transactions);
							    		universityStats.add(jsonObject);
					    				
					    			}
					    			else
					    			{
					    				String queryTerm = year+"-"+String.valueOf(i);
					    				Long numberOfTransactions = statisticsDAO.getNumberOfTransactionsByMonthByInstitution(queryTerm,institutionId);
					    				if(numberOfTransactions!=0L)
					    				{
											jsonObject.add(Globals.months.get(i), numberOfTransactions);
								    		universityStats.add(jsonObject);


					    				}
					    				else
					    				{
											jsonObject.add(Globals.months.get(i), 0);
								    		universityStats.add(jsonObject);

					    				}
										
					    				
					    			}
					    			
					    			

					    		
					    		}
					    		
					    		
							}
				    		
							 jsonArray = universityStats.build();

				    	}
				    	else
				    	{
				    		// throw an error
				    	}
				    	
				    	

				    
				    }
				    else
				    {
				    	if(!transactionsPerInst.isEmpty())
				    	{
							JsonArrayBuilder universityStats = Json.createArrayBuilder();

				    		for(int i=1 ;i<=12; i++)
							{
				    			JsonObjectBuilder jsonObject = Json.createObjectBuilder();

					    		if(i<10)
					    		{
					    			String searchTerm = year+"-"+"0"+String.valueOf(i)+"-"+String.valueOf(institutionId);
					    			CacheMemory.TransactionByInstitution trx = transactionsPerInst.get(searchTerm);
					    			Long transactions = Long.valueOf(0);
					    			if(trx!= null)
					    			{
					    				transactions = trx.getNumberOfTransactions();
					    			}
									jsonObject.add(Globals.months.get(i), transactions);
						    		universityStats.add(jsonObject);

					    		}
					    		
					    		else
					    		{

					    			String searchTerm = year+"-"+String.valueOf(i)+"-"+String.valueOf(institutionId);
					    			CacheMemory.TransactionByInstitution trx = transactionsPerInst.get(searchTerm);
					    			Long transactions = Long.valueOf(0);
					    			if(trx!= null)
					    			{
					    				transactions = trx.getNumberOfTransactions();
					    			}
									jsonObject.add(Globals.months.get(i), transactions);
						    		universityStats.add(jsonObject);

					    		
					    		}
					    		
					    		
							}
				    		
							 jsonArray = universityStats.build();

				    	}
				    	else
				    	{
				    		// throw an error
				    	}
				    	
				    	

				    }
					
				}
				else
				{
					throw new UnAuthorizedExceptionMapper("Invalid cookie");

				}
			}
			else
			{
				throw new UnAuthorizedExceptionMapper("Invalid cookie");

			}
			
			
		
		}
		else
		{
			throw new UnAuthorizedExceptionMapper("No valid session found");

		}
		
	
		
		return Response.status(Status.OK)
			       .entity(jsonArray.toString())
			       .build();
	}


	
	// validate cookie sample code 
	
	public void validateCookie()
	{
		/**boolean isCookieAvailable = false;
		String cookieValue = null;
		
        for(String name : headers.getCookies().keySet())
		{
			if(name.trim().equals("Authorization"))
			{
				isCookieAvailable = true;
				Cookie cookie = headers.getCookies().get(name);
                cookieValue = cookie.getValue();
			}
			
		}
		
		if(isCookieAvailable)
		{
			String cookieTimestamp = Globals.getTimeFromCookie(cookieValue);
			String formattedCookieTimestamp = Globals.getFormattedTimestamp(cookieTimestamp);
			System.out.println("retrieved cookie: "+cookieTimestamp);

			System.out.println("formatted time: "+formattedCookieTimestamp);

			Timestamp timeStamp = Globals.getSQLDate(formattedCookieTimestamp);
			AuthToken authToken = authTokenDAO.getTokenObject(cookieValue);
			if(authToken!=null)
			{
				Timestamp tokenExpiryDate = authToken.getExpiryTime();
				Date date = new Date();
				DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
				String ctimeStamp = cdf.format(date);
			    Timestamp currentTime = Globals.getSQLDate(ctimeStamp);
				if(tokenExpiryDate.after(currentTime))
				{
					// put your logic here
					
				}
				else
				{
					throw new UnAuthorizedExceptionMapper("Invalid cookie");

				}
			}
			else
			{
				throw new UnAuthorizedExceptionMapper("Invalid cookie");

			}
			
			
		
		}
		else
		{
			throw new UnAuthorizedExceptionMapper("No valid session found");

		}
		**/
	}


}
