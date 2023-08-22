package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserRequestExceptionMapper extends Exception implements ExceptionMapper<UserRequestExceptionMapper> {
	
	public static final long serialVersionUID = 1L;
	
    public UserRequestExceptionMapper()
	{
		super("");
	}
	
	public UserRequestExceptionMapper(String message)
	{
		super(message);
	}
	
	@Override
	public Response toResponse(UserRequestExceptionMapper ex)
	{
		ClientResponse response = new ClientResponse(ex.getMessage(), "400");
		
		return Response.status(Status.BAD_REQUEST)
				.entity(response)
				.build();
	 }

}
