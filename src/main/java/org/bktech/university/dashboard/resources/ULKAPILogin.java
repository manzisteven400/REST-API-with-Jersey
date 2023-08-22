package org.bktech.university.dashboard.resources;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.bktech.university.dashboard.Globals;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;



@Path("/ulkapilogin")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ULKAPILogin {
	
	
	@Inject
	UserAccountDAO userAccountDAO;
	
	
	@Context HttpServletRequest request;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
	public Response validateCredentials() throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
	{
    	JsonObject jsonObject = null;
    	String postData = null;
		StringBuffer buffer = new StringBuffer();
		String line = null;
    	
    	try
    	{
            char [] body = new char[request.getContentLength()];
			
			java.io.InputStreamReader input = new java.io.InputStreamReader(request.getInputStream());
			
			org.apache.commons.io.IOUtils.readFully(input, body);
			 
			String bodyContent = new String(body);
			
			postData = bodyContent;
			
			//BufferedReader reader = request.getReader();
			BufferedReader reader = new BufferedReader(input);

			while((line = reader.readLine()) != null)
			{
				buffer.append(line);
				buffer.append("/n");
			}
		
    	}
    	catch(Exception e)
    	{

			throw new UserRequestExceptionMapper("Error parsing posted data");
		
    		
    	}
    	try
    	{

			String username = null;
            String password = null;
			  
			  JsonParser parser = Json.createParser(new StringReader(postData));
			  while(parser.hasNext())
			 {
				  JsonParser.Event event = parser.next();

					switch(event)
					{
					   case KEY_NAME:
						   if(parser.getString().equalsIgnoreCase("username"))
						   {
							  
							   event = parser.next();
							   
							   switch(event)
							   {
							      case VALUE_STRING:
							    	  //System.out.println("Access token: "+parser.getString());
							    	  username = parser.getString();
							    	  
							    	  
							   }
						   }
						   else if(parser.getString().equalsIgnoreCase("password"))
						   {

							  event = parser.next();
							   
							   switch(event)
							   {
							      case VALUE_STRING:
							    	  password = parser.getString();

							    	  break;
							    	  
							   }
						   
						   }
						   else
						   {
                               throw new UserRequestExceptionMapper(parser.getString()+" is not a valid json field");

						   }
					}
					
				 
			  }
			  
			  
			 
			 
			    ThirdPartyAuthentication thirdPartyAuthentication = userAccountDAO.validateThirdPartyCredentials(username,password);
			 
			
				 if(thirdPartyAuthentication != null)
				 {
					 
					 String authToken = UUID.randomUUID().toString();
					 Date date = new Date();
					 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 formatter.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
				     String tokenExpiryTime = formatter.format(date);
				     Calendar calendar = Globals.getCalendar(tokenExpiryTime);
					 calendar.add(Calendar.MINUTE,30);
					 String dateCalendar = Globals.getDate(calendar);
				     Globals.tokens.put(authToken,dateCalendar);
				     
				     
					 jsonObject = Json.createObjectBuilder()
								.add("httpStatusMessage", "Authenticated successfully")
								.add("authtoken", authToken)
								.build();
				 }
				 else
				 {
					 
	               throw new UnAuthorizedExceptionMapper("Authentication failed");
					
				 }
			 
			 
			

			 
          
		
    	}
    	catch(JsonParsingException ex)
		{

			throw new UserRequestExceptionMapper("Error parsing posted data please do check your data format");
		
    		
		}
    	
    	ResponseBuilder builder = Response.status(Status.OK)
			       .entity(jsonObject.toString());
    	
    	return builder.build();
			       
    
	}



}
