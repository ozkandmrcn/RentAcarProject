package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.requests.create.CreateAdditionalServicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteAdditionalServicesRequest;
import com.etiya.recap.entities.requests.update.UpdateAdditionalServicesRequest;

public interface AdditionalServicesService {
	
	DataResult<List<AdditionalServices>> getAll();

	Result add(CreateAdditionalServicesRequest createAdditionalServicesRequest);
	
	DataResult<AdditionalServices> getById(int id);
	
	Result delete(DeleteAdditionalServicesRequest deleteAdditionalServicesRequest);
	
	Result update(UpdateAdditionalServicesRequest updateAdditionalServicesRequest);
}


