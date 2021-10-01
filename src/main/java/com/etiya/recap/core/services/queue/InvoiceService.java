package com.etiya.recap.core.services.queue;

import org.springframework.stereotype.Service;

import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.concretes.IndividualInvoices;
import com.etiya.recap.entities.concretes.CorporateInvoices;
import com.etiya.recap.entities.dtos.CorporateCustomerInvoiceDto;
import com.etiya.recap.entities.dtos.IndividualCustomerInvoicesDto;

@Service
public interface InvoiceService {

	public CorporateCustomerInvoiceDto invoiceCorporateCustomer(CorporateCustomer corporateCustomer,CorporateInvoices corporateInvoices);

	public IndividualCustomerInvoicesDto individualCustomerInvoicesDto(IndividualCustomer individualCustomer,IndividualInvoices individualInvoices);

}
