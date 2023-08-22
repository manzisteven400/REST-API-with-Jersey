package org.bktech.university.dashboard.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;
import org.bktech.university.dashboard.models.UserAccount;

import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UserAccountDAO implements UserAccountInterface{
	
	
	public UserAccountDAO(){}
	
	private EntityManager em = Persistence.createEntityManagerFactory("UniversityFees").createEntityManager();

	public UserAccount validateUserAccount(String username, String password)
	{
		try
		{
			return em.createNamedQuery("validateUserAccount", UserAccount.class)
			.setParameter("email", username)
			.setParameter("password", password)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		
		
	}
	
	public ThirdPartyAuthentication validateThirdPartyCredentials(String username, String password)
	{
		try
		{
			return em.createNamedQuery("validateThirdPartyCredentials", ThirdPartyAuthentication.class)
			.setParameter("username", username)
			.setParameter("password", password)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		
		
	
		
	}



}
