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
				name = "validateThirdPartyCredentials",
				query="SELECT a FROM ThirdPartyAuthentication a WHERE a.username = :username AND a.password = :password"
			
			)
			
			
			
		})

@XmlRootElement
@Entity
@Table(name="third_party_authentication")
public class ThirdPartyAuthentication {
	

	@Id
	@Column(name="third_party_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long thirdPartyId;
	
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;

	public Long getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(Long thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	

}
