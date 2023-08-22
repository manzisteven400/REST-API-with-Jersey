package org.bktech.university.dashboard.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="invoice_archive")
public class InvoiceArchive {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="payer_names")
	private String payerNames;
	
	@Column(name="payment_purpose")
	private String paymentPurpose;
	
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="payment_date")
	private Timestamp paymentDate;
	
	@Column(name="bank_account")
	private String bankAccount;
	
	@Column(name="bank_slip")
	private String bankSlip;
	
	@Column(name="invoice_number")
	private String invoiceNumber;
	
	@Column(name="bank_name")
	private String bankName;
	



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Institution getInstitutionId() {
		return institutionId;
	}


	public void setInstitutionId(Institution institutionId) {
		this.institutionId = institutionId;
	}


	public String getPayerNames() {
		return payerNames;
	}


	public void setPayerNames(String payerNames) {
		this.payerNames = payerNames;
	}


	public String getPaymentPurpose() {
		return paymentPurpose;
	}


	public void setPaymentPurpose(String paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Timestamp getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public String getBankSlip() {
		return bankSlip;
	}


	public void setBankSlip(String bankSlip) {
		this.bankSlip = bankSlip;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



	
	

}
