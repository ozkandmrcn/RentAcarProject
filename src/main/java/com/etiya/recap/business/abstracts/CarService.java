package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.dtos.CarDetailWithCarImgDto;
import com.etiya.recap.entities.requests.create.CreateCarRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarRequest;
import com.etiya.recap.entities.requests.update.UpdateCarRequest;


public interface CarService {

	DataResult<List<Car>> getAll();
	
	DataResult<Car> getById(int id);

	Result add(CreateCarRequest createCarRequest);
	
	Result delete(DeleteCarRequest deleteCarRequest);
	
	Result update(UpdateCarRequest updateCarRequest);
	
	
	
	DataResult<List<CarDetailWithCarImgDto>> getCarWithCarImgByCarId(int id);
	
	DataResult<List<Car>> getCarByColorId(int colorId);
	
	DataResult<List<Car>> getCarByBrandId(int brandId);
	
	DataResult<List<CarImages>> getCarImagesByCarId(int carId);
	
	DataResult<List<CarDetailDto>> getAllCarsWithDetail();
}
