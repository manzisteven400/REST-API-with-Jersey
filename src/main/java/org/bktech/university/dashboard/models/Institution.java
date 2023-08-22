package org.bktech.university.dashboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries(
		{
			@NamedQuery
			(
				name = "getInstitution",
				query="SELECT a FROM  Institution  a  WHERE lower(TRIM(FROM a.accronym)) = :universityAccronym"
			
			),
			
			@NamedQuery
			(
				name = "getAllActiveInstitutions",
				query="SELECT a FROM  Institution  a  WHERE lower(TRIM(FROM a.status)) = :status"
			
			)
			
			
			
		})



@XmlRootElement
@Entity
@Table(name="institution")
public class Institution {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="accronym")
	private String accronym;
	
	@Column(name="inst_logo")
	private String institutionLogo;
	

	@Column(name="student_pay_transaction_fees")
	private String studentPayTransactionFees;
	

	@Column(name="status")
	private String status;
	
	@Column(name="invoice_expiry_date_in_weeks")

	private int invoiceExpiryDate;



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAccronym() {
		return accronym;
	}


	public void setAccronym(String accronym) {
		this.accronym = accronym;
	}


	public String getInstitutionLogo() {
		return institutionLogo;
	}


	public void setInstitutionLogo(String institutionLogo) {
		this.institutionLogo = institutionLogo;
	}


	public String getStudentPayTransactionFees() {
		return studentPayTransactionFees;
	}


	public void setStudentPayTransactionFees(String studentPayTransactionFees) {
		this.studentPayTransactionFees = studentPayTransactionFees;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getInvoiceExpiryDate() {
		return invoiceExpiryDate;
	}


	public void setInvoiceExpiryDate(int invoiceExpiryDate) {
		this.invoiceExpiryDate = invoiceExpiryDate;
	}
	
	
	
	

}
