package org.bktech.university.dashboard.resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;



@Path("/institution")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class InstitutionResource {
	
    @Inject
	StudentAccountDAO studentAccountDAO;
    
    @Inject
	UserAccountDAO userAccountDAO;
    
    
    @Context HttpHeaders httpHeader;
    @GET
    @Produces("application/json")
    public Response getListOfActiveInstitutions()  throws UserRequestExceptionMapper, UnAuthorizedExceptionMapper
    {
    	String username = httpHeader.getHeaderString("username");
    	String password = httpHeader.getHeaderString("password");
    	
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
				
				JsonArrayBuilder institutions = Json.createArrayBuilder();
				
				for(Institution institutionObj : studentAccountDAO.getAllActiveInstitutions("active"))
				{
					JsonObjectBuilder jsonObject = Json.createObjectBuilder();
					jsonObject.add("id", institutionObj.getId());
					jsonObject.add("name", institutionObj.getName());

					institutions.add(jsonObject);


				}
				
				JsonArray jsonArray = institutions.build();
				
				return Response.status(Status.OK)
					       .entity(jsonArray.toString())
					       .build();

				
			}
			else
			{


				throw new UnAuthorizedExceptionMapper("Authentication failed");
			
	    	
			}


    	}
		
    	

    	
    	
    }

}
