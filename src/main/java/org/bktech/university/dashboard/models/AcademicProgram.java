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
				name = "getAcademicProgram",
				query="SELECT a FROM  AcademicProgram  a  WHERE lower(TRIM(FROM a.name)) LIKE :academicProgramName"
			
			)
			
			
			
		})

@XmlRootElement
@Entity
@Table(name="academic_program")
public class AcademicProgram {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="name")
	private String name;

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
	
	
	
	
 

}
