package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CorporateInvoices;
import com.etiya.recap.entities.dtos.CorporateCustomerInvoiceDto;
import com.etiya.recap.entities.requests.InvoiceBetweenDateRequest;
import com.etiya.recap.entities.requests.create.CreateInvoicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteInvoicesRequest;
import com.etiya.recap.entities.requests.update.UpdateInvoicesRequest;

public interface CorporateInvoicesService {
	
	DataResult<List<CorporateInvoices>> getAll();
	
	Result add(CreateInvoicesRequest createInvoicesRequest);
	
	DataResult<CorporateInvoices> getById(int id);
	
	Result delete(DeleteInvoicesRequest deleteInvoicesRequest);
	
	Result update(UpdateInvoicesRequest updateInvoicesRequest);

	DataResult<CorporateCustomerInvoiceDto> getCorporateInvoiceDtoByInvoiceId(int invoiceId);
	
	DataResult <List<CorporateInvoices>> getCorporateInvoicesByCustomerId(int customerId);
	
	DataResult<List<CorporateInvoices>>getCorporateInvoicesBetweenTwoDate(InvoiceBetweenDateRequest invoiceBetweenDateRequest);

	
	

}
