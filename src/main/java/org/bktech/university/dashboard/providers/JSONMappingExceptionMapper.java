package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.JsonMappingException;

@Provider
public class JSONMappingExceptionMapper implements ExceptionMapper<JsonMappingException>{
	
	@Override
	public Response toResponse(JsonMappingException ex)
	{
		ClientResponse response = new ClientResponse("Invalid JSON, at least one of the JSON field type is not readable by the system", "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	}

}
