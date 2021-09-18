package com.etiya.recap.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.recap.entities.concretes.Customer;

public interface CustomerDao  extends JpaRepository<Customer, Integer> {
	
	@Query("Select c.findeksScore FROM Customer c Where c.customerId=:customerId")
	int getFindeksScoreByCustomerId(int customerId);

}
