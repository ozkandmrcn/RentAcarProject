package com.etiya.recap.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "corporateCustomer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CorporateCustomer{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "taxNumber")
	private int taxNumber;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private ApplicationUser applicationUser;
	

}
