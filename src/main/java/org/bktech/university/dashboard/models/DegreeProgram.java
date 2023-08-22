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
			@NamedQuery
			(
				name = "getDegreeProgram",
				query="SELECT a FROM  DegreeProgram  a inner join a.institutionId b WHERE lower(TRIM(FROM a.degreeName)) = :degreeName AND b.id = :institutionId"
			
			),
			@NamedQuery
			(
				name = "getDegreeProgramByName",
				query="SELECT a FROM  DegreeProgram  a  WHERE lower(TRIM(FROM a.degreeName)) = :degreeName"
			
			)
			
			
			
		})


@XmlRootElement
@Entity
@Table(name="degree_program")
public class DegreeProgram {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="degree_name")
	private String degreeName;
	
	@ManyToOne
	@JoinColumn(name="faculty_id")
	private Faculty faculty;
	
	@ManyToOne
	@JoinColumn(name="academic_program_id")
	private AcademicProgram academicProgram;
	
	@Column(name="status")
	private String status;
	
	@Column(name="degree_accronym")
	private String degreeAccronym;
	
	

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

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public AcademicProgram getAcademicProgram() {
		return academicProgram;
	}

	public void setAcademicProgram(AcademicProgram academicProgram) {
		this.academicProgram = academicProgram;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDegreeAccronym() {
		return degreeAccronym;
	}

	public void setDegreeAccronym(String degreeAccronym) {
		this.degreeAccronym = degreeAccronym;
	}
	
	
	

}
