package com.etiya.recap.core.services.queue;

import org.springframework.stereotype.Service;

import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.concretes.IndividualInvoices;
import com.etiya.recap.entities.concretes.CorporateInvoices;
import com.etiya.recap.entities.dtos.CorporateCustomerInvoiceDto;
import com.etiya.recap.entities.dtos.IndividualCustomerInvoicesDto;

@Service
public class InvioceAdapter implements InvoiceService{
	
	public CorporateCustomerInvoiceDto invoiceCorporateCustomer(CorporateCustomer corporateCustomer,CorporateInvoices corporateInvoices) {
		CorporateCustomerInvoiceDto corporateCustomerInvoiceDto = new CorporateCustomerInvoiceDto();
		
		corporateCustomerInvoiceDto.setCompanyName(corporateCustomer.getCompanyName());
		corporateCustomerInvoiceDto.setTaxNumber(corporateCustomer.getTaxNumber());
		corporateCustomerInvoiceDto.setCreationDate(corporateInvoices.getCreationDate());
		corporateCustomerInvoiceDto.setInvoiceNumber(corporateInvoices.getInvoiceNumber());
		corporateCustomerInvoiceDto.setRentDateCount(corporateInvoices.getRentDateCount());
		corporateCustomerInvoiceDto.setRentPrice(corporateInvoices.getRentPrice());
		return corporateCustomerInvoiceDto;
	}

	public IndividualCustomerInvoicesDto individualCustomerInvoicesDto(IndividualCustomer individualCustomer,IndividualInvoices individualInvoices) {
		IndividualCustomerInvoicesDto individualCustomerInvoicesDto = new IndividualCustomerInvoicesDto();
		individualCustomerInvoicesDto.setFirstName(individualCustomer.getFirstName());
		individualCustomerInvoicesDto.setLastName(individualCustomer.getLastName());
		individualCustomerInvoicesDto.setIdentityNumber(individualCustomer.getIdentityNumber());
		individualCustomerInvoicesDto.setInvoiceNumber(individualInvoices.getInvoiceNumber());
		individualCustomerInvoicesDto.setRentDateCount(individualInvoices.getRentDateCount());
		individualCustomerInvoicesDto.setRentPrice(individualInvoices.getRentPrice());
		individualCustomerInvoicesDto.setCreationDate(individualInvoices.getCreationDate());
		
		return individualCustomerInvoicesDto;
	}


}
