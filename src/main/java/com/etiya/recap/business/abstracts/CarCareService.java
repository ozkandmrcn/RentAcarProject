package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarCare;
import com.etiya.recap.entities.requests.create.CreateCarCareRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarCareRequest;
import com.etiya.recap.entities.requests.update.UpdateCarCareRequest;

public interface CarCareService {
	
	DataResult<List<CarCare>> getAll();

	Result sendCarToCare(CreateCarCareRequest createCarCareRequest);
	
	DataResult<CarCare> getById(int id);
	
	Result delete(DeleteCarCareRequest deleteCarCareRequest);
	
	Result update(UpdateCarCareRequest updateCarCareRequest);

}
