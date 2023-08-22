package org.bktech.university.dashboard.ejb;

import javax.ejb.Remote;
import org.bktech.university.dashboard.models.PaymentLog;
import org.bktech.university.dashboard.models.Bank;
import org.bktech.university.dashboard.models.InvoiceArchive;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.SubPaymentPurpose;
import org.bktech.university.dashboard.models.InvoiceBankAccount;


import java.util.List;

@Remote
public interface PaymentLogService {
	
	public PaymentLog getPaymentLogByID(Long paymentLogID);
	public void deletePaymentLog(PaymentLog paymentLog);
	public int deleteBankAccount(Long bankAccountID);
	public Bank getBank(Long bankID);
	public void deletePaymentPurpose(PaymentPurpose paymentPurpose);
	public List<PaymentLog> getPaymentLogByStudentID(Long studentID);
	public void saveInvoice(InvoiceArchive invoiceArchive);
	public BankAccount getBankAccountByFacultyID(Long facultyID);
	public PaymentPurpose getPaymentByFacultyID(Long facultyID);
	public void registerPaymentPurpose(PaymentPurpose paymentPurpose);
	public PaymentPurpose isPaymentPurposeRegistered(Long facultyID, String purpose);
	public void registerPayment(PaymentLog payment);
	public BankAccount getBankAccount(String accountNumber);
	public PaymentPurpose getPaymentPurposeByID(Long paymentPurposeID);
	public SubPaymentPurpose getSubPaymentPurposeByID(Long subPaymentPurposeID);
	public PaymentLog getPaymentLogByTelcosProcessingNumber(String processingNumber);
	public void registerBankAccount(BankAccount bankAccount);
	public InvoiceBankAccount validateInvoiceBankAccount(String bankAccount, Long institutionId);
	public BankAccount getBankAccountByInstitutionID(String accountNumber, Long facultyId, Long institutionId);
	public List<PaymentLog> getReconciliationDataByDate(Long institutionId, String date);





}
