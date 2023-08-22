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
				name = "getInstitutionCalender",
				query="SELECT a FROM  InstitutionCalender a inner join a.institutionId b WHERE lower(TRIM(FROM a.academicYear)) = :academicYear AND b.id = :institutionId"
			
			),
			
			@NamedQuery
			(
				name = "getInstitutionCalenderByInstitutionId",
				query="SELECT a FROM  InstitutionCalender a inner join a.institutionId b WHERE b.id = :institutionId"
			
			)
			
			
			
		})



@XmlRootElement
@Entity
@Table(name="institution_calender")
public class InstitutionCalender {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="academic_year")
	private String academicYear;

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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	

}
