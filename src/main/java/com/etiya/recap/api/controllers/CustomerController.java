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

import com.etiya.recap.business.abstracts.CustomerService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Customer;
import com.etiya.recap.entities.requests.CreateCustomerRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	private CustomerService customerService;

	
	@Autowired
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	
	@PostMapping("/addcustomer")
	public ResponseEntity<?> addCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
		return ResponseEntity.ok(this.customerService.add(createCustomerRequest));
	}

	@GetMapping("/getallcustomers")
	public DataResult<List<Customer>> getAllCustomers() {
		return this.customerService.getAll();
	}
	
	@GetMapping("/getcustomerbyid")
	public  DataResult<Customer> getCustomerById(int id) {
		return this.customerService.getById(id);
	}
	
	
	@DeleteMapping("/removecustomer")
	public Result removeCar(CreateCustomerRequest createCustomerRequest) {
		return this.customerService.delete(createCustomerRequest);
	}
	
	@PostMapping("/updatecustomer")
	public ResponseEntity<?> updateCar(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
		return ResponseEntity.ok(this.customerService.update(createCustomerRequest));
	}
	
	
	

}
