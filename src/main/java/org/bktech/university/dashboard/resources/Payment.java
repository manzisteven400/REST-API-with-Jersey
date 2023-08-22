package org.bktech.university.dashboard.resources;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.bktech.university.dashboard.Globals;
import org.bktech.university.dashboard.ejb.PaymentLogDAO;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.InstitutionCalender;
import org.bktech.university.dashboard.models.PaymentLog;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.SubPaymentPurpose;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;

@Path("/payment")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Payment {
	
	
	 @Inject
     UserAccountDAO userAccountDAO;
	 
     @Inject
	 StudentAccountDAO studentAccountDAO;
		
     @Inject
	 PaymentLogDAO paymentLogDAO;
	 
	 
	
	

	@Context HttpServletRequest request;
	@Context HttpHeaders httpHeader;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
    @SuppressWarnings("unused")

	public Response registerPayment() throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
	{
    	
    	String username = httpHeader.getHeaderString("username");
    	String password = httpHeader.getHeaderString("password");
    	

        JsonObject jsonObject = null;
    	
    	if(username == null)
		{
			throw new UnAuthorizedExceptionMapper("username is required");
		
    	}
    	else if (password == null)
    	{

			throw new UnAuthorizedExceptionMapper("password is required");
		
    	}
    	else
    	{
		    ThirdPartyAuthentication thirdPartyAuthentication = userAccountDAO.validateThirdPartyCredentials(username,password);
			if(thirdPartyAuthentication != null)
			{
				String bodyContent = null;

				try
				{
				
				bodyContent = org.apache.commons.io.IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
				}
				catch(Exception e)
				{


					throw new UserRequestExceptionMapper("Error parsing posted data");
				
		    		
		    	
				}
				
				
				try
				{

					String regNumber = null;
		            String accountNumber = null;
		            String paymentPurposeId = null;
		            String phoneNumber = null;
		            String processingNumber= null;
		            double amountPaid = 0.0;
		            String subPurposeId = null;
					  
					JsonParser parser = Json.createParser(new StringReader(bodyContent));
					
					while(parser.hasNext())
					{

						  JsonParser.Event event = parser.next();

							switch(event)
							{
							   case KEY_NAME:
								   if(parser.getString().equalsIgnoreCase("regNumber"))
								   {
									  
									   event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  //System.out.println("Access token: "+parser.getString());
									    	  if(!parser.getString().isEmpty())
									    	  regNumber = parser.getString();
									    	  
									    	  break;
									   }
								   }
								   else if(parser.getString().equalsIgnoreCase("accountNumber"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  if(!parser.getString().isEmpty())

									    	  accountNumber = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("paymentPurposeId"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  if(!parser.getString().isEmpty())

									    	  paymentPurposeId = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("phoneNumber"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  if(!parser.getString().isEmpty())

									    	  phoneNumber = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("processingNumber"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  if(!parser.getString().isEmpty())

									    	  processingNumber = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("amountPaid"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_NUMBER:
                                              
									    	  amountPaid = parser.getLong();

									    	  
									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("subPurposeId"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:

									    	  subPurposeId = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   else
								   {
		                               throw new UserRequestExceptionMapper(parser.getString()+" is not a valid json field");

								   }
							}
							
						 
					  
						
					}
					
					if(regNumber != null && accountNumber != null && paymentPurposeId != null && phoneNumber != null && processingNumber != null && subPurposeId != null && amountPaid != 0.0)
					{
                        PaymentLog paymentLog = new PaymentLog();
						Student student =	studentAccountDAO.getStudent(regNumber);
						if(student == null)
						{

                            throw new UserRequestExceptionMapper("reg number not found");

						}
						Student studentObj = new Student();
						studentObj.setId(student.getId());
						paymentLog.setStudent(studentObj);
						Institution institution = new Institution();
						institution.setId(student.getInstitutionId().getId());
						paymentLog.setInstitutionId(institution);
                        
						BankAccount bankAccount = paymentLogDAO.getBankAccount(accountNumber);
						if(bankAccount == null)
						{


                            throw new UserRequestExceptionMapper("account number not found");

						}
						paymentLog.setBankId(bankAccount.getBank());
						BankAccount bankAccountObj = new BankAccount();
						bankAccountObj.setId(bankAccount.getId());
						paymentLog.setBankAccount(bankAccountObj);
						PaymentPurpose pytPurpose = paymentLogDAO.getPaymentPurposeByID(Long.parseLong(paymentPurposeId));
						if(pytPurpose == null)
						{


                           throw new UserRequestExceptionMapper(" invalid payment purpose id");

						
						}
						
						if(pytPurpose != null)
						{
							if(pytPurpose.getInstitutionId().getId()!= student.getInstitutionId().getId())
							{

								



		                           throw new UserRequestExceptionMapper(" The institution mapped to payment purpose does not match the student institution ");

								
								
								
							
								
							}
							
							
							if(pytPurpose.getBankAccount().getId() != bankAccount.getId())
							{


								



		                           throw new UserRequestExceptionMapper(" The bank account mapped to payment purpose does not match posted account number ");

								
								
								
							
								
							
							}
							
							if(pytPurpose.getHasDependent() == 1)
							{
								
								if(subPurposeId.isEmpty())
								{

									

									



			                           throw new UserRequestExceptionMapper(" subpayment purpose id is missing");

									
									
									
								
									
								
								}
								
							}
							
						}
						PaymentPurpose paymentPurpose = new PaymentPurpose();
						paymentPurpose.setId(Long.parseLong(paymentPurposeId));
						paymentLog.setPaymentPurpose(paymentPurpose);
						InstitutionCalender institutionCalender = new InstitutionCalender();
						InstitutionCalender institutionCalenderObj = studentAccountDAO.getInstitutionCalenderByInstitutionId(student.getInstitutionId().getId());
						institutionCalender.setId(institutionCalenderObj.getId());
						paymentLog.setInstitutionCalenderId(institutionCalenderObj.getId());
						paymentLog.setPayerName(phoneNumber);
						paymentLog.setMsisdn(phoneNumber);
						paymentLog.setIsRegistered("yes");
						paymentLog.setPaymentChannel("MTN mobile money channel");
						paymentLog.setOperator("MTN");
						paymentLog.setPaymentStatus("PENDING");
						paymentLog.setUssdStatus("1000");
						paymentLog.setPaymentDevice(phoneNumber);
						if(processingNumber.indexOf("UNIVSFEES") == -1)
						{
							

							



	                           throw new UserRequestExceptionMapper(" invalid subpayment purpose id");

							
							
							
						
							
						}
						paymentLog.setProcessingNumber(processingNumber);
						
						paymentLog.setLogged(0);
						paymentLog.setAmountPaid(amountPaid);
						Date date = new Date();
						DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						formatter.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
					    String timeStamp = formatter.format(date);
                        java.sql.Timestamp currentTime = Globals.getSQLDate(timeStamp);
                        paymentLog.setPaymentDate(currentTime);
                        paymentLog.setPostingDate(currentTime);
                        

						if(!subPurposeId.isEmpty())
						{
							
	                    	SubPaymentPurpose subPaymentPurpose = paymentLogDAO.getSubPaymentPurposeByID(Long.parseLong(subPurposeId));

					     	
	                    	if(subPaymentPurpose == null)
							{
								



		                           throw new UserRequestExceptionMapper(" invalid subpayment purpose id");

								
								
								
							}
	                    	

						     if(subPaymentPurpose.getPaymentPurposeId() != Long.parseLong(paymentPurposeId))
							 {

									



			                           throw new UserRequestExceptionMapper(" subpayment purpose id does not match payment purpose id");

									
									
									
								
									
							 }
						     
						     SubPaymentPurpose subPaymentPurposeObject = new SubPaymentPurpose();
						     subPaymentPurposeObject.setId(Long.parseLong(subPurposeId));
						     
						     
						     paymentLog.setSubPaymentPurpose(subPaymentPurposeObject);							
						}
               
						
						PaymentLog pytLog = paymentLogDAO.getPaymentLogByTelcosProcessingNumber(processingNumber);
						
						if(pytLog == null)
						{
						   paymentLogDAO.registerPayment(paymentLog);
						}
						else
						{
							


							



	                           throw new UserRequestExceptionMapper("the transaction processing number already exists");

							
							
							
						
							
					 
							
						}

						
				         jsonObject = Json.createObjectBuilder()
									.add("httpStatusCode", "200")

									.add("httpStatusMessage", "transaction logged successfully")
									.build();
						
						
						
						
					}
					
					else
					{

				        jsonObject = Json.createObjectBuilder()
								     .add("httpStatusCode", "400")

				        		    .add("httpStatusMessage", "Json fields validation failed: please double check your request data")
									.build();
						
						
					}
					
					
					
					
					
					
					
				
				}
				
				catch(JsonParsingException ex)
				{


					throw new UserRequestExceptionMapper("Error parsing posted data please do check your data format");
				
		    		
				
				}

				
			}
			
    	}
    	
    	
		ResponseBuilder builder = Response.status(Status.OK)
			      .entity(jsonObject.toString());
	
	    return builder.build();
	}

}
