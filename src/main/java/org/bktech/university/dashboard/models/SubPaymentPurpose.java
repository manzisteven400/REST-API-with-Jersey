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
				name = "getSubPaymentPurposebyID",
				query="SELECT a FROM SubPaymentPurpose a WHERE a.id = :subPaymentPurposeID"
			
			)
			
			
			
			
			
		})

@XmlRootElement
@Entity
@Table(name="sub_payment_purpose")
public class SubPaymentPurpose {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="purpose")
	private String purpose;
	
	@Column(name="payment_purpose")
	private Long paymentPurposeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Long getPaymentPurposeId() {
		return paymentPurposeId;
	}

	public void setPaymentPurposeId(Long paymentPurposeId) {
		this.paymentPurposeId = paymentPurposeId;
	}
	
	

}
