package org.bktech.university.dashboard.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;

import org.bktech.university.dashboard.models.InvoiceBankAccount;
import org.bktech.university.dashboard.models.Bank;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.InvoiceArchive;
import org.bktech.university.dashboard.models.PaymentLog;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.SubPaymentPurpose;
import org.bktech.university.dashboard.models.UserAccount;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PaymentLogDAO implements PaymentLogService {
	
	public PaymentLogDAO(){}
	
	private EntityManager em = Persistence.createEntityManagerFactory("UniversityFees").createEntityManager();
	
	public PaymentLog getPaymentLogByID(Long paymentLogID)
	{
		try
		{
			return em.createNamedQuery("getPaymentLogByID", PaymentLog.class)
			.setParameter("pytLogId", paymentLogID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
	
	}
	
	public void deletePaymentLog(PaymentLog paymentLog)
	{
		em.getTransaction().begin();
		em.remove(paymentLog);
		em.getTransaction().commit();
		
	}


	public int deleteBankAccount(Long bankAccountID)
	{
		int result = 0;
		
		try
		{
			em.getTransaction().begin();

			result = em.createNamedQuery("deleteBankAccount", Integer.class)
			.setParameter("bankAccountID", bankAccountID)
			.executeUpdate();
			
			em.getTransaction().commit();
			

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
		
		return result;
	
	
	}
	
	public Bank getBank(Long bankID)
	{

		try
		{
			return em.createNamedQuery("getBank", Bank.class)
			.setParameter("bankID", bankID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	}
	
	public BankAccount getBankAccountByInstitutionID(String accountNumber, Long facultyId, Long institutionId)
	{


		try
		{
			return em.createNamedQuery("getBankAccountByInstitutionID", BankAccount.class)
			.setParameter("accountNumber", accountNumber)
		    .setParameter("facultyId", facultyId)
            .setParameter("institutionId", institutionId)

			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
	}

	
	public void deletePaymentPurpose(PaymentPurpose paymentPurpose)
	{

		em.getTransaction().begin();
		em.remove(paymentPurpose);
		em.getTransaction().commit();
		
	
		
	}



	public List<PaymentLog> getPaymentLogByStudentID(Long studentID)
	{
		List<PaymentLog> paymentLogs = em.createNamedQuery("getPaymentLogByStudentID", PaymentLog.class)
	    .setParameter("studentID", studentID)
		.getResultList();
        
        return paymentLogs;
		
	
 
	}
	
	public List<PaymentLog> getReconciliationDataByDate(Long institutionId, String date)
	{

		List<PaymentLog> paymentLogs = em.createNamedQuery("getReconciliationDataByDate", PaymentLog.class)
	    .setParameter("institutionId", institutionId)
	     .setParameter("postingDate", date)

		.getResultList();
        
        return paymentLogs;
		
	
 
	
		
	}

	
	public void saveInvoice(InvoiceArchive invoiceArchive)
	{
        
		em.getTransaction().begin();
		em.persist(invoiceArchive);
		em.getTransaction().commit();

	
	}
	
	public BankAccount getBankAccountByFacultyID(Long facultyID)
	{
		try
		{
			return em.createNamedQuery("getBankAccountByFacultyID", BankAccount.class)
			.setParameter("facultyID", facultyID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	}
	
	public PaymentPurpose isPaymentPurposeRegistered(Long facultyID, String purpose)
	{
		try
		{
			return em.createNamedQuery("IsPaymentPurposeRegistered", PaymentPurpose.class)
			.setParameter("facultyID", facultyID)
			.setParameter("purpose", purpose)

			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			PaymentPurpose purposeObj = new PaymentPurpose();
			
			return purposeObj;
		
		}
		
		
	
		
	}

	
	public PaymentPurpose getPaymentByFacultyID(Long facultyID)
	{

		try
		{
			return em.createNamedQuery("getPaymentPurposeByFacultyID", PaymentPurpose.class)
			.setParameter("facultyID", facultyID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	}
	
	public void registerBankAccount(BankAccount bankAccount)
	{



		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(bankAccount);
		em.getTransaction().commit();
	
		
	
	
	
	}
	
	public InvoiceBankAccount validateInvoiceBankAccount(String bankAccount, Long institutionId)
	{



		try
		{
			return em.createNamedQuery("validateInvoiceBankAccount", InvoiceBankAccount.class)
			.setParameter("bankAccount", bankAccount)
			.setParameter("institutionId", institutionId)

			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	
	}


	
	public PaymentPurpose getPaymentPurposeByID(Long paymentPurposeID)
	{


		try
		{
			return em.createNamedQuery("getPaymentPurposeByID", PaymentPurpose.class)
			.setParameter("PaymentPurposeID", paymentPurposeID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	}

	public SubPaymentPurpose getSubPaymentPurposeByID(Long subPaymentPurposeID)
	{



		try
		{
			return em.createNamedQuery("getSubPaymentPurposebyID", SubPaymentPurpose.class)
			.setParameter("subPaymentPurposeID", subPaymentPurposeID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	
		
	}


	public void registerPaymentPurpose(PaymentPurpose paymentPurpose)
	{

		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(paymentPurpose);
		em.getTransaction().commit();
	
	}
	
	public void registerPayment(PaymentLog payment)
	{
        
		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(payment);
		em.getTransaction().commit();
	
	
	}
	
	public BankAccount getBankAccount(String accountNumber)
	{


		try
		{
			return em.createNamedQuery("getBankAccount", BankAccount.class)
			.setParameter("accountNumber", accountNumber)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	}

	public PaymentLog getPaymentLogByTelcosProcessingNumber(String processingNumber)
	{




	   try
		{
			return em.createNamedQuery("getPaymentLogByProcessingNumber", PaymentLog.class)
			.setParameter("processingNumber", processingNumber)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	
		
	
		
	}





}
