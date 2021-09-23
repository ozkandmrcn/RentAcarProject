package com.etiya.recap.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiya.recap.entities.concretes.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {
	
	List<Rental> getByCar_id(int carId);

}
