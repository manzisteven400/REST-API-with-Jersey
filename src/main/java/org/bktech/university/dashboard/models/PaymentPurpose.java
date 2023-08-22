package org.bktech.university.dashboard.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries(
		{
			@NamedQuery
			(
				name = "getPaymentPurposeByFacultyID",
				query="SELECT a FROM PaymentPurpose a inner join a.facultyId f WHERE f.id = :facultyID"
			
			),
			
			@NamedQuery
			(
				name = "IsPaymentPurposeRegistered",
				query="SELECT a FROM PaymentPurpose a inner join a.facultyId f WHERE f.id = :facultyID AND a.purpose = :purpose "
			
			),
			
			@NamedQuery
			(
				name = "getPaymentPurposeByID",
				query="SELECT a FROM PaymentPurpose a WHERE a.id = :PaymentPurposeID"
			
			)
			
			
			
		})


@XmlRootElement
@Entity
@Table(name="payment_purpose")
public class PaymentPurpose {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="purpose")
	private String purpose;
	

	@JoinColumn(name="bank_account_id")
	private BankAccount bankAccount;
	
	@ManyToOne
	@JoinColumn(name="faculty_id")
	private Faculty facultyId;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="acc_priority")
	private String accPriority;
	
	@Column(name="has_dependent")
	private int hasDependent;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public BankAccount getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}


	public Faculty getFacultyId() {
		return facultyId;
	}


	public void setFacultyId(Faculty facultyId) {
		this.facultyId = facultyId;
	}


	public Institution getInstitutionId() {
		return institutionId;
	}


	public void setInstitutionId(Institution institutionId) {
		this.institutionId = institutionId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAccPriority() {
		return accPriority;
	}


	public void setAccPriority(String accPriority) {
		this.accPriority = accPriority;
	}


	public int getHasDependent() {
		return hasDependent;
	}


	public void setHasDependent(int hasDependent) {
		this.hasDependent = hasDependent;
	}
	
	
	
	

}
