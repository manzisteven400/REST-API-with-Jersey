package org.bktech.university.dashboard.resources;

import java.text.SimpleDateFormat;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;

import org.bktech.university.dashboard.ejb.PaymentLogDAO;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.providers.NullPointerExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.PaymentLog;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.SubPaymentPurpose;





import java.util.List;


@Path("/data")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Reconciliation {
	
	@Inject
	UserAccountDAO userAccountDAO;
	
	@Inject
	StudentAccountDAO studentAccountDAO;
	
	@Inject
	PaymentLogDAO paymentLogDAO;



	

	@Context HttpHeaders httpHeaders;
	@Path("/{date}")
	@GET
	@Produces("application/json")
	
	public Response getReconciliationDataByDate(@PathParam("date") String date) throws UserRequestExceptionMapper
	{

		String username = httpHeaders.getHeaderString("username");
		
		String password = httpHeaders.getHeaderString("password");
		
	    String universityCode =  httpHeaders.getHeaderString("universityCode");
		
		if(username == null)
		{
			throw new UserRequestExceptionMapper("User name cannot be empty");
		}
		else if (password == null)
		{
		  
		   throw new UserRequestExceptionMapper("Password cannot be empty");
		    
	    }
		
		else if(universityCode == null)
		{
		   throw new UserRequestExceptionMapper("University code cannot be empty");
		}
		
		else
		{
			java.sql.Date date1 = getDate(date);
			
			
			if(date1 == null)
			{
				throw new UserRequestExceptionMapper("Invalid start date format");
			}
			
			ThirdPartyAuthentication thirdParty = userAccountDAO.validateThirdPartyCredentials(username,password);
			if(thirdParty != null)
			{
				Institution institution = studentAccountDAO.getInstitution(universityCode);
				if(institution != null)
				{
					List<PaymentLog> paymentLogs = paymentLogDAO.getReconciliationDataByDate(institution.getId(), date);
					
					JsonArrayBuilder pytLogs = Json.createArrayBuilder();

					
					for(PaymentLog paymentLog : paymentLogs)
					{
						if(!paymentLog.getPaymentStatus().equalsIgnoreCase("posted") || !paymentLog.getPaymentStatus().equalsIgnoreCase("cancelled") )
						continue;
						JsonObjectBuilder jsonObject = Json.createObjectBuilder();
						jsonObject.add("regNumber", paymentLog.getRegistrationNumber());	
						jsonObject.add("bankAccount", paymentLog.getLoggedBankAccount());
						jsonObject.add("bankSlip", paymentLog.getBankSlip());
						jsonObject.add("amount", paymentLog.getAmountPaid());
						PaymentPurpose paymentPurpose = paymentLogDAO.getPaymentPurposeByID(paymentLog.getPaymentPurpose().getId());
                        if(paymentPurpose != null)
                        {
                        	if(paymentPurpose.getHasDependent() == 0)
                        	{
        						jsonObject.add("paymentDescription", paymentPurpose.getDescription());

                        	}
                        	else
                        	{
                        		SubPaymentPurpose subPaymentPurpose = paymentLogDAO.getSubPaymentPurposeByID(paymentLog.getSubPaymentPurpose().getId());
                        		if(subPaymentPurpose != null)
                        		{

            						jsonObject.add("paymentDescription", subPaymentPurpose.getPurpose());

                            	
                        		}
                        		else
                        		{

            						jsonObject.add("paymentDescription", paymentPurpose.getDescription());

                            	
                        		}
                        	}
                        }

    					java.util.Date dateObj = new java.util.Date();
    					dateObj.setTime(paymentLog.getPostingDate().getTime());
    					String formattedPaymentDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    	                
						jsonObject.add("paymentDate", formattedPaymentDate);
						
			            pytLogs.add(jsonObject);



						
					}
					
					//JsonArray jsonArray = null;
					JsonArray jsonArray = pytLogs.build();
					

					return Response.status(Status.OK)
						       .entity(jsonArray.toString())
						       .build();
					

					
				}
				
				else
				{

					throw new UserRequestExceptionMapper("Unrecognized university code");
				
				}
			}
			else
			{

				throw new UserRequestExceptionMapper("Invalid credentials");
			
			}
			
			
			// user super to call controller function
			// whatever super function returns that's what gets sent to the client.
			
		
			
			
		}
	    
	}
	
	public java.sql.Date getDate(String date)
	{


		SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date2 = null;
		java.sql.Date sqlDate = null;
		try
		{
			date2 = date1.parse(date);
		}
		catch(java.text.ParseException e)
		{
			e.printStackTrace();
			return sqlDate;
		}
		sqlDate = new java.sql.Date(date2.getTime());
		return sqlDate;
		
	
	
	}


}
