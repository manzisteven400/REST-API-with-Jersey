package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;

@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException>{
	
	@Override
	public Response toResponse(ParamException ex)
	{
		
		
		return Response.status(Status.BAD_REQUEST)
				.entity("param exception is thrown")
				.build();
	}

}
