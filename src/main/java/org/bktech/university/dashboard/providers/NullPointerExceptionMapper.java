package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



//@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
	
	
	@Override
	public Response toResponse(NullPointerException ex)
	{
		ClientResponse response = new ClientResponse(ex.getMessage(), "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	}
	

}
