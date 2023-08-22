package org.bktech.university.dashboard.models;

import java.sql.Timestamp;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.CascadeType;


@NamedQueries(
		{
			//SELECT I.id, I.name, count(S.id) from Student S LEFT JOIN  Institution I ON S.institution_id = I.id GROUP BY I.id
			
			@NamedQuery
			(
				name = "getTotalNumberOfTransactions",
				query="SELECT I.name, count(P.id) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus GROUP BY I.id"

			
			)
			,
			@NamedQuery
			(
				name = "getTotalPaidAmount",
				query="SELECT I.name, SUM(P.amountPaid) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus  GROUP BY I.id"

			
			)
			,
			@NamedQuery
			(
				name = "getTotalPaidAmountByChannel",
				query="SELECT I.name, SUM(P.amountPaid) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus AND P.paymentChannel = :paymentChannel GROUP BY I.id"

			
			)
			,
			@NamedQuery
			(
				name = "getTotalNumberOfTransactionsByInstitution",
				query="SELECT count(P.id) from PaymentLog P LEFT JOIN  P.institutionId I WHERE I.id = :institutionId AND P.paymentStatus = :paymentStatus"

			
			),
			@NamedQuery
			(
				name = "getTotalPaidAmountByInstitution",
				query="SELECT SUM(P.amountPaid) from PaymentLog P LEFT JOIN  P.institutionId I WHERE I.id = :institutionId AND P.paymentStatus = :paymentStatus"

			
			),
			@NamedQuery
			(
				name = "getTotalPaidAmountByChannelAndInstitution",
				query="SELECT SUM(P.amountPaid) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus AND P.paymentChannel = :paymentChannel AND I.id = :institutionId"

			
			),
			
			@NamedQuery
			(
				name = "getTotalPaidAmountPerServicePerInstitution",
				query="SELECT T.purpose, SUM(P.amountPaid) from PaymentLog P INNER JOIN  P.paymentPurpose T LEFT JOIN  P.subPaymentPurpose S INNER JOIN  P.institutionId I  WHERE S.id is null AND I.id = :institutionId AND P.paymentStatus = :paymentStatus GROUP BY T.id"

			
			),
			
			@NamedQuery
			(
				name = "getTotalPaidAmountPerSubServicePerInstitution",
				query="SELECT S.purpose, SUM(P.amountPaid) from PaymentLog P  INNER JOIN  P.subPaymentPurpose S INNER JOIN  P.institutionId I WHERE I.id = :institutionId AND P.paymentStatus = :paymentStatus  GROUP BY S.id"

			
			),
			
			@NamedQuery
			(
				name = "getNumberOfTransactions",
				query="SELECT I.id, count(P.id) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus AND CAST(P.postingDate AS CHAR(7)) = :paymentMonth GROUP BY I.id"

			
			),
			
			@NamedQuery
			(
				name = "getNumberOfTransactionsPerMonthPerInst",
				query="SELECT count(P.id) from PaymentLog P LEFT JOIN  P.institutionId I WHERE P.paymentStatus = :paymentStatus AND CAST(P.postingDate AS CHAR(7)) = :paymentMonth AND I.id = :institutionId GROUP BY I.id"

			
			),
			
		
			
			@NamedQuery
			(
				name = "getPaymentLogByID",
				query="SELECT a FROM PaymentLog a inner join a.bankAccount b inner join a.paymentPurpose p WHERE a.id = :pytLogId"
			
			),
			
			@NamedQuery
			(
				name = "getPaymentLogByStudentID",
				query="SELECT a FROM PaymentLog a inner join a.student s inner join a.bankAccount b inner join a.paymentPurpose p WHERE s.id = :studentID"
			
			),
			
			@NamedQuery
			(
				name = "getPaymentLogByProcessingNumber",
				query="SELECT a FROM PaymentLog a  WHERE a.processingNumber = :processingNumber"
			
			),
			@NamedQuery
			(

					name = "getReconciliationDataByDate",
					query="SELECT P from PaymentLog P INNER JOIN  P.institutionId I WHERE  CAST(P.postingDate AS CHAR(10)) = :postingDate AND I.id = :institutionId AND P.bankSlip is not null"

				
						
			
			)
			
			
			
		})


