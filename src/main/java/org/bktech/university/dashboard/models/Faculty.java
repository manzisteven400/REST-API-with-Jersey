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
				name = "getFaculty",
				query="SELECT a FROM  Faculty  a inner join a.institutionId b WHERE lower(TRIM(FROM a.name)) = :facultyName AND b.id = :institutionId"
			
			),
			@NamedQuery
			(
				name = "getFacultyByName",
				query="SELECT a FROM  Faculty  a  WHERE lower(TRIM(FROM a.name)) = :facultyName"
			
			),
			
			@NamedQuery
			(
				name = "deleteFaculty",
				query="DELETE FROM Faculty a  WHERE a.id = :facultyID"
			
			),
			
			@NamedQuery
			(
				name = "getFacultyByID",
				query="SELECT a FROM  Faculty  a  WHERE a.id = :facultyID"
			
			)
			
			
			
		})

@XmlRootElement
@Entity
@Table(name="faculty")
public class Faculty {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="accronym")
    private String accronym;
	
	@Column(name="code")

	private String code;

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

	public Institution getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Institution institutionId) {
		this.institutionId = institutionId;
	}

	public String getAccronym() {
		return accronym;
	}

	public void setAccronym(String accronym) {
		this.accronym = accronym;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
