package org.bktech.university.dashboard.models;

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
			//SELECT I.id, I.name, count(S.id) from Student S LEFT JOIN  Institution I ON S.institution_id = I.id GROUP BY I.id
			
			@NamedQuery
			(
				name = "getTotalNumberOfStudents",
				query="SELECT I.name, count(S.id) from Student S LEFT JOIN  S.institutionId I  GROUP BY I.id"

			
			),
			@NamedQuery
			(
				name = "getTotalNumberOfStudentsByInstitution",
				query="SELECT count(S.id) from Student S LEFT JOIN  S.institutionId I  WHERE I.id = :institutionId"

			
			),
			
			@NamedQuery
			(
				name = "getStudent",
				query="SELECT a FROM Student a WHERE TRIM(FROM a.reg_number) = :reg_number"
			
			),
			
			@NamedQuery
			(
				name = "getStudentByPhone",
				query="SELECT a FROM Student a WHERE TRIM(FROM a.phone) = :phone"
			
			),
			@NamedQuery
			(
				name = "getStudentByNID",
				query="SELECT a FROM Student a WHERE TRIM(FROM a.nida) = :nida"
			
			),
			
			@NamedQuery
			(
				name = "getExpiredInvoices",
				query="SELECT a FROM Student a  inner join a.institutionId I inner join a.facultyId f WHERE  a.invoiceExpiryDate < CAST(CURRENT_DATE as DATE) AND a.isValidStudentRecord = :isValidStudentRecord"
			
			),
			
			@NamedQuery
			(
				name = "deleteInvoice",
				query="DELETE FROM Student a  WHERE a.id = :studentID"
			
			)
			
			
			
			
		})




@XmlRootElement
@Entity
@Table(name="student")
public class Student {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="first_name")
	private String first_name;
	
	@Column(name="last_name")
	private String last_name;

	@Column(name="reg_number")
	private String reg_number;
	
	@Column(name="nida")
	private String nida;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="dob")
	private String dob;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="std_class")
	private String studentClass;
	
	@Column(name="invoice_expiry_date")
	private java.sql.Date invoiceExpiryDate;
	
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@ManyToOne
	@JoinColumn(name="faculty_id")
	private Faculty facultyId;
	
	@ManyToOne
	@JoinColumn(name="institution_calender_id")
	private InstitutionCalender institutionCalender;
	
	@ManyToOne
	@JoinColumn(name="degree_program_id")
	private DegreeProgram degreeProgramId;
	
	@ManyToOne
	@JoinColumn(name="study_program_type_id")
	private StudyProgramType studyProgramType;
	
	@ManyToOne
	@JoinColumn(name="academic_program_id")
	private AcademicProgram AcademicProgram;
	
	@Column(name="std_status")
	private String studentStatus;
	
	@Column(name="applicant_status")
	private String applicantStatus;
	
	@Column(name="is_valid_student_record")
	private String isValidStudentRecord;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getReg_number() {
		return reg_number;
	}

	public void setReg_number(String reg_number) {
		this.reg_number = reg_number;
	}

	public String getNida() {
		return nida;
	}

	public void setNida(String nida) {
		this.nida = nida;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Institution getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Institution institutionId) {
		this.institutionId = institutionId;
	}

	public Faculty getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Faculty facultyId) {
		this.facultyId = facultyId;
	}

	public InstitutionCalender getInstitutionCalender() {
		return institutionCalender;
	}

	public void setInstitutionCalender(InstitutionCalender institutionCalender) {
		this.institutionCalender = institutionCalender;
	}



	public StudyProgramType getStudyProgramType() {
		return studyProgramType;
	}

	public void setStudyProgramType(StudyProgramType studyProgramType) {
		this.studyProgramType = studyProgramType;
	}

	public AcademicProgram getAcademicProgram() {
		return AcademicProgram;
	}

	public void setAcademicProgram(AcademicProgram academicProgram) {
		AcademicProgram = academicProgram;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getApplicantStatus() {
		return applicantStatus;
	}

	public void setApplicantStatus(String applicantStatus) {
		this.applicantStatus = applicantStatus;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public DegreeProgram getDegreeProgramId() {
		return degreeProgramId;
	}

	public void setDegreeProgramId(DegreeProgram degreeProgramId) {
		this.degreeProgramId = degreeProgramId;
	}

	

	

	public String getIsValidStudentRecord() {
		return isValidStudentRecord;
	}

	public void setIsValidStudentRecord(String isValidStudentRecord) {
		this.isValidStudentRecord = isValidStudentRecord;
	}

	public java.sql.Date getInvoiceExpiryDate() {
		return invoiceExpiryDate;
	}

	public void setInvoiceExpiryDate(java.sql.Date invoiceExpiryDate) {
		this.invoiceExpiryDate = invoiceExpiryDate;
	}

	
	
	
	
	
	
	

}
