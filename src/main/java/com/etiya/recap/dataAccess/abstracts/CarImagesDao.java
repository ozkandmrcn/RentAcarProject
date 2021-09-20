package com.etiya.recap.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recap.entities.concretes.CarImages;

public interface CarImagesDao extends JpaRepository<CarImages, Integer> {
	
	int countByCar_id(int carId);
	
	List<CarImages> getByCar_id(int carId);
	
	boolean existsByCar_id(int carId);
	
	
}
