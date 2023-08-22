package org.bktech.university.dashboard.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries(
		{
			@NamedQuery
			(
				name = "getToken",
				query="SELECT a FROM AuthToken a WHERE a.authToken = :authToken"
			
			),
			
			@NamedQuery
			(
				name = "deleteToken",
				query="DELETE FROM AuthToken a WHERE CAST(a.expiryTime AS CHAR(10)) = :day"
			
			)
			
			
			
		})


@XmlRootElement
@Entity
@Table(name="auth_token")
public class AuthToken {
	
	@Id
	@Column(name="auth_token_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long authTokenId;
	
	
	@OneToOne
	@NotNull(message="user account id is required")
	@JoinColumn(name="user_account_id")
	private UserAccount userAccount;
	
	@Column(name="auth_token")
	@NotBlank(message="authorization token is required")
	private String authToken;
	
	@Column(name="expiry_time")
	@NotBlank(message="expiry time is required")
	private Timestamp expiryTime;

	public Long getAuthTokenId() {
		return authTokenId;
	}

	public void setAuthTokenId(Long authTokenId) {
		this.authTokenId = authTokenId;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	
	
	

}
