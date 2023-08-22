package org.bktech.university.dashboard;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;

@Singleton
public class TimerTestService {
	
	
	public void startSomeEvent(@Observes SomeEvent event)
	{
		System.out.println("Timer works ......................");
	}

}
