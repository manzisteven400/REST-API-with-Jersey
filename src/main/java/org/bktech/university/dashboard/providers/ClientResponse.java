package org.bktech.university.dashboard.providers;

public class ClientResponse {
	
	private String httpStatusCode;
	private String httpStatusMessage;

	
	
	public ClientResponse(String httpStatusMessage, String httpStatusCode)
	{
		super();
		this.httpStatusCode = httpStatusCode;
		this.httpStatusMessage = httpStatusMessage;
	}
	
	public ClientResponse()
	{
		
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

   


	
	

}
