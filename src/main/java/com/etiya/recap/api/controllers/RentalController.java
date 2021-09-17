package com.etiya.recap.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recap.business.abstracts.RentalService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.CreateRentalRequest;


@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	
	
	private RentalService rentalService;
	
	@Autowired

	public RentalController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}
	
	@PostMapping("/addrental")
	public Result addRental(@RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.add(createRentalRequest);
	}

	@GetMapping("/getallrentals")
	public DataResult<List<Rental>> getAllRentals() {
		return this.rentalService.getAll();
	}
	
	@GetMapping("/getrentalbyid")
	public  DataResult<Rental> getRentalById(int id) {
		
		return this.rentalService.getById(id);
	}
	
	
	
	
	
	@DeleteMapping("/removerental")
	public Result removeRental(Rental rental) {
		return this.rentalService.delete(rental);
	}
	
	@PostMapping("/updaterental")
	public Result updateRentAl(@RequestBody Rental rental) {
		return this.rentalService.update(rental);
	}
	

}
