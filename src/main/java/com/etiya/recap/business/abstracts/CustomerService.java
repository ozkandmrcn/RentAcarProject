package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Customer;
import com.etiya.recap.entities.requests.CreateCustomerRequest;

public interface CustomerService {
	
	DataResult<List<Customer>> getAll();

	Result add(CreateCustomerRequest createCustomerRequest);
	
	DataResult<Customer> getById(int id);
	
	Result delete(CreateCustomerRequest createCustomerRequest);
	
	Result update(CreateCustomerRequest createCustomerRequest);

}
