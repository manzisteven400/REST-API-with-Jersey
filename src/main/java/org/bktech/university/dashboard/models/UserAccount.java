package org.bktech.university.dashboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries(
		{
			@NamedQuery
			(
				name = "validateUserAccount",
				query="SELECT a FROM UserAccount a inner join a.institutionId b inner join a.profile c WHERE a.email = :email AND a.password = :password"
			
			)
			
			
			
		})


@XmlRootElement
@Entity
@Table(name="user_account")
public class UserAccount {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institutionId;
	
	@Column(name="email")
	@NotBlank(message="email is required")
	private String email;
	
	@Column(name="password")
	@NotBlank(message="password is required")
	private String password;
	
	@OneToOne
	@NotNull(message="user profile id is required")
	@JoinColumn(name="user_profile_id")
	private UserProfile profile;
	
	@Column(name="reg_date")
	@NotBlank(message="registration date is required")
	private Timestamp registrationDate;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	

}
