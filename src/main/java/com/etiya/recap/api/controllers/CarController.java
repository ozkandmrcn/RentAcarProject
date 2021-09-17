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

import com.etiya.recap.business.abstracts.CarService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.requests.CreateCarRequest;


@RestController
@RequestMapping("/api/cars")
public class CarController {
	
	private CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/addcar")
	public ResponseEntity<?> addCar(@Valid @RequestBody CreateCarRequest createCarRequest) {
		return ResponseEntity.ok(this.carService.add(createCarRequest));
	}
	
	

	@GetMapping("/getallcars")
	public DataResult<List<Car>> getAllCars() {
		return this.carService.getAll();
	}
	
	@GetMapping("/getcarbyid")
	public  DataResult<Car> getCarById(int id) {
		return this.carService.getById(id);
	}
	
	@GetMapping("/getallcarswithdetail")
	public DataResult<List<CarDetailDto>> getCarsWithDetail() {
		return this.carService.getAllCarsWithDetail();
	}
	
	@DeleteMapping("/removecar")
	public Result removeCar(CreateCarRequest createCarRequest) {
		return this.carService.delete(createCarRequest);
	}
	
	@PostMapping("/updatecar")
	public ResponseEntity<?> updateCar(@Valid @RequestBody CreateCarRequest createCarRequest) {
		return ResponseEntity.ok(this.carService.update(createCarRequest));
	}
	
	


}
