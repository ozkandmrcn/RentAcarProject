package com.etiya.recap.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.IndividualInvoicesService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.services.queue.InvoiceService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.dataAccess.abstracts.IndividualInvoicesDao;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.concretes.IndividualInvoices;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.dtos.IndividualCustomerInvoicesDto;
import com.etiya.recap.entities.requests.InvoiceBetweenDateRequest;
import com.etiya.recap.entities.requests.create.CreateInvoicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteInvoicesRequest;
import com.etiya.recap.entities.requests.update.UpdateInvoicesRequest;

@Service
public class IndividualInvoicesManager implements IndividualInvoicesService {

	private IndividualInvoicesDao individualInvoicesDao;
	private RentalDao rentalDao;
	private IndividualCustomerDao individualCustomerDao;
	private InvoiceService invoiceService;


	@Autowired
	public IndividualInvoicesManager( RentalDao rentalDao,
			IndividualCustomerDao individualCustomerDao,IndividualInvoicesDao individualInvoicesDao,InvoiceService invoiceService) {
		this.rentalDao = rentalDao;
		this.individualCustomerDao = individualCustomerDao;
		this.individualInvoicesDao=individualInvoicesDao;
		this.invoiceService=invoiceService;
	}

	@Override
	public DataResult<List<IndividualInvoices>> getAll() {
		return new SuccessDataResult<List<IndividualInvoices>>(this.individualInvoicesDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateInvoicesRequest createInvoicesRequest) {
		Rental rental = this.rentalDao.getById(createInvoicesRequest.getRentalId());
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(rental.getUser().getUserId());
		
		IndividualCustomer individualCustomer=this.individualCustomerDao.getIndividualCustomerByUser(applicationUser);
		
		long totalRentDateCount = ChronoUnit.DAYS.between(rental.getRentDate().toInstant(),rental.getReturnDate().toInstant());
		double rentPrice = (rental.getCar().getDailyPrice() * totalRentDateCount);

		//başlangıç şehrine teslim edilmediyse 500 tl fark alınır.
		if(rental.getReturnCity().equals(rental.getRentalStartingCity())==false)
		{
		   rentPrice+=500;
		}
		
		//Hizmet bedelleri
		for (AdditionalServices additionalService : rental.getAdditionalServices()) {
			if(additionalService.getAdditionalServiceName().equals("BebekKoltugu")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
			if(additionalService.getAdditionalServiceName().equals("Scooter")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
			if(additionalService.getAdditionalServiceName().equals("Insurance")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
		}
		
		Random randomInvoiceNumber = new Random();
		IndividualInvoices individualInvoices = new IndividualInvoices();
		individualInvoices.setCreationDate(createInvoicesRequest.getCreationDate());
		individualInvoices.setInvoiceNumber(randomInvoiceNumber.nextInt((9999 - 1111) + 1) + 1111);
		individualInvoices.setRentDateCount(totalRentDateCount);
		individualInvoices.setRentPrice(rentPrice);
		individualInvoices.setRental(this.rentalDao.getById(createInvoicesRequest.getRentalId()));
		individualInvoices.setIndividualCustomer(individualCustomer);

		this.individualInvoicesDao.save(individualInvoices);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<IndividualInvoices> getById(int id) {
		return new SuccessDataResult<IndividualInvoices>(this.individualInvoicesDao.getById(id), Messages.GetById);
	}
	
	@Override
	public DataResult<IndividualCustomerInvoicesDto> getIndividualInvoiceDtoByInvoiceId(int invoiceId) {
		
		IndividualInvoices individualInvoices=this.individualInvoicesDao.getById(invoiceId);
		IndividualCustomer individualCustomer=this.individualCustomerDao.getById(individualInvoices.getIndividualCustomer().getCustomerId());
		
		return new SuccessDataResult<IndividualCustomerInvoicesDto>(this.invoiceService.individualCustomerInvoicesDto(individualCustomer, individualInvoices), "indivudual geldi");
	}

	@Override
	public Result delete(DeleteInvoicesRequest deleteInvoicesRequest) {
		
		IndividualInvoices individualInvoices=new IndividualInvoices();
		individualInvoices.setId(deleteInvoicesRequest.getId());
		
		this.individualInvoicesDao.delete(individualInvoices);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateInvoicesRequest updateInvoicesRequest) {

		Rental rental = this.rentalDao.getById(updateInvoicesRequest.getRentalId());

		long totalRentDateCount = ChronoUnit.DAYS.between(rental.getRentDate().toInstant(),rental.getReturnDate().toInstant());
		double rentPrice = (rental.getCar().getDailyPrice() * totalRentDateCount);

		//başlangıç şehrine teslim edilmediyse 500 tl fark alınır.
		if(rental.getReturnCity().equals(rental.getRentalStartingCity())==false)
		{
			 rentPrice+=500;
		}
				
		//Hizmet bedelleri
		for (AdditionalServices additionalService : rental.getAdditionalServices()) {
		if(additionalService.getAdditionalServiceName().equals("BebekKoltugu")) {
			 rentPrice+=300;
			}
		if(additionalService.getAdditionalServiceName().equals("Scooter")) {
			rentPrice+=200;
			}
		if(additionalService.getAdditionalServiceName().equals("Insurance")) {
			rentPrice+=100;
			}
		}
				
		Random randomInvoiceNumber = new Random();
		
		IndividualInvoices individualInvoices = this.individualInvoicesDao.getById(updateInvoicesRequest.getId());
		individualInvoices.setCreationDate(updateInvoicesRequest.getCreationDate());
		individualInvoices.setInvoiceNumber(randomInvoiceNumber.nextInt((9999 - 1111) + 1) + 1111);
		individualInvoices.setRentDateCount(totalRentDateCount);
		individualInvoices.setRentPrice(rentPrice);
		individualInvoices.setRental(this.rentalDao.getById(updateInvoicesRequest.getRentalId()));

		this.individualInvoicesDao.save(individualInvoices);
		return new SuccessResult(true, Messages.Update);
		
	}

	@Override
	public DataResult<List<IndividualInvoices>> getIndividualInvoicesByCustomerId(int customerId) {
		return new SuccessDataResult<List<IndividualInvoices>>(this.individualInvoicesDao.getIndividualInvoicesByCustomerId(customerId),Messages.GetAll);
	}

	@Override
	public DataResult<List<IndividualInvoices>> getIndividualInvoicesBetweenTwoDate(InvoiceBetweenDateRequest invoiceBetweenDateRequest) {
		return new SuccessDataResult<List<IndividualInvoices>>(this.individualInvoicesDao.getByCreationDateBetween(invoiceBetweenDateRequest.getMinDate(),invoiceBetweenDateRequest.getMaxDate()),Messages.GetAll);
	}
}
