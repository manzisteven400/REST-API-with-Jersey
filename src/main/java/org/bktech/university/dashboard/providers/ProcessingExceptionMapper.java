package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ProcessingException;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
	
	@Override
	public Response toResponse(ProcessingException ex)
	{
		
		
		return Response.status(Status.BAD_REQUEST)
				.entity("username cannot be null")
				.build();
	}

}
