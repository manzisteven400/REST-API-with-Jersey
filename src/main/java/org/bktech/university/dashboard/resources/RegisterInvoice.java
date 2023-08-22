package org.bktech.university.dashboard.resources;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
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
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.bktech.university.dashboard.Globals;
import org.bktech.university.dashboard.ejb.PaymentLogDAO;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.models.AcademicProgram;
import org.bktech.university.dashboard.models.Bank;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.InstitutionCalender;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.StudyProgramType;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;
import org.bktech.university.dashboard.models.InvoiceBankAccount;

import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.DegreeProgram;


@Path("/invoice")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

public class RegisterInvoice {
	
    @Inject
	 StudentAccountDAO studentAccountDAO;
    
    @Inject
	 PaymentLogDAO paymentLogDAO;
    
    @Inject
	UserAccountDAO userAccountDAO;

    

    @Context HttpServletRequest request;
	@Context HttpHeaders httpHeader;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
	public Response registerInvoice() throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
	{
    	
    	JsonObject jsonObject = null;

    	String universityCode = httpHeader.getHeaderString("universityCode");
    	
    	if(universityCode == null)
    	{

			throw new UnAuthorizedExceptionMapper("universityCode is required");
		
    	
    	}
    	

    	String username = httpHeader.getHeaderString("username");
    	
    	if(username == null)
    	{

			throw new UnAuthorizedExceptionMapper("username is required");
		
    	
    	}
    	

    	String password = httpHeader.getHeaderString("password");
    	
    	if(password == null)
    	{

			throw new UnAuthorizedExceptionMapper("password is required");
		
    	
    	}
    	
	    ThirdPartyAuthentication thirdPartyAuthentication = userAccountDAO.validateThirdPartyCredentials(username,password);

		if(thirdPartyAuthentication != null)
		{
		    Institution institution = studentAccountDAO.getInstitution(universityCode);
		    
		    if(institution != null)
		    {
		    	String bodyContent = null;
		    	try
		    	{
					bodyContent = org.apache.commons.io.IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());

		    	}
		    	catch(Exception e)
		    	{
		    		throw new UserRequestExceptionMapper(e.getMessage());

		    	}
		    	
		    	try
		    	{


					

		    	    String invoiceNumber = null;
		    		String payerFirstName = null;
		    		String payerLastName = null;
		    		double amount = 0.0;
		    		String bankAccount = null;
		    		String paymentDescription = null;
		    		
					  
					  JsonParser parser = Json.createParser(new StringReader(bodyContent));
					  while(parser.hasNext())
					 {
						  JsonParser.Event event = parser.next();

							switch(event)
							{
							   case KEY_NAME:
								   if(parser.getString().equalsIgnoreCase("invoiceNumber"))
								   {
									  
									   event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  //System.out.println("Access token: "+parser.getString());
									    	  invoiceNumber = parser.getString();
									    	  
									    	  
									   }
								   }
								   else if(parser.getString().equalsIgnoreCase("payerFirstName"))
								   {

									  event = parser.next();
									   
									   switch(event)
									   {
									      case VALUE_STRING:
									    	  payerFirstName = parser.getString();

									    	  break;
									    	  
									   }
								   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("payerLastName"))
								   {


										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  payerLastName = parser.getString();

										    	  break;
										    	  
										   }
									   
									   
									   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("amount"))
								   {



										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_NUMBER:
										    	  amount = parser.getLong();

										    	  break;
										    	  
										   }
									   
									   
									   
								   
									   
								   }

								   else if(parser.getString().equalsIgnoreCase("bankAccount"))
								   {



										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  bankAccount = parser.getString();

										    	  break;
										    	  
										   }
									   
									   
									   
								   
									   
								   }
								   
								   else if(parser.getString().equalsIgnoreCase("paymentDescription"))
								   {




										  event = parser.next();
										   
										   switch(event)
										   {
										      case VALUE_STRING:
										    	  paymentDescription = parser.getString();

										    	  break;
										    	  
										   }
									   
									   
									   
								   
									   
								   
									   
								   }


								   else
								   {
		                               throw new UserRequestExceptionMapper(parser.getString()+" is not a valid json field");

								   }
							}
							
						 
					  }
					  
					  if(invoiceNumber != null && payerFirstName != null && payerLastName != null && amount != 0.0 && bankAccount != null && paymentDescription != null)
					  {
						  
							InvoiceBankAccount bankAccountObj = paymentLogDAO.validateInvoiceBankAccount(bankAccount,institution.getId());
							
							if(bankAccountObj != null)
							{

								Faculty facultyObj = new Faculty();
								facultyObj.setInstitutionId(institution);
								facultyObj.setName(universityCode+"/"+invoiceNumber);
								facultyObj.setAccronym(universityCode+"/"+invoiceNumber);
								facultyObj.setCode(universityCode+"/"+invoiceNumber);
								studentAccountDAO.registerFaculty(facultyObj);
								
							    AcademicProgram academicProgram = studentAccountDAO.getAcademicProgram("undergraduate program");
								StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType("day program");
								DegreeProgram degreeProgram = new DegreeProgram();

								degreeProgram.setInstitutionId(institution);
								degreeProgram.setFaculty(facultyObj);
								degreeProgram.setAcademicProgram(academicProgram);
								degreeProgram.setDegreeName(universityCode+"/"+invoiceNumber);
								
								studentAccountDAO.registerDegreeProgram(degreeProgram);
								
								Faculty faculty = studentAccountDAO.getFaculty(universityCode+"/"+invoiceNumber, institution.getId());
								DegreeProgram degreeProgramObj = studentAccountDAO.getDegreeProgram(universityCode+"/"+invoiceNumber,institution.getId());
								InstitutionCalender institutionCalender = studentAccountDAO.getInstitutionCalenderByInstitutionId(institution.getId());
                                Student std = new Student();

								Date dateObject = new Date();
								DateFormat cdf = new SimpleDateFormat("yyyy-MM-dd");
								cdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
								String ctimeStamp = cdf.format(dateObject);
								Calendar calendar = getCalendar(ctimeStamp);
								int invoiceExpiryPeriod = institution.getInvoiceExpiryDate();
								calendar.add(Calendar.WEEK_OF_MONTH,invoiceExpiryPeriod);
								String dateCalendar = getDate(calendar);
								java.sql.Date sqlDate = getDate(dateCalendar);


								std.setReg_number(invoiceNumber);
								std.setFirst_name(payerFirstName);
								std.setLast_name(payerLastName);
								std.setInstitutionId(institution);
								std.setFacultyId(faculty);
								std.setDegreeProgramId(degreeProgramObj);
								std.setAcademicProgram(academicProgram);
								std.setStudyProgramType(studyProgramType);
								std.setIsValidStudentRecord("no");
							    std.setInvoiceExpiryDate(sqlDate);
								std.setInstitutionCalender(institutionCalender);
								std.setStudentStatus("ACTIVE");
								std.setApplicantStatus("DONE");
								std.setStudentClass("");
								
								studentAccountDAO.registerStudent(std);
								
								BankAccount bankAccountObject = new BankAccount();
								
								


								bankAccountObject.setBank(Long.valueOf(1));
								bankAccountObject.setFacultyId(faculty);
								bankAccountObject.setInstitutionId(institution);
								bankAccountObject.setAccountNumber(bankAccount);
								bankAccountObject.setAccountStatus("Active");
								
								paymentLogDAO.registerBankAccount(bankAccountObject);
								
								PaymentPurpose paymentPurpose = new PaymentPurpose();


								paymentPurpose.setBankAccount(paymentLogDAO.getBankAccountByInstitutionID(bankAccount,faculty.getId(),institution.getId()));
								paymentPurpose.setInstitutionId(institution);
								paymentPurpose.setPurpose(paymentDescription);
								paymentPurpose.setDescription(paymentDescription);
								paymentPurpose.setFacultyId(faculty);
								paymentPurpose.setHasDependent(0);
								paymentPurpose.setAccPriority("1");
								
								paymentLogDAO.registerPaymentPurpose(paymentPurpose);
								
								

								jsonObject = Json.createObjectBuilder()
								   .add("httpStatusMessage", "saved")

								   .build();
						    

						    	ResponseBuilder builder = Response.status(Status.OK)
									       .entity(jsonObject.toString());
						 	
						 	     return builder.build();
								


								
							
							
							}
							else
							{




								throw new UserRequestExceptionMapper("Bank account not registered");
							
					    		
							
					    	
							  
						  
								
							}

						  
					  }
					  else
					  {



							throw new UserRequestExceptionMapper("One or more required fields are missing");
						
				    		
						
				    	
						  
					  }
					  
					  
					  
		    	}
		    	catch(JsonParsingException ex)
		    	{


					throw new UserRequestExceptionMapper("Error parsing posted data please do check your data format");
				
		    		
				
		    	}
		    	
		    }
		    else
		    {


				 
	            throw new UnAuthorizedExceptionMapper("Institution profile not yet created");
					
				 
			
		    }

			
		}
		else
		{

			 
            throw new UnAuthorizedExceptionMapper("Authentication failed: either username or password is incorrect");
				
			 
		}
		

    	
    	
    	


    	
	}


	public  Calendar getCalendar(String timeStamp)
	{

		Calendar calendar = null;
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Date dateCalendar = df.parse(timeStamp);
			calendar = Calendar.getInstance();
			calendar.setTime(dateCalendar);
			
	    }
		catch(ParseException ex)
		{
			
		}
		
		return calendar;
		
	
	
	}
	
	public String getDate(Calendar calendar)
	{


		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
        String date = df.format(calendar.getTime());
        
        return date;
	
		

	
	
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
