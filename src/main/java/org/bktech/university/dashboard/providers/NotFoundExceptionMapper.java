package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
	
	@Override
	public Response toResponse(NotFoundException ex)
	{
		return Response.status(Status.BAD_REQUEST)
				.entity("not found exception is thrown")
				.build();
	}

}
