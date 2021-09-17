package com.etiya.recap.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recap.business.abstracts.CarImagesService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.CreateCarImagesRequest;

@RestController
@RequestMapping("/api/carimages")
public class CarImagesController {
	
	private CarImagesService carImagesService;
 
	@Autowired
	public CarImagesController(CarImagesService carImagesService) {
		super();
		this.carImagesService = carImagesService;
	}
	
	@PostMapping("/addcarimages")
	public ResponseEntity<?> addCar(@Valid @RequestBody CreateCarImagesRequest createCarImagesRequest) {
		return ResponseEntity.ok(this.carImagesService.add(createCarImagesRequest));
	}
	
	

	@GetMapping("/getallcarsimages")
	public DataResult<List<CarImages>> getAllCars() {
		return this.carImagesService.getAll();
	}
	
	@GetMapping("/getcarimagesbyid")
	public  DataResult<CarImages> getCarById(int id) {
		return this.carImagesService.getById(id);
	}
	
	
	@DeleteMapping("/removecarimages")
	public Result removeCar(CreateCarImagesRequest createCarImagesRequest) {
		return this.carImagesService.delete(createCarImagesRequest);
	}
	
	@PostMapping("/updatecarimages")
	public ResponseEntity<?> updateCar(@Valid @RequestBody CreateCarImagesRequest createCarImagesRequest) {
		return ResponseEntity.ok(this.carImagesService.update(createCarImagesRequest));
	}
	
	@GetMapping("/getallcarbyid")
	public DataResult<List<CarImages>> getallcarById(int carId) {
		return this.carImagesService.getImagesByCarId(carId);
	}
	

}
