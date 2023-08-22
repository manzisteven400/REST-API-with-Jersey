package org.bktech.university.dashboard;

import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import org.glassfish.hk2.api.Injectee;
import java.lang.reflect.Type;
import java.util.List;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.api.ActiveDescriptor;



@Service
public class ServiceResolver implements JustInTimeInjectionResolver {
	
	@Inject
	private ServiceLocator serviceLocator;
	
	@Override
	public boolean justInTimeResolution(Injectee injectee)
	{
		final Type requiredType = injectee.getRequiredType();
		
		if(injectee.getRequiredQualifiers().isEmpty() && requiredType instanceof Class)
		{
			
			final Class<?> requiredClass = (Class<?>) requiredType;
			
			if(requiredClass.getName().startsWith("org.bktech.university.dashboard"))
			{
				final List<ActiveDescriptor<?>> descriptors = ServiceLocatorUtilities.addClasses(serviceLocator, requiredClass);
				
				
				
					return !descriptors.isEmpty();
				
			}
			
		}
		
		return false;
	}
	
	
	
	

}
