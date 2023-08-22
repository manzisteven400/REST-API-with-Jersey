package org.bktech.university.dashboard.ejb;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.bktech.university.dashboard.models.AuthToken;
import org.bktech.university.dashboard.models.UserAccount;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AuthTokenDAO implements AuthTokenService {
	
	public AuthTokenDAO(){}
	
	private EntityManager em = Persistence.createEntityManagerFactory("UniversityFees").createEntityManager();

	
	public void saveToken(AuthToken authToken)
	{
		em.getTransaction().begin();
		em.persist(authToken);
		em.getTransaction().commit();

	}
	
	public AuthToken getTokenObject(String token)
	{

		try
		{
			return em.createNamedQuery("getToken", AuthToken.class)
			.setParameter("authToken", token)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		
		
	
	}
	
	public int deleteToken(String previousDay)
	{
		
		int result = 0;
		
		try
		{
			System.out.println("Delete function is invoked");
			
			em.getTransaction().begin();

			result = em.createNamedQuery("deleteToken", Integer.class)
			.setParameter("day", previousDay)
			.executeUpdate();
			
			em.getTransaction().commit();
			
			System.out.println("Results about to be returned");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("delete throws an exception within the DAO class");

		}
		
		return result;
	}


}
