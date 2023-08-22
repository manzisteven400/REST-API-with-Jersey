package org.bktech.university.dashboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries(
		{
		
			
			@NamedQuery
			(
				name = "deleteBankAccount",
				query="DELETE FROM BankAccount a  WHERE a.id = :bankAccountID"
			
			),
			
			@NamedQuery
			(
				name = "getBankAccountByFacultyID",
				query="SELECT a FROM  BankAccount  a inner join a.facultyId f  WHERE f.id = :facultyID"
			
			),
			
			@NamedQuery
			(
				name = "getBankAccount",
				query="SELECT a FROM  BankAccount  a  WHERE TRIM(FROM a.accountNumber) = :accountNumber"
			
			),
			@NamedQuery
			(

					name = "getBankAccountByInstitutionID",
					query="SELECT a FROM  BankAccount  a inner join a.facultyId f inner join a.institutionId i WHERE TRIM(FROM a.accountNumber) = :accountNumber AND f.id = :facultyId AND i.id = :institutionId"
				
					
					
			)
			
			
			
		})

@XmlRootElement
@Entity
@Table(name="bank_account")
public class BankAccount {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	
	@Column(name="bank_id")

	private Long bank;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="account_status")

	private String accountStatus;
	
	@OneToOne
	@JoinColumn(name="faculty_id")
	private Faculty facultyId;
	

	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



    

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	
	

}
