package com.etiya.recap.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "rentals","creditCards" })
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "findeksScore")
	private int findeksScore;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private ApplicationUser applicationUser;
	
	@OneToMany(mappedBy = "customer")
	private List<Rental> rentals;
	
	@OneToMany(mappedBy = "customer")
	private List<CreditCard> creditCards;

}
