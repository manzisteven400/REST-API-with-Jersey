package org.bktech.university.dashboard.providers;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

//@Provider
public class RuntimeExceptionMapper  implements ExceptionMapper<RuntimeException> {
	
	@Override
	public Response toResponse(RuntimeException ex)
	{
		
		
	
		ClientResponse response = new ClientResponse("Resource invocation error: please check both data and format of your request", "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	}

}
