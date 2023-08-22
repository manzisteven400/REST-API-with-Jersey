package org.bktech.university.dashboard.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnAuthorizedExceptionMapper extends Exception implements ExceptionMapper<UnAuthorizedExceptionMapper>  {
	
	public static final long serialVersionUID = 1L;
	
    public UnAuthorizedExceptionMapper()
	{
		super("");
	}
	
	public UnAuthorizedExceptionMapper(String message)
	{
		super(message);
	}
	
	
	@Override
	public Response toResponse(UnAuthorizedExceptionMapper ex)
	{
		ClientResponse response = new ClientResponse(ex.getMessage(), "401");
		
		return Response.status(Status.UNAUTHORIZED)
				.entity(response)
				.build();
	 }

}
