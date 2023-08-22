package org.bktech.university.dashboard.providers;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.spi.ResponseErrorMapper;

//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>, ResponseErrorMapper {
	
	@Override
	public Response toResponse(Throwable ex)
	{
		
	    ClientResponse response = new ClientResponse("Resource invocation error: please check both data and format of your request", "500");
		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(response)
				.build();
	 }
		
	
	

}
