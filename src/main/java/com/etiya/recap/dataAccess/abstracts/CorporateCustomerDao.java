package com.etiya.recap.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiya.recap.entities.concretes.CorporateCustomer;
@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer>{

}
