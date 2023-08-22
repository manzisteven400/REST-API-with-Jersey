package org.bktech.university.dashboard;


import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
	
	public MyApplication () 
	{
		
		packages("org.bktech.university.dashboard");

	
		
		register(new MyApplicationBinder());


	}
	

	
	
    
    


}
