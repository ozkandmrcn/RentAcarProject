package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarDamageInformation;
import com.etiya.recap.entities.requests.create.CreateCarDamageInfoRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarDamageInfoRequest;
import com.etiya.recap.entities.requests.update.UpdateCarDamageInformationRequest;


public interface CarDamageInformationService {
	
	DataResult<List<CarDamageInformation>> getAll();
	    
	DataResult<CarDamageInformation> getById(int id);
	
	DataResult<List<CarDamageInformation>> getCarDamageInfoByCarId(int carId);
		
	Result add(CreateCarDamageInfoRequest createCarDamageInfoRequest);
		
	Result delete(DeleteCarDamageInfoRequest deleteCarDamageInfoRequest);
		
	Result update(UpdateCarDamageInformationRequest updateCarDamageInformationRequest);

}