@XmlRootElement
@Entity
@Table(name="payment_log")
public class PaymentLog {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="bank_id")
	private Long bankId;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="bank_account_id")
	private BankAccount bankAccount;
	
	@ManyToOne
	@JoinColumn(name="faculty_id")
	private Faculty faculty;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="student_id")
	private Student student;
	
	
	@Column(name="institution_calender_id")
	private Long institutionCalenderId;
	
	@Column(name="payer_name")
	private String payerName;
	
	@Column(name="msisdn")
	private String msisdn;
	
	@Column(name="student_names")
	private String studentNames;
	
	@Column(name="nida")
	private String nida;
	
	@Column(name="is_registered")
	private String isRegistered;
	
	@Column(name="ext_trx_id")
	private String extTrxId;
	
	@Column(name="bank_slip")
	private String bankSlip;
	
	@Column(name="payment_channel")
	private String paymentChannel;
	
	@Column(name="operator")
	private String operator;
	
	@Column(name="status_desc")
	private String paymentStatus;
	
	@Column(name="ussd_status")
	private String ussdStatus;
	
	@Column(name="payment_year")
	private int paymentYear;
	
	@Column(name="payment_device")
	private String paymentDevice;
	
	@Column(name="processing_number")
	private String processingNumber;
	
	@Column(name="amount_paid")
	private double amountPaid;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="payment_purpose_id")
	private PaymentPurpose paymentPurpose;
	
	@ManyToOne
	@JoinColumn(name="sub_purpose_id")
	private SubPaymentPurpose subPaymentPurpose;
	
	@Column(name="posting_date")
	private Timestamp postingDate;
	
	@Column(name="payment_date")
	private Timestamp paymentDate;
	
	@Column(name="logged")
	private int logged;
	
	
	@Column(name="student_ref")
	private String registrationNumber;
	

	@Column(name="log_bank_account")
	private String LoggedBankAccount;
	
	

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

	public Long getBankId() {
		return bankId;
	}
	
	

	public String getLoggedBankAccount() {
		return LoggedBankAccount;
	}

	public void setLoggedBankAccount(String loggedBankAccount) {
		LoggedBankAccount = loggedBankAccount;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Long getInstitutionCalenderId() {
		return institutionCalenderId;
	}

	public void setInstitutionCalenderId(Long institutionCalenderId) {
		this.institutionCalenderId = institutionCalenderId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getStudentNames() {
		return studentNames;
	}

	public void setStudentNames(String studentNames) {
		this.studentNames = studentNames;
	}

	public String getNida() {
		return nida;
	}

	public void setNida(String nida) {
		this.nida = nida;
	}

	public String getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
	}

	public String getExtTrxId() {
		return extTrxId;
	}

	public void setExtTrxId(String extTrxId) {
		this.extTrxId = extTrxId;
	}

	public String getBankSlip() {
		return bankSlip;
	}

	public void setBankSlip(String bankSlip) {
		this.bankSlip = bankSlip;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getUssdStatus() {
		return ussdStatus;
	}

	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}

	public int getPaymentYear() {
		return paymentYear;
	}

	public void setPaymentYear(int paymentYear) {
		this.paymentYear = paymentYear;
	}

	public String getPaymentDevice() {
		return paymentDevice;
	}

	public void setPaymentDevice(String paymentDevice) {
		this.paymentDevice = paymentDevice;
	}

	public String getProcessingNumber() {
		return processingNumber;
	}

	public void setProcessingNumber(String processingNumber) {
		this.processingNumber = processingNumber;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public PaymentPurpose getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(PaymentPurpose paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	public SubPaymentPurpose getSubPaymentPurpose() {
		return subPaymentPurpose;
	}

	public void setSubPaymentPurpose(SubPaymentPurpose subPaymentPurpose) {
		this.subPaymentPurpose = subPaymentPurpose;
	}

	public Timestamp getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Timestamp postingDate) {
		this.postingDate = postingDate;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getLogged() {
		return logged;
	}

	public void setLogged(int logged) {
		this.logged = logged;
	}
	
	
	

}
