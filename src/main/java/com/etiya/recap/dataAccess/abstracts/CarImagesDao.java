package com.etiya.recap.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recap.entities.concretes.CarImages;

public interface CarImagesDao extends JpaRepository<CarImages, Integer> {
	
	int countByCar_id(int carId);
	
	
}
