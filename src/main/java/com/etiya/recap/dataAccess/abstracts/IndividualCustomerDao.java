package com.etiya.recap.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiya.recap.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
	
	/*
	@Query("Select c.findeksScore FROM Customer c Where c.customerId=:customerId")
	int getFindeksScoreByCustomerId(int customerId);
	*/

}
