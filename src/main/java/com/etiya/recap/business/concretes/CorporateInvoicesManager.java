package com.etiya.recap.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CorporateInvoicesService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.services.queue.InvoiceService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recap.dataAccess.abstracts.CorporateInvoicesDao;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.concretes.CorporateInvoices;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.dtos.CorporateCustomerInvoiceDto;
import com.etiya.recap.entities.requests.InvoiceBetweenDateRequest;
import com.etiya.recap.entities.requests.create.CreateInvoicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteInvoicesRequest;
import com.etiya.recap.entities.requests.update.UpdateInvoicesRequest;

@Service
public class CorporateInvoicesManager implements CorporateInvoicesService {

	private CorporateInvoicesDao corporateInvoicesDao;
	private RentalDao rentalDao;
	private CorporateCustomerDao corporateCustomerDao;
	private InvoiceService invoiceService;

	@Autowired
	public CorporateInvoicesManager(CorporateInvoicesDao corporateInvoicesDao, RentalDao rentalDao,CorporateCustomerDao corporateCustomerDao,InvoiceService invoiceService) {
		this.corporateInvoicesDao = corporateInvoicesDao;
		this.rentalDao = rentalDao;
		this.corporateCustomerDao = corporateCustomerDao;
		this.invoiceService=invoiceService;
	}

	@Override
	public DataResult<List<CorporateInvoices>> getAll() {
		return new SuccessDataResult<List<CorporateInvoices>>(this.corporateInvoicesDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateInvoicesRequest createInvoicesRequest) {
		Rental rental = this.rentalDao.getById(createInvoicesRequest.getRentalId());		
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(rental.getUser().getUserId());
		
		CorporateCustomer corporateCustomer=this.corporateCustomerDao.getCorporateCustomerByUser(applicationUser);
		
		Random randomInvoiceNumber = new Random();
		
		CorporateInvoices corporateInvoices = new CorporateInvoices();
		corporateInvoices.setCreationDate(createInvoicesRequest.getCreationDate());
		corporateInvoices.setInvoiceNumber(randomInvoiceNumber.nextInt((9999 - 1111) + 1) + 1111);
		corporateInvoices.setRentDateCount(createInvoicesRequest.getTotalRentDateCount());
		corporateInvoices.setRentPrice(createInvoicesRequest.getRentPrice());
		corporateInvoices.setRental(this.rentalDao.getById(createInvoicesRequest.getRentalId()));
		corporateInvoices.setCorporateCustomer(corporateCustomer);

		this.corporateInvoicesDao.save(corporateInvoices);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<CorporateInvoices> getById(int id) {
		return new SuccessDataResult<CorporateInvoices>(this.corporateInvoicesDao.getById(id), Messages.GetById);
	}
	
	@Override
	public DataResult<CorporateCustomerInvoiceDto> getCorporateInvoiceDtoByInvoiceId(int invoiceId) {
		CorporateInvoices corporateInvoices = this.corporateInvoicesDao.getById(invoiceId);
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(corporateInvoices.getCorporateCustomer().getCustomerId());
		
		
		return new SuccessDataResult<CorporateCustomerInvoiceDto>(this.invoiceService.invoiceCorporateCustomer(corporateCustomer, corporateInvoices), "CorporateGeldi");
	}
	

	@Override
	public Result delete(DeleteInvoicesRequest deleteInvoicesRequest) {
		CorporateInvoices corporateInvoices = new CorporateInvoices();
		corporateInvoices.setId(deleteInvoicesRequest.getId());
		this.corporateInvoicesDao.delete(corporateInvoices);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateInvoicesRequest updateInvoicesRequest) {

		Rental rental = this.rentalDao.getById(updateInvoicesRequest.getRentalId());

		long totalRentDateCount = ChronoUnit.DAYS.between(rental.getRentDate().toInstant(),
				rental.getReturnDate().toInstant());
		double rentPrice = (rental.getCar().getDailyPrice() * totalRentDateCount);
		
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

		CorporateInvoices corporateInvoices = this.corporateInvoicesDao.getById(updateInvoicesRequest.getId());
		corporateInvoices.setCreationDate(updateInvoicesRequest.getCreationDate());
		corporateInvoices.setInvoiceNumber(randomInvoiceNumber.nextInt((9999 - 1111) + 1) + 1111);
		corporateInvoices.setRentDateCount(totalRentDateCount);
		corporateInvoices.setRentPrice(rentPrice);
		corporateInvoices.setRental(this.rentalDao.getById(updateInvoicesRequest.getRentalId()));

		this.corporateInvoicesDao.save(corporateInvoices);
		return new SuccessResult(true, Messages.Update);
	}

	@Override
	public DataResult<List<CorporateInvoices>> getCorporateInvoicesByCustomerId(int customerId) {
		return new SuccessDataResult<List<CorporateInvoices>>(this.corporateInvoicesDao.getCorporateInvoicesByCustomerId(customerId),Messages.GetAll);
	}

	@Override
	public DataResult<List<CorporateInvoices>> getCorporateInvoicesBetweenTwoDate(InvoiceBetweenDateRequest invoiceBetweenDateRequest) {
		
		return new SuccessDataResult<List<CorporateInvoices>>(this.corporateInvoicesDao.getByCreationDateBetween(invoiceBetweenDateRequest.getMinDate(),invoiceBetweenDateRequest.getMaxDate()),Messages.GetAll);

	}

}
