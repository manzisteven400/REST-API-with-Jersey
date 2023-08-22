package org.bktech.university.dashboard.resources;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.bktech.university.dashboard.Globals;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.ejb.PaymentLogDAO;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;

@Path("paymentpurpose")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RegisterPaymentPurpose {
	
	
	@Inject
	StudentAccountDAO studentAccountDAO;
	
	@Inject
	PaymentLogDAO paymentLogDAO;
	
	
	@Context HttpServletRequest request;
	@Context HttpHeaders httpHeader;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
	public Response registerPaymentPurpose() throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
	{
    	JsonObject jsonObject = null;

    	
    	String token = httpHeader.getHeaderString("Authorization");
        
    	if(token == null)
		{
			throw new UnAuthorizedExceptionMapper("Authorization token is required");
		
    	}
    	
    	else
    	{
    		if(Globals.tokens.containsKey(token))
			{
				String expiryTime = Globals.tokens.get(token);
				Date date = new Date();
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				formatter.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
			    String currentTime = formatter.format(date);
			    Timestamp tokenExpiryTime = Globals.getSQLDate(expiryTime);
			    Timestamp convertedCurrentTime = Globals.getSQLDate(currentTime);
			    if(tokenExpiryTime.after(convertedCurrentTime))
			    {
			    	String postData = null;
					StringBuffer buffer = new StringBuffer();
					String line = null;
					String bodyContent = null;
					
					try
					{
                        bodyContent = org.apache.commons.io.IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
						
						postData = bodyContent;
					}
					catch(Exception e)
			    	{

						e.printStackTrace();
						//System.out.println(e.getMessage());
			    		throw new UserRequestExceptionMapper(e.getMessage());
					
			    		
			    	}
					
					try
					{

						  String universityAccronym = null;
			              String campus = null;
			              String paymentPurpose = null;

						  
						  JsonParser parser = Json.createParser(new StringReader(postData));
						  while(parser.hasNext())
						 {
							  JsonParser.Event event = parser.next();

								switch(event)
								{
								   case KEY_NAME:
									   if(parser.getString().equalsIgnoreCase("universityAccronym"))
									   {
										  
										   event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  //System.out.println("Access token: "+parser.getString());
										    	  universityAccronym = parser.getString();
										    	  
										    	  
										   }
									   }
									   else if(parser.getString().equalsIgnoreCase("campus"))
									   {

										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  campus = parser.getString();

										    	  break;
										    	  
										   }
									   
									   }
									   else if(parser.getString().equalsIgnoreCase("paymentPurpose"))
									   {

										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  paymentPurpose = parser.getString();

										    	  break;
										    	  
										   }
									   
									   }
									   
								}
								
							 
						  }
						  
						// process the record 
						  
						  if(universityAccronym != null && campus != null && paymentPurpose != null)
						  {
							   Institution institution = studentAccountDAO.getInstitution(universityAccronym);
							   
							   if(institution != null)
							   {
								   Faculty faculty = studentAccountDAO.getFaculty(campus, institution.getId());
								   
								   if(faculty != null)
								   {
									   BankAccount bankAccount = paymentLogDAO.getBankAccountByFacultyID(faculty.getId());
									   
									   if(bankAccount != null)
									   {
										   PaymentPurpose paymentPurposeObj = new PaymentPurpose();
										   
										   paymentPurposeObj.setBankAccount(bankAccount);
										   paymentPurposeObj.setFacultyId(faculty);
										   paymentPurposeObj.setInstitutionId(institution);
										   paymentPurposeObj.setPurpose(paymentPurpose);
										   paymentPurposeObj.setDescription(paymentPurpose);
										   paymentPurposeObj.setAccPriority("1");
										   paymentPurposeObj.setHasDependent(0);
										   
										   PaymentPurpose paymentPurposeObject = paymentLogDAO.isPaymentPurposeRegistered(faculty.getId(), paymentPurpose);
										   
										   if(paymentPurposeObject == null)
										   {
											   paymentLogDAO.registerPaymentPurpose(paymentPurposeObj);
											   
											   jsonObject = Json.createObjectBuilder()
														.add("httpStatusMessage", "saved")
														.build();

										   }
										   
										   else
										   {
											   jsonObject = Json.createObjectBuilder()
														.add("httpStatusMessage", "record already exists")
														.build();
										   }
										   
                                           
										   
										   
										   
										  
							            
										   
										   
										   
										   
									   }
									   
									   else
									   {
										   jsonObject = Json.createObjectBuilder()
													.add("httpStatusMessage", "Bank account not registered")
													.build();
									   }
								   }
								   
								   else
								   {
									   jsonObject = Json.createObjectBuilder()
												.add("httpStatusMessage", "Campus not found")
												.build();
								   }
							   }
							   
							   else
							   {
								   jsonObject = Json.createObjectBuilder()
											.add("httpStatusMessage", "institution not found")
											.build();
							   }
						  }
						  
						  else
						  {
							  jsonObject = Json.createObjectBuilder()
										.add("httpStatusMessage", "one of the required fields is missing")
										.build();
						  }
						  
						  // return response
						  
						
					
					}
					catch(JsonParsingException ex)
					{
						         jsonObject = Json.createObjectBuilder()
								.add("httpStatusMessage", "error parsing posted json data, please do check your data format")
								.build();
						         
					}
					
					
					
			    }
			  
    	}
    	
		
	}
    	
    	ResponseBuilder builder = Response.status(Status.OK)
			       .entity(jsonObject.toString());
 	
 	    return builder.build();  	

}
}
