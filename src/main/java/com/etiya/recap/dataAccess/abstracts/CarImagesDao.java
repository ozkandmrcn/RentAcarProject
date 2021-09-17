package com.etiya.recap.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.recap.entities.concretes.CarImages;

public interface CarImagesDao extends JpaRepository<CarImages, Integer> {
	
	 int countByCar_Id(int carId);
	 
	 
	  @Query("From CarImages where car_id=:carId ")
	  List<CarImages> getCarImagesByCarId(int carId);

}
