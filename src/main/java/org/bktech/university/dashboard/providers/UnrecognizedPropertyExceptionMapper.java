package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {
	
	
	@Override
	public Response toResponse(UnrecognizedPropertyException ex)
	{
		ClientResponse response = new ClientResponse("An invalid request. The field: "+ex.getPropertyName()+" is not recognized by the system", "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	}

}
