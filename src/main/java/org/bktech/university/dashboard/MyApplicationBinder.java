package org.bktech.university.dashboard;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.bktech.university.dashboard.ejb.UserAccountDAO;
import org.bktech.university.dashboard.ejb.UserAccountInterface;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;


public class MyApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure()
	{
		bind(new Globals()).to(Globals.class);
		bind(ServiceResolver.class).to(JustInTimeInjectionResolver.class);

	}

}
