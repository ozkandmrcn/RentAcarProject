package com.etiya.recap.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recap.business.abstracts.CreditCardService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CreditCard;
import com.etiya.recap.entities.requests.create.CreateCreditCardRequest;
import com.etiya.recap.entities.requests.delete.DeleteCreditCardRequest;
import com.etiya.recap.entities.requests.update.UpdateCreditCardRequest;

@RestController
@RequestMapping("/api/creditcards")
public class CreditCardController {
	
	private CreditCardService creditCardService;

	
	@Autowired
	public CreditCardController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}
	
	@PostMapping("/addcreditcard")
	public Result addBrand(@Valid @RequestBody CreateCreditCardRequest createCreditCardRequest){
		return this.creditCardService.add(createCreditCardRequest) ;
	}
	
	@GetMapping("/getallcreditcards")
	public DataResult<List<CreditCard>> getAllCreditCards(){
		return this.creditCardService.getAll();
	}
	
	@GetMapping("/getcreditcardbyid")
	public DataResult<CreditCard> getCreditCardById(int id) {
		return this.creditCardService.getById(id);
	}
	
	@DeleteMapping("/removecreditcard")
	public Result removeCreditCard(DeleteCreditCardRequest deleteCreditCardRequest) {
		return this.creditCardService.delete(deleteCreditCardRequest);
	}
	
	@PostMapping("/updatecreditcard")
	public Result updateCreditCard(@Valid @RequestBody UpdateCreditCardRequest upCreditCardRequest) {
		return this.creditCardService.update(upCreditCardRequest);
	}
	
	

}
