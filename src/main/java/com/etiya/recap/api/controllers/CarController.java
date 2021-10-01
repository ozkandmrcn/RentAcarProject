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
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.dtos.CarDetailWithCarImgDto;
import com.etiya.recap.entities.requests.create.CreateCarRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarRequest;
import com.etiya.recap.entities.requests.update.UpdateCarAvailableRequest;
import com.etiya.recap.entities.requests.update.UpdateCarRequest;


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
	
	@GetMapping("/getallcarsincare")
	public DataResult<List<Car>> getAllCarsInCare(){
		return this.carService.getAllCarsInCare();
	}
	
	@GetMapping("/getallcarsarenotincare")
	public DataResult<List<Car>> getAllCarsAreNotInCare(){
		return this.carService.getAllCarsNotInCare();
	}
	
	@GetMapping("/getcarsbycityname")
	public DataResult<List<Car>> getCarsByCityName(String city) {
		return this.carService.getCarsByCityName(city);
	}
	
	@GetMapping("/getcarbycarid")
	public  DataResult<Car> getCarByCarId(int id) {
		return this.carService.getById(id);
	}
	
	@GetMapping("/getallcarswithlessdetail")
	public DataResult<List<CarDetailDto>> getCarsWithLessDetail() {
		return this.carService.getAllCarsWithDetail();
	}
	
	@GetMapping("/getcarimagesbycarid")
	public  DataResult<List<CarImages>> getCarImagesByCarId(int carId) {
		return this.carService.getCarImagesByCarId(carId);
	}
	
	
	@DeleteMapping("/removecar")
	public Result removeCar(DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
	
	@PostMapping("/updatecar")
	public ResponseEntity<?> updateCar(@Valid @RequestBody UpdateCarRequest updateCarRequest) {
		return ResponseEntity.ok(this.carService.update(updateCarRequest));
	}
	
	@PostMapping("/updatecarisavailable")
	public ResponseEntity<?> updateCarIsAvailable(@Valid @RequestBody UpdateCarAvailableRequest updateCarAvailableRequest) {
		return ResponseEntity.ok(this.carService.updateCarAvailable(updateCarAvailableRequest));
	}

	@GetMapping("/getcarbybrandid")
	public  DataResult<List<Car>> getCarByBrandId(int brandId) {
		return this.carService.getCarByBrandId(brandId);
	}
	
	@GetMapping("/getcarbycolorid")
	public  DataResult<List<Car>> getCarByColorId(int colorId) {
		return this.carService.getCarByColorId(colorId);
	}
	
	@GetMapping("/getcarlessdetailwithcarimg")
	public  DataResult<List<CarDetailWithCarImgDto>> getCarLessDetailWithCarImg(int id) {
		return this.carService.getCarWithCarImgByCarId(id);
	}
	
}
