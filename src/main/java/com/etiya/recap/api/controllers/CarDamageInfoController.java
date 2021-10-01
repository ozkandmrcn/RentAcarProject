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

import com.etiya.recap.business.abstracts.CarDamageInformationService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarDamageInformation;
import com.etiya.recap.entities.requests.create.CreateCarDamageInfoRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarDamageInfoRequest;
import com.etiya.recap.entities.requests.update.UpdateCarDamageInformationRequest;

@RestController
@RequestMapping("/api/cardamageinfo")
public class CarDamageInfoController {
	
	private CarDamageInformationService carDamageInformationService;
	
	@Autowired
	public CarDamageInfoController(CarDamageInformationService carDamageInformationService) {
		this.carDamageInformationService = carDamageInformationService;
	}

	@PostMapping("/addcardamageinfo")
	public ResponseEntity<?> addCarDamageInfo(@Valid @RequestBody CreateCarDamageInfoRequest createCarDamageInfoRequest){
		return ResponseEntity.ok(this.carDamageInformationService.add(createCarDamageInfoRequest)) ;
	}
	
	@GetMapping("/getallcardamageinfos")
	public DataResult<List<CarDamageInformation>> getAllCarDamageInfos(){
		return this.carDamageInformationService.getAll();
	}
	
	@GetMapping("/getcardamageinfobyid")
	public DataResult<CarDamageInformation> getCarDamageInfoById(int id) {
		return this.carDamageInformationService.getById(id);
	}
	
	@GetMapping("/getcardamageinfobycarid")
	public DataResult<List<CarDamageInformation>> getCarDamageInfoByCarId(int id) {
		return this.carDamageInformationService.getCarDamageInfoByCarId(id);
	}
	
	@DeleteMapping("/removecardamageinfo")
	public Result removeCarDamageInfo(DeleteCarDamageInfoRequest deleteCarDamageInfoRequest) {
		return this.carDamageInformationService.delete(deleteCarDamageInfoRequest);
	}
	
	@PostMapping("/updatecardamageinfo")
	public ResponseEntity<?> updateCarDamageInfo(@Valid @RequestBody UpdateCarDamageInformationRequest updateCarDamageInformationRequest) {
		return ResponseEntity.ok(this.carDamageInformationService.update(updateCarDamageInformationRequest)) ;
	}
}
