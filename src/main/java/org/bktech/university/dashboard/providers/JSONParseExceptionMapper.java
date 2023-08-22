package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.core.JsonParseException;



@Provider
public class JSONParseExceptionMapper implements ExceptionMapper<JsonParseException>  {
	
	
	@Override
	public Response toResponse(JsonParseException ex)
	{
		ClientResponse response = new ClientResponse("Invalid JSON", "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	}

}
