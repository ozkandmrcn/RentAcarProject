package com.etiya.recap.api.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recap.business.abstracts.CarImagesService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.create.CreateCarImagesRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarImagesRequest;
import com.etiya.recap.entities.requests.update.UpdateCarImagesRequest;

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
	public ResponseEntity<?> addCar(@Valid  CreateCarImagesRequest createCarImagesRequest, MultipartFile file) throws IOException {
		return ResponseEntity.ok(this.carImagesService.add(createCarImagesRequest,file));
	}
	
	@GetMapping("/getallcarimages")
	public  DataResult<List<CarImages>> getAllCarImages() {
		return this.carImagesService.getAll();
	}

	@GetMapping("/getcarimagesbyid")
	public  DataResult<List<CarImages>> getCarById(int id) {
		return this.carImagesService.getById(id);
	}
	
	@DeleteMapping("/removecarimages")
	public Result removeCar(DeleteCarImagesRequest deleteCarImagesRequest) {
		return this.carImagesService.delete(deleteCarImagesRequest);
	}
	
	@PostMapping("/updatecarimages")
	public ResponseEntity<?> updateCar(@Valid @RequestBody UpdateCarImagesRequest updateCarImagesRequest) {
		return ResponseEntity.ok(this.carImagesService.update(updateCarImagesRequest));
	}
}
