package org.bktech.university.dashboard.resources;

import java.io.BufferedReader;
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

import java.util.HashMap;

import org.bktech.university.dashboard.Globals;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.DegreeProgram;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.StudyProgramType;
import org.bktech.university.dashboard.models.InstitutionCalender;
import org.bktech.university.dashboard.models.AcademicProgram;







@Path("ulkstudent")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RegisterULKStudents {
	
    @Inject
	StudentAccountDAO studentAccountDAO;
	
	
	@Context HttpServletRequest request;
	@Context HttpHeaders httpHeader;
    @POST
    @Produces("application/json")
	@Consumes("application/json")
	public Response registerPayment() throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
	{
    	
    	
    	
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
			            char [] body = new char[request.getContentLength()];
						
						//java.io.InputStreamReader input = new java.io.InputStreamReader(request.getInputStream());
						
						//org.apache.commons.io.IOUtils.readFully(input, body);
						
						
						 
						//String bodyContent = new String(body);
						
						bodyContent = org.apache.commons.io.IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
						
						postData = bodyContent;
						
						//BufferedReader reader = request.getReader();
						//BufferedReader reader = new BufferedReader(input);

						/**while((line = reader.readLine()) != null)
						{
							buffer.append(line);
							buffer.append("/n");
						}**/
					
			    	}
			    	catch(Exception e)
			    	{

						e.printStackTrace();
						//System.out.println(e.getMessage());
			    		throw new UserRequestExceptionMapper(e.getMessage());
					
			    		
			    	}
			    	
			    	try
			    	{

						JsonParser parser = Json.createParser(new StringReader(postData.trim()));
					
						
						if(parser.next()!=JsonParser.Event.START_ARRAY)
						{
			               
							throw new UserRequestExceptionMapper("Request not processed: an array of objects was expected");
						
				    	}
						
						else
						{
							while(parser.hasNext())
							{
							   try
							   {
								
								String regNumber = null;
								String firstName = null;
								String lastName = null;
								String campus = null;
								String phoneNumber = null;
								String NID = null;
								String dateOfBirth = null;
								String gender = null;
								String degreeProgram = null;
								String section = null;
								String studentClass = null;
								String paymentCode = null;
								String isStudentRegistered = null;

                                JsonParser.Event event = parser.next();
								
								if(event == JsonParser.Event.START_OBJECT)
								{

									 while(parser.hasNext())
									 {
										  if(event == JsonParser.Event.END_OBJECT)
										  {
											break;
															 
										   }
										
										
										       event = parser.next();
										       
										      

										        switch(event)
												{
												   case KEY_NAME:
													   if(parser.getString().equalsIgnoreCase("regNumber"))
													   {
														  
														   event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:
														    	  regNumber = parser.getString();
														    	  break;
														   }
													   }
													   else if(parser.getString().equalsIgnoreCase("firstName"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:
														    	   

														    	  firstName= parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   else if(parser.getString().equalsIgnoreCase("lastName"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  lastName = parser.getString();


														    	  break;
														    	  
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
													   else if(parser.getString().equalsIgnoreCase("phoneNumber"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  phoneNumber = parser.getString();



														    	  break;
														    	  
														   }
													   
													   }
													   else if(parser.getString().equalsIgnoreCase("NID"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  NID = parser.getString();

														    	  break;
														    	  
														   }
													   
													   }
													   else if(parser.getString().equalsIgnoreCase("dateOfBirth"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  dateOfBirth = parser.getString();

														    	  break;
														    	  
														   }
													   
													   }
													   
													   else if(parser.getString().equalsIgnoreCase("gender"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  gender = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   
													   else if(parser.getString().equalsIgnoreCase("degreeProgram"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  degreeProgram = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   else if(parser.getString().equalsIgnoreCase("section"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  section = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   
													   else if(parser.getString().equalsIgnoreCase("studentClass"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  studentClass = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   
													   else if(parser.getString().equalsIgnoreCase("paymentCode"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  paymentCode = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
													   
													   else if(parser.getString().equalsIgnoreCase("isStudentRegistered"))
													   {

														  event = parser.next();
														   
														   switch(event)
														   {
														      case VALUE_STRING:

														    	  isStudentRegistered = parser.getString();


														    	  break;
														    	  
														   }
													   
													   }
												
													   else
													   {
							                               throw new UserRequestExceptionMapper(parser.getString()+" is not a valid json field");

													   }
												}
										        
								 		
											 
										  
									 
	                                  //break;
										  
									 }
									 // the try block was here before 
									
									 if(isStudentRegistered != null )
									 {

										 Institution institution = studentAccountDAO.getInstitution("ULK");
										 InstitutionCalender institutionCalender = studentAccountDAO.getInstitutionCalender("2018-2019",institution.getId());
										 if(isStudentRegistered.equalsIgnoreCase("no"))
										 {
											 if(paymentCode != null && firstName != null && lastName != null &&!paymentCode.isEmpty() && campus != null &&!campus.isEmpty() )
											 {
												 System.out.println("About to persist an applicant");
												 Student stdObject = studentAccountDAO.getStudent(paymentCode);
												 
												 if(stdObject == null)
												 {
													 System.out.println("applicant not found");

													 Student student = new Student();
													 student.setReg_number(paymentCode);
													 student.setFirst_name(firstName);
													 student.setLast_name(lastName);
													 Faculty faculty = studentAccountDAO.getFacultyByName(campus.trim()+"/APPLICATION FEES");
													 student.setFacultyId(faculty);
									
													 DegreeProgram degreeProgramObj = studentAccountDAO.getDegreeProgramByName("not available");
													 StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType("day");
													 AcademicProgram academicProgram = studentAccountDAO.getAcademicProgram("undergraduate");
													 student.setInstitutionId(institution);
													 student.setInstitutionCalender(institutionCalender);
													 student.setDegreeProgramId(degreeProgramObj);
													 student.setStudyProgramType(studyProgramType);
													 student.setAcademicProgram(academicProgram);
													 student.setStudentStatus("ACTIVE");
													 student.setApplicantStatus("DONE");
													 studentAccountDAO.registerStudent(student);
													 
													 System.out.println("applicant peristed");

													 
												 }
												 
											
												 
												 
												 

											 }
										 }
										 
										 else if(isStudentRegistered.equalsIgnoreCase("yes"))
										 {
											 if(paymentCode != null)
											 {
												 HashMap<String,String> studentRecords = new HashMap<String,String>();
												 studentRecords.put("regNumber",regNumber);
												 studentRecords.put("firstName",firstName);
												 studentRecords.put("lastName",lastName);
												 studentRecords.put("campus",campus);
												 studentRecords.put("gender",gender);
												 studentRecords.put("degreeProgram",degreeProgram);
												 studentRecords.put("section",section);
												 studentRecords.put("studentClass",studentClass);
												 studentRecords.put("phoneNumber",phoneNumber);
												 studentRecords.put("NID",NID);
												 studentRecords.put("dateOfBirth",dateOfBirth);



												 
												 
												 if(!paymentCode.isEmpty())
												 {
													
													 System.out.println("reg number "+regNumber);
													 System.out.println("campus "+campus);


													 Student studentObj = studentAccountDAO.getStudent(paymentCode);
													 if(studentObj != null && !regNumber.isEmpty() && !campus.isEmpty())
													 {
														 Student studentObject = studentAccountDAO.getStudent(regNumber);
														 if(studentObject == null && studentObj.getInstitutionId().getId()==institution.getId())
														 {
															 studentAccountDAO.updateStudentRecord(studentObj,studentRecords,institution);
														 }

														 
													 }
													 else
													 {
														 Student studentObject = studentAccountDAO.getStudent(regNumber);
														 if(studentObject != null && studentObject.getInstitutionId().getId()==institution.getId())
														 {
															 
															 studentAccountDAO.updateStudentRecord(studentObject,studentRecords,institution);

															 
														 }
														 else
														 {


																Student student = new Student();
															    Long facultyId = Long.valueOf(0);
																Faculty faculty = null;
															    student.setReg_number(studentRecords.get("regNumber").trim());
																student.setFirst_name(studentRecords.get("firstName"));
																student.setLast_name(studentRecords.get("lastName"));
																student.setInstitutionId(institution);
																student.setInstitutionCalender(institutionCalender);

																if(!studentRecords.get("campus").isEmpty())
																{
																	faculty = studentAccountDAO.getFaculty(studentRecords.get("campus").trim(), institution.getId());
																	if(faculty != null)
																	{
																	   student.setFacultyId(faculty);
																	   facultyId = faculty.getId();
																	}
																}
																if(!studentRecords.get("gender").isEmpty())
																{
																	student.setSex(studentRecords.get("gender"));
																}
																if(!studentRecords.get("degreeProgram").isEmpty())
																{
																	if(facultyId>0)
																	{
																		
																		DegreeProgram degreeProgramObject = studentAccountDAO.getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
																		if(degreeProgramObject == null)
																		{
																			DegreeProgram degreeProgramObj = new DegreeProgram();
																			degreeProgramObj.setInstitutionId(institution);
																			degreeProgramObj.setFaculty(faculty);
																			AcademicProgram academicProgram = studentAccountDAO.getAcademicProgram("undergraduate");
																			degreeProgramObj.setAcademicProgram(academicProgram);
																			degreeProgramObj.setDegreeName(studentRecords.get("degreeProgram"));
																			degreeProgramObj.setDegreeAccronym(studentRecords.get("degreeProgram"));
																			degreeProgramObj.setStatus("Active");
																			studentAccountDAO.registerDegreeProgram(degreeProgramObj);
																			degreeProgramObject = studentAccountDAO.getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
														                    
																			
																		}
																		
														                student.setDegreeProgramId(degreeProgramObject);
																	}
																	
																	
																}
																
																if(studentRecords.get("degreeProgram").isEmpty())
																{
																	
																	DegreeProgram degreeProgramObj = studentAccountDAO.getDegreeProgramByName("not available");
													                student.setDegreeProgramId(degreeProgramObj);

																}

																
																 

											                  if(!studentRecords.get("section").isEmpty())
																{
																	StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType(studentRecords.get("section"));
																	if(studyProgramType != null)
																	{
																		student.setStudyProgramType(studyProgramType);
																	}
																	else
																	{
																		 studyProgramType = studentAccountDAO.getStudyProgramType("day");
																		 student.setStudyProgramType(studyProgramType);

																	}
																	
																}
											                  if(studentRecords.get("section").isEmpty())
											                  {
																	 StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType("day");
																	 student.setStudyProgramType(studyProgramType);


											                  }

											                  
																if(!studentRecords.get("studentClass").isEmpty())
																{
																	if(!studentRecords.get("studentClass").equals("-"))
																    student.setStudentClass(studentRecords.get("studentClass"));
																}
																
																if(!studentRecords.get("phoneNumber").isEmpty())
																{
																	if(studentRecords.get("phoneNumber").length() == 9)
																	{
																		Student std = studentAccountDAO.getStudentByPhone("0"+studentRecords.get("phoneNumber"));
																		if(std == null)
																		{
																			student.setPhone("0"+studentRecords.get("phoneNumber"));
																		}
																	}
																	else
																	{

																		Student std = studentAccountDAO.getStudentByPhone(studentRecords.get("phoneNumber"));
																		if(std == null)
																		{
																			student.setPhone(studentRecords.get("phoneNumber"));
																		}
																	
																		
																	}
																}
																
																if(!studentRecords.get("NID").isEmpty())
																{
																	if(!studentRecords.get("NID").equals("0"))
																	{
																		Student std = studentAccountDAO.getStudentByNID(studentRecords.get("NID"));
																		if(std == null)
																		{
																			student.setNida(studentRecords.get("NID"));
																		}
																	}
																}
																
																if(!studentRecords.get("dateOfBirth").isEmpty())
																{
																	String [] splittedDate = studentRecords.get("dateOfBirth").split("\\s+");
																	student.setDob(splittedDate[0]);
																}
																

																 AcademicProgram academicProgram = studentAccountDAO.getAcademicProgram("undergraduate");
																 student.setAcademicProgram(academicProgram);
																 
															     student.setStudentStatus("ACTIVE");
																 student.setApplicantStatus("DONE");
																 
																 
																 studentAccountDAO.registerStudent(student);
															
															 
														 
															 
														 }

													 }
													 
												 }
												 else
												 {
													 if(!campus.isEmpty() && !regNumber.isEmpty())
													 {
														 Student stdobj = studentAccountDAO.getStudent(regNumber);
														 if(stdobj == null)
														 {

																Student student = new Student();
															    Long facultyId = Long.valueOf(0);
																Faculty faculty = null;
															    student.setReg_number(studentRecords.get("regNumber").trim());
																student.setFirst_name(studentRecords.get("firstName"));
																student.setLast_name(studentRecords.get("lastName"));
																student.setInstitutionId(institution);
																student.setInstitutionCalender(institutionCalender);
																if(!studentRecords.get("campus").isEmpty())
																{
																	faculty = studentAccountDAO.getFaculty(studentRecords.get("campus").trim(), institution.getId());
																	if(faculty != null)
																	{
																	   student.setFacultyId(faculty);
																	   facultyId = faculty.getId();
																	}
																}
																if(!studentRecords.get("gender").isEmpty())
																{
																	student.setSex(studentRecords.get("gender"));
																}
																if(!studentRecords.get("degreeProgram").isEmpty())
																{
																	if(facultyId>0)
																	{
																		AcademicProgram academicProgram = studentAccountDAO.getAcademicProgram("undergraduate");
																		student.setAcademicProgram(academicProgram);
																		
																		DegreeProgram degreeProgramObject = studentAccountDAO.getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
																		if(degreeProgramObject == null)
																		{
																			DegreeProgram degreeProgramObj = new DegreeProgram();
																			degreeProgramObj.setInstitutionId(institution);
																			degreeProgramObj.setFaculty(faculty);
																			degreeProgramObj.setAcademicProgram(academicProgram);
																			degreeProgramObj.setDegreeName(studentRecords.get("degreeProgram"));
																			degreeProgramObj.setDegreeAccronym(studentRecords.get("degreeProgram"));
																			degreeProgramObj.setStatus("Active");
																			studentAccountDAO.registerDegreeProgram(degreeProgramObj);
																			degreeProgramObject = studentAccountDAO.getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
														                    
																			
																		}
																		
														                student.setDegreeProgramId(degreeProgramObject);
																	}
																	
																	
																}
																
																if(studentRecords.get("degreeProgram").isEmpty())
																{
																	
																	DegreeProgram degreeProgramObj = studentAccountDAO.getDegreeProgramByName("not available");
													                student.setDegreeProgramId(degreeProgramObj);

																}

																
																 

											                  if(!studentRecords.get("section").isEmpty())
																{
																	StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType(studentRecords.get("section"));
																	if(studyProgramType != null)
																	{
																		student.setStudyProgramType(studyProgramType);
																	}
																	else
																	{
																		 studyProgramType = studentAccountDAO.getStudyProgramType("day");
																		 student.setStudyProgramType(studyProgramType);

																	}
																	
																}
											                  if(studentRecords.get("section").isEmpty())
											                  {
																	 StudyProgramType studyProgramType = studentAccountDAO.getStudyProgramType("day");
																	 student.setStudyProgramType(studyProgramType);


											                  }

											                  
																if(!studentRecords.get("studentClass").isEmpty())
																{
																	if(!studentRecords.get("studentClass").equals("-"))
																    student.setStudentClass(studentRecords.get("studentClass"));
																}
																
																if(!studentRecords.get("phoneNumber").isEmpty())
																{
																	if(studentRecords.get("phoneNumber").length() == 9)
																	{
																		Student std = studentAccountDAO.getStudentByPhone("0"+studentRecords.get("phoneNumber"));
																		if(std == null)
																		{
																			student.setPhone("0"+studentRecords.get("phoneNumber"));
																		}
																	}
																	else
																	{

																		Student std = studentAccountDAO.getStudentByPhone(studentRecords.get("phoneNumber"));
																		if(std == null)
																		{
																			student.setPhone(studentRecords.get("phoneNumber"));
																		}
																	
																		
																	}
																}
																
																if(!studentRecords.get("NID").isEmpty())
																{
																	if(!studentRecords.get("NID").equals("0"))
																	{
																		Student std = studentAccountDAO.getStudentByNID(studentRecords.get("NID"));
																		if(std == null)
																		{
																			student.setNida(studentRecords.get("NID"));
																		}
																	}
																}
																
																if(!studentRecords.get("dateOfBirth").isEmpty())
																{
																	String [] splittedDate = studentRecords.get("dateOfBirth").split("\\s+");
																	student.setDob(splittedDate[0]);
																}
																

																student.setStudentStatus("ACTIVE");
																student.setApplicantStatus("DONE");
																 
																 
																 studentAccountDAO.registerStudent(student);
															
															 
														 }
														 else
														 {
															 if(stdobj.getInstitutionId().getId()==institution.getId())
															 studentAccountDAO.updateStudentRecord(stdobj,studentRecords,institution);

														 }
														 
													 }
												

													 
												 }
											 }
										 }

										 
										 
									 
										 
									 }
									 
									 else
									 {
										 // log the issue
									 }
									

								}
								
								// Process payment
								
									// log issue
							   }
								 catch(Throwable e)
								 {
						
									 continue;
								 }
					
							}
							
							JsonObject serverResponse = Json.createObjectBuilder()
							   .add("httpStatusMessage", "saved")

							   .build();
					    
					    	
					    	ResponseBuilder builder = Response.status(Status.OK)
								       .entity(serverResponse.toString());
					 	
					 	     return builder.build();
						}
						
						
						
			    	}
			    	catch(JsonParsingException ex)
					{

						throw new UserRequestExceptionMapper("Error parsing posted data please do check your data format");
					
			    		
					}
			    	
			    	
			    }
			    
			    else
			    {



					throw new UnAuthorizedExceptionMapper("Expired token");
				
		    		
		    	
				
			    }

			}
			else
			{


				throw new UnAuthorizedExceptionMapper("Invalid token");
			
	    		
	    	
			}
		}
		

    	
    	
    	
		
	}
    
   
    
  

    
 
    
  
    
   
    
    
   
    




}
