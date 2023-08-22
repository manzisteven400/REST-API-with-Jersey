package org.bktech.university.dashboard.resources;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import java.sql.Timestamp;
import java.io.BufferedReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.ejb.AuthTokenDAO;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountInterface;
import org.bktech.university.dashboard.Globals;

import javax.ws.rs.Consumes;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.servlet.http.HttpServletRequest;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.json.JSONObject;
import org.json.HTTP;
import org.json.JSONException;
import org.bktech.university.dashboard.models.UserAccount;
import org.bktech.university.dashboard.models.AuthToken;

import javax.faces.bean.RequestScoped;
import javax.annotation.ManagedBean;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.Calendar;

import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/login")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Login {
	
	@Inject
	UserAccountDAO userAccountDAO;
	
	@Inject
	AuthTokenDAO authTokenDAO;
	
	
	@Context HttpServletRequest request;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
	public Response validateUserCredentials()
	{
    	UserAccount userAccount = null;
    	Globals.getValue();
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

		    jsonObject = Json.createObjectBuilder()
					.add("message", "Error parsing posted data")
					.build();
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
					}
					
				 
			  }
			  
			  // call EJB
			 userAccount = userAccountDAO.validateUserAccount(username, password);
			 
			 if(userAccount != null)
			 {
				 JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
			     jsonObjectBuilder.add("message", "User authenticated successfully");
				 JsonObjectBuilder ObjectBuilder = Json.createObjectBuilder();
				 ObjectBuilder.add("userName", userAccount.getEmail());
				 ObjectBuilder.add("institutionId", userAccount.getInstitutionId().getId());
				 ObjectBuilder.add("institutionName", userAccount.getInstitutionId().getName());
				 ObjectBuilder.add("institutionLogo",  userAccount.getInstitutionId().getInstitutionLogo());
				 ObjectBuilder.add("institutionAccronym",  userAccount.getInstitutionId().getAccronym());

				 ObjectBuilder.add("userProfileName", userAccount.getProfile().getName());
				 ObjectBuilder.add("userProfileNumber", userAccount.getProfile().getUserProfileNumber());



				 jsonObjectBuilder.add("data", ObjectBuilder);

			     jsonObject = jsonObjectBuilder.build();

			     
			     
			     
			 }
			 else
			 {
				    jsonObject = Json.createObjectBuilder()
							.add("message", "login failed")
							.build();
				 
			  }
            
		}
		catch(JsonParsingException ex)
		{
			         jsonObject = Json.createObjectBuilder()
					.add("message", "error parsing posted json data, please do check your data format")
					.build();
			         
		}
		
		ResponseBuilder builder = null;
		
		if(userAccount!=null)
	    {

			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			df.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
			String timeStamp = df.format(date);
			// calendar timestamp
			DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
			String ctimeStamp = cdf.format(date);
			Calendar calendar = Globals.getCalendar(ctimeStamp);
			calendar.add(Calendar.MINUTE,2);
			String dateCalendar = Globals.getDate(calendar);
			System.out.println("date calendar: "+dateCalendar);
			Timestamp expiryTime = Globals.getSQLDate(dateCalendar);
			AuthToken authToken = new AuthToken();
			authToken.setUserAccount(userAccount);
			authToken.setAuthToken(userAccount.getEmail()+Globals.getTimeInReverse(timeStamp));
			authToken.setExpiryTime(expiryTime);
			authTokenDAO.saveToken(authToken);
			NewCookie cookie =  new NewCookie("Authorization", userAccount.getEmail()+Globals.getTimeInReverse(timeStamp),"/", "localhost", 1, null, 120, false);
		    builder = Response.status(Status.OK)
				       .entity(jsonObject.toString())
				       .cookie(cookie);
		    
	    }
		
		else
		{
			builder = Response.status(Status.OK)
				       .entity(jsonObject.toString());
				       
		}
	    
	
	          
		return builder.build();       
	}
    
    
   
    @Context HttpServletRequest clientRequest;
    @Path("/ulk")
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
            char [] body = new char[clientRequest.getContentLength()];
			
			java.io.InputStreamReader input = new java.io.InputStreamReader(clientRequest.getInputStream());
			
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
			  
			  
			 String bkUsername = System.getProperty("BKUsername");
			 String bkPassword = System.getProperty("BKPassword");
			 if(bkUsername!=null&&bkPassword!=null)
			 {
				 if(bkUsername.trim().equals(username) && bkPassword.trim().equals(password))
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
			 
			 else
			 {


					throw new UserRequestExceptionMapper("System cannot access stored username and password");
				
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
