package org.bktech.university.dashboard;

import java.io.Serializable;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.enterprise.event.Event;
import javax.ejb.Schedule;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@Singleton
@ManagedBean
@ViewScoped
public class TimeService  implements Serializable{
	
	@Inject
	private Event<SomeEvent> someEvent;
	
	@Schedule(hour="*", second="5",minute="*", persistent=true)
	public void fireSomeEvent()
	{
		someEvent.fire(new SomeEvent()); 
		
	}

}
