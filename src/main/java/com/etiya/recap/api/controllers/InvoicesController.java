package com.etiya.recap.api.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.etiya.recap.business.abstracts.CorporateInvoicesService;
import com.etiya.recap.business.abstracts.IndividualInvoicesService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CorporateInvoices;
import com.etiya.recap.entities.concretes.IndividualInvoices;
import com.etiya.recap.entities.dtos.CorporateCustomerInvoiceDto;
import com.etiya.recap.entities.dtos.IndividualCustomerInvoicesDto;
import com.etiya.recap.entities.requests.InvoiceBetweenDateRequest;
import com.etiya.recap.entities.requests.delete.DeleteInvoicesRequest;
import com.etiya.recap.entities.requests.update.UpdateInvoicesRequest;

@RestController
@RequestMapping("/api/corpareteinvoices")
public class InvoicesController {
	
	private CorporateInvoicesService corporateInvoicesService;
	private IndividualInvoicesService individualInvoicesService;

	@Autowired
	public InvoicesController(CorporateInvoicesService corporateInvoicesService,IndividualInvoicesService individualInvoicesService) {
		this.corporateInvoicesService = corporateInvoicesService;
		this.individualInvoicesService=individualInvoicesService;
	}

	/*            ***********Araba Kiralanır kiralanmaz fatura kesme işlemi yaptık***********
	@PostMapping("/addcorporatecustomerinvoice")
	public ResponseEntity<?> addCorporateCustomerInvioce(@Valid  @RequestBody CreateInvoicesRequest createInvoicesRequest) {
		return ResponseEntity.ok(this.corporateInvoicesService.add(createInvoicesRequest));
	}
	
	@PostMapping("/addindividualcustomerinvoice")
	public ResponseEntity<?> addIndividualCustomerInvioce(@Valid  @RequestBody CreateInvoicesRequest createInvoicesRequest) {
		return ResponseEntity.ok(this.individualInvoicesService.add(createInvoicesRequest));
	}
	*/
	
	@GetMapping("/getallcorporateinvoices")
	public DataResult<List<CorporateInvoices>> getAllCorporateInvoices(){
		return this.corporateInvoicesService.getAll();
	}
	

	@GetMapping("/getallindividualinvoices")
	public DataResult<List<IndividualInvoices>> getAllIndividualInvoices(){
		return this.individualInvoicesService.getAll();
	}
	
	@GetMapping("/getcorporateinvoicebyinvoiceid")
	public DataResult<CorporateInvoices> getCorporateInvoicesByInvoiceId(int id) {
		return this.corporateInvoicesService.getById(id);
	}
	
	@GetMapping("/getindividualinvoicebyinvoiceid")
	public DataResult<IndividualInvoices> getIndividualInvoicesByInvoiceId(int id) {
		return this.individualInvoicesService.getById(id);
	}
	
	@PostMapping("/updatecorporateinvoice")
	public ResponseEntity<?> updateCorporateInvoice(@Valid @RequestBody UpdateInvoicesRequest updateInvoicesRequest) {
		return ResponseEntity.ok(this.corporateInvoicesService.update(updateInvoicesRequest));
	}
	

	@PostMapping("/updateindividualinvoice")
	public ResponseEntity<?> updateIndividualInvoice(@Valid @RequestBody UpdateInvoicesRequest updateInvoicesRequest) {
		return ResponseEntity.ok(this.individualInvoicesService.update(updateInvoicesRequest));
	}
	
	@DeleteMapping("/removecorporateinvoice")
	public Result removeCorporateInvoice(DeleteInvoicesRequest deleteInvoicesRequest) {
		return this.corporateInvoicesService.delete(deleteInvoicesRequest);
	}
	
	@DeleteMapping("/removeindividualinvoice")
	public Result removeIndividualInvoice(DeleteInvoicesRequest deleteInvoicesRequest) {
		return this.individualInvoicesService.delete(deleteInvoicesRequest);
	}
	
	@GetMapping("/getindividualinvoiceDtobyinvoiceid")
	public DataResult<IndividualCustomerInvoicesDto> getIndividualInvoiceDtoByInvoiceId(int id){
		return this.individualInvoicesService.getIndividualInvoiceDtoByInvoiceId(id);
	}
	
	@GetMapping("/getcorporateinvoiceDtobyinvoiceid")
	public DataResult<CorporateCustomerInvoiceDto> getCorporateInvoiceDtoByInvoiceId(int id){
		return this.corporateInvoicesService.getCorporateInvoiceDtoByInvoiceId(id);
	}
	
	@GetMapping("/getindividualinvoicesbycustomerid")
	public DataResult<List<IndividualInvoices>> getIndividualInvoicesByCustomerId(int customerId)
	{
		return this.individualInvoicesService.getIndividualInvoicesByCustomerId(customerId);
	}
	
	@GetMapping("/getcorporateinvoicesbycustomerid")
	public DataResult<List<CorporateInvoices>> getCorporateInvoicesByCustomerId(int customerId)
	{
		return this.corporateInvoicesService.getCorporateInvoicesByCustomerId(customerId);
	}
	
	@GetMapping("/getindividualinvoicesbetweentwodate")
	public DataResult<List<IndividualInvoices>> getIndividualInvoicesBetweenTwoDate(String minDate, String maxDate) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate1 = dateFormat.parse(minDate);
		Date maxDate1 = dateFormat.parse(maxDate);

		InvoiceBetweenDateRequest invoiceBetweenDateRequest = new InvoiceBetweenDateRequest();
		invoiceBetweenDateRequest.setMinDate(minDate1);
		invoiceBetweenDateRequest.setMaxDate(maxDate1);
		
		return this.individualInvoicesService.getIndividualInvoicesBetweenTwoDate(invoiceBetweenDateRequest);
	}
	
	@GetMapping("/getcorporateinvoicesbetweentwodate")
	public DataResult<List<CorporateInvoices>> getCorporateInvoicesBetweenTwoDate(String minDate, String maxDate) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate1 = dateFormat.parse(minDate);
		Date maxDate1 = dateFormat.parse(maxDate);

		InvoiceBetweenDateRequest invoiceBetweenDateRequest = new InvoiceBetweenDateRequest();
		invoiceBetweenDateRequest.setMinDate(minDate1);
		invoiceBetweenDateRequest.setMaxDate(maxDate1);
		
		return this.corporateInvoicesService.getCorporateInvoicesBetweenTwoDate(invoiceBetweenDateRequest);
	}

}
