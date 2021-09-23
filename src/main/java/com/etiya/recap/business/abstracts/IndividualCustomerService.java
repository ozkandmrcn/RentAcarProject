package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.requests.create.CreateIndividualCustomerRequest;
import com.etiya.recap.entities.requests.delete.DeleteIndividualCustomerRequest;
import com.etiya.recap.entities.requests.update.UpdateIndividualCustomerRequest;

public interface IndividualCustomerService {
	
	DataResult<List<IndividualCustomer>> getAll();
	
	DataResult<IndividualCustomer> getById(int id);

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
	
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

}
