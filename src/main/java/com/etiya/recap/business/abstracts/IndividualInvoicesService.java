package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.IndividualInvoices;
import com.etiya.recap.entities.dtos.IndividualCustomerInvoicesDto;
import com.etiya.recap.entities.requests.InvoiceBetweenDateRequest;
import com.etiya.recap.entities.requests.create.CreateInvoicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteInvoicesRequest;
import com.etiya.recap.entities.requests.update.UpdateInvoicesRequest;

public interface IndividualInvoicesService {
	
	DataResult<List<IndividualInvoices>> getAll();
	
	Result add(CreateInvoicesRequest createInvoicesRequest);
	
	DataResult<IndividualInvoices> getById(int id);
	
	Result delete(DeleteInvoicesRequest deleteInvoicesRequest);
	
	Result update(UpdateInvoicesRequest updateInvoicesRequest);

	DataResult<IndividualCustomerInvoicesDto> getIndividualInvoiceDtoByInvoiceId(int invoiceId);
	
	DataResult<List<IndividualInvoices>> getIndividualInvoicesByCustomerId(int customerId);
	
	DataResult<List<IndividualInvoices>>getIndividualInvoicesBetweenTwoDate(InvoiceBetweenDateRequest invoiceBetweenDateRequest);

	
	
	

}
