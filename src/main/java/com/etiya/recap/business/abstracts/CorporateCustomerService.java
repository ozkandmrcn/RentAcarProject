package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.requests.create.CreateCorporateCustomerRequest;
import com.etiya.recap.entities.requests.delete.DeleteCorporateCustomerRequest;
import com.etiya.recap.entities.requests.update.UpdateCorporateCustomerRequest;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomer>> getAll();
	
	DataResult<CorporateCustomer> getById(int id);

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

}
