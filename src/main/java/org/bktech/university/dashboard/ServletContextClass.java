package org.bktech.university.dashboard;

import java.util.ArrayList;
import java.util.Timer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

import org.bktech.university.dashboard.crons.DeleteExpiredInvoices;

@Stateless
public class ServletContextClass  implements ServletContextListener {
	
	
	    //private static final Logger logger = Logger.getLogger(ServletContextClass.class);
	    
	    @Override
	    public void contextInitialized(ServletContextEvent arg0) {
	
		//logger.info("The servlet context class is invoked");
		
		
		TokenManager tokenManager = new TokenManager();
		ULKAPITokenManager ulkAPITokenManager = new ULKAPITokenManager();
		DeleteExpiredInvoices deleteExpiredInvoices = new DeleteExpiredInvoices();
		
		Timer timer = new Timer(true);
		Timer ulkAPITimer = new Timer(true);
		Timer deleteExpiredInvoicesTimer = new Timer(true);

		//timer.scheduleAtFixedRate(tokenManager, 0, 1200*1000);
		
		ulkAPITimer.scheduleAtFixedRate(ulkAPITokenManager, 0, 900*1000);
		
		//deleteExpiredInvoicesTimer.scheduleAtFixedRate(deleteExpiredInvoices, 0, 3600*1000);


		
	    final CacheMemory cacheMemory = new CacheMemory();
		
	    
	    new Thread()
		{
			public void run()
			{
				
				cacheMemory.cacheStatistics();
				//TimeService timer = new TimeService();
				//timer.fireSomeEvent();
				//TimerTestService test = new TimerTestService();
				//test.startSomeEvent(new SomeEvent());
				
				
			}
		}.start();
	    
		
	}
	
	
	
	public void contextDestroyed(ServletContextEvent arg0){
		
	
		
		
	}
	
  
}
