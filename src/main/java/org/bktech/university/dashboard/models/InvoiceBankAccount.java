package org.bktech.university.dashboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries
({
	@NamedQuery
	(
			name = "validateInvoiceBankAccount",
			query="SELECT a FROM  InvoiceBankAccount  a inner join a.instId b WHERE lower(TRIM(FROM a.bankAccount)) = :bankAccount AND b.id = :institutionId"
		
	)
})


@XmlRootElement
@Entity
@Table(name="invoice_bank_account")

public class InvoiceBankAccount {
	

	
	@Id
	@Column(name="invoice_account_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long invoice_account_id;
	
	@ManyToOne
	@JoinColumn(name="inst_id")

	private Institution instId;
	

	@Column(name="bank_account")
	private String bankAccount;
	
	@Column(name="status")
	private String status;

	public Long getInvoice_account_id() {
		return invoice_account_id;
	}

	public void setInvoice_account_id(Long invoice_account_id) {
		this.invoice_account_id = invoice_account_id;
	}

	public Institution getInstId() {
		return instId;
	}

	public void setInstId(Institution instId) {
		this.instId = instId;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	


	

}
