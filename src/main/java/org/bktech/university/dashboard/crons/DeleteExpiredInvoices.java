package org.bktech.university.dashboard.crons;

import java.util.TimerTask;
import org.bktech.university.dashboard.ejb.PaymentLogDAO;
import org.bktech.university.dashboard.ejb.StudentAccountDAO;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.PaymentLog;
import org.bktech.university.dashboard.models.InvoiceArchive;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.Bank;
import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.BankAccount;
import org.bktech.university.dashboard.models.PaymentPurpose;


import java.util.List;

public class DeleteExpiredInvoices extends TimerTask {
	
	PaymentLogDAO paymentLogDAO = new PaymentLogDAO();
	StudentAccountDAO studentAccountDAO = new StudentAccountDAO();
	
	
	public void run()
	{
		try
		{   
			System.out.println("about to retrieve invoices");

			List<Student> listOfExpiredInvoices = studentAccountDAO.getExpiredInvoices("no");
			if(listOfExpiredInvoices.size()>0)
			{
				System.out.println("invoices are retrieved");

				
				for(Student invoice : listOfExpiredInvoices)
				{
					try
					{

						List<PaymentLog> paymentLogs = paymentLogDAO.getPaymentLogByStudentID(invoice.getId());
						if(paymentLogs.size()>0)
						{
							System.out.println("payment logs are retrieved");

							
							for(PaymentLog paymentLog : paymentLogs)
							{
								
								try
								{
								
								InvoiceArchive invoiceArchive = new InvoiceArchive();
								
								Institution institution = new Institution();
								institution.setId(invoice.getInstitutionId().getId());
								invoiceArchive.setInstitutionId(institution);
								invoiceArchive.setPayerNames(invoice.getLast_name()+" "+invoice.getFirst_name());
								invoiceArchive.setPaymentPurpose(paymentLog.getPaymentPurpose().getPurpose());
								invoiceArchive.setAmount(paymentLog.getAmountPaid());
								if(paymentLog.getPostingDate() != null)
								{
									invoiceArchive.setPaymentDate(paymentLog.getPostingDate());
								}
								invoiceArchive.setBankAccount(paymentLog.getBankAccount().getAccountNumber());
								if(paymentLog.getBankSlip() != null)
								{
									invoiceArchive.setBankSlip(paymentLog.getBankSlip());

								}
								invoiceArchive.setInvoiceNumber(invoice.getReg_number());
								Bank bank = paymentLogDAO.getBank(paymentLog.getBankId());
								if(bank != null)
								{
								   invoiceArchive.setBankName(bank.getName());
								}
								
								paymentLogDAO.saveInvoice(invoiceArchive);
								
								System.out.println("Invoice is saved");

								
								int result = studentAccountDAO.deleteFacultyRecord(invoice.getFacultyId().getId());
								System.out.println("faculty is deleted" +String.valueOf(result));
								
	                            paymentLogDAO.deletePaymentLog(paymentLog);
								}
								catch(Throwable e)
								{
									continue;
								}

								
							}
							
						}
						else
						{
							Faculty faculty = studentAccountDAO.getFacultyByID(invoice.getFacultyId().getId());
							
							if(faculty != null)
							{
								BankAccount bankAccount = paymentLogDAO.getBankAccountByFacultyID(faculty.getId());
								
								if(bankAccount != null)
								{
									PaymentPurpose paymentPurpose = paymentLogDAO.getPaymentByFacultyID(faculty.getId());
									
									if(paymentPurpose != null)
									{
									      paymentLogDAO.deletePaymentPurpose(paymentPurpose);
										

									}
									
									int result = paymentLogDAO.deleteBankAccount(bankAccount.getId());
									
									System.out.println("bank account is deleted" +String.valueOf(result));
									
									result = studentAccountDAO.deleteFacultyRecord(faculty.getId());
									
									System.out.println("faculty  is deleted" +String.valueOf(result));
									
									result = studentAccountDAO.deleteInvoice(invoice.getId());
									
									System.out.println("invoice  is deleted" +String.valueOf(result));



									
									
								}
							}
							
							
						}
					
						
					}
					catch(Throwable e)
					{
						continue;
					}
				}
			}
			
		}
		catch(Throwable e)
		{
			
		}
	}

}
