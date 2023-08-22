package org.bktech.university.dashboard.ejb;

import java.util.List;














import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.bktech.university.dashboard.models.UserAccount;



@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class StatisticsDAO implements StatisticsService{
	
	public StatisticsDAO(){}
	
	private EntityManager em = Persistence.createEntityManagerFactory("UniversityFees").createEntityManager();

    @SuppressWarnings("unchecked")
    public List<Object[]> getNumberOfStudents()
	{
		
         List<Object[]> listOfStudents = em.createNamedQuery("getTotalNumberOfStudents").getResultList();
         
         return listOfStudents;
		
	}
    
    @SuppressWarnings("unchecked")
    public List<Object[]> getNumberOfTransactions()
    {

		List<Object[]> listOfTransactions = em.createNamedQuery("getTotalNumberOfTransactions")
		.setParameter("paymentStatus", "posted")
		.getResultList();
        
        return listOfTransactions;
		
	
    	
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> getTotalPaidAmount()
	{
		
		List<Object[]> totalPaidAmount = em.createNamedQuery("getTotalPaidAmount")
		.setParameter("paymentStatus", "posted")
	    .getResultList();
        
        return totalPaidAmount;
		
	}
    
    @SuppressWarnings("unchecked")
    public List<Object[]> getTotalPaidAmountByChannel(String channel)
	{
		
        List<Object[]> totalPaidAmountByChannel = em.createNamedQuery("getTotalPaidAmountByChannel")
        .setParameter("paymentStatus", "posted")
        .setParameter("paymentChannel", channel)
        .getResultList();
        
        return totalPaidAmountByChannel;
		
	
		
	}
    
	public Long getNumberOfStudentsByInstitution(Long institutionId)
	{

		TypedQuery<Long> numberOfStudents =  em.createNamedQuery("getTotalNumberOfStudentsByInstitution", Long.class);
		numberOfStudents.setParameter("institutionId", institutionId);
		return numberOfStudents.getSingleResult();

	}
	
	public Long getNumberOfTransactionsByInstitution(Long institutionId)
	{


		TypedQuery<Long> numberOfTransactions =  em.createNamedQuery("getTotalNumberOfTransactionsByInstitution", Long.class);
		numberOfTransactions.setParameter("institutionId", institutionId);
		numberOfTransactions.setParameter("paymentStatus", "posted");

		return numberOfTransactions.getSingleResult();

	
	}
	
	public Double getTotalPaidAmountByInstitution(Long institutionId)
	{

		TypedQuery<Double> paidAmount =  em.createNamedQuery("getTotalPaidAmountByInstitution", Double.class);
		paidAmount.setParameter("institutionId", institutionId);
		paidAmount.setParameter("paymentStatus", "posted");

		return paidAmount.getSingleResult();

	}
	
	public Double getTotalPaidAmountByChannelAndInstitution(String channel, Long institutionId)
	{


		TypedQuery<Double> paidAmount =  em.createNamedQuery("getTotalPaidAmountByChannelAndInstitution", Double.class);
		paidAmount.setParameter("institutionId", institutionId);
		paidAmount.setParameter("paymentChannel", channel);
		paidAmount.setParameter("paymentStatus", "posted");

		return paidAmount.getSingleResult();

	
		
	}
	
    @SuppressWarnings("unchecked")
    public List<Object[]> getTotalPaidAmountByService(Long institutionId)
	{
		List<Object[]> totalPaidAmount = em.createNamedQuery("getTotalPaidAmountPerServicePerInstitution")
				.setParameter("institutionId", institutionId)
				.setParameter("paymentStatus","posted")
			    .getResultList();
		        
		        return totalPaidAmount;
		
	}

    @SuppressWarnings("unchecked")
    public List<Object[]> getTotalPaidAmountBySubService(Long institutionId)
	{

		List<Object[]> totalPaidAmount = em.createNamedQuery("getTotalPaidAmountPerSubServicePerInstitution")
				.setParameter("institutionId", institutionId)
			    .setParameter("paymentStatus","posted")
			    .getResultList();
		        
		        return totalPaidAmount;
		
	
		
	}

    @SuppressWarnings("unchecked")
    public List<Object[]> getNumberOfTransactionsForCaching(String month)
    {
    	List<Object[]> listOfTransactions = em.createNamedQuery("getNumberOfTransactions")
		.setParameter("paymentStatus", "posted")
		.setParameter("paymentMonth", month)
        .getResultList();
        
        return listOfTransactions;
		
	
    	
    
    }
    
	public Long getNumberOfTransactionsByMonthByInstitution(String month, Long institutionId)
	{
		
        try
        {
		  TypedQuery<Long> numberOfTransactions =  em.createNamedQuery("getNumberOfTransactionsPerMonthPerInst", Long.class);
		  numberOfTransactions.setParameter("institutionId", institutionId);
		  numberOfTransactions.setParameter("paymentStatus", "posted");
		  numberOfTransactions.setParameter("paymentMonth", month);

		  return numberOfTransactions.getSingleResult();
        }
		catch(NoResultException e)
		{
			return 0L;
		}
	
		
	}



    
    

}
