package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CarCareService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarCareDao;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarCare;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.create.CreateCarCareRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarCareRequest;
import com.etiya.recap.entities.requests.update.UpdateCarCareRequest;

@Service
public class CarCareManager implements CarCareService {

	private CarCareDao carCareDao;
	private RentalDao rentalDao;
	private CarDao carDao;

	@Autowired
	public CarCareManager(CarCareDao carCareDao,RentalDao rentalDao,CarDao carDao) {
		this.carCareDao = carCareDao;
		this.rentalDao = rentalDao;
		this.carDao = carDao;
	}

	@Override
	public DataResult<List<CarCare>> getAll() {
		return new SuccessDataResult<List<CarCare>>(this.carCareDao.findAll(), Messages.GetAll);
	}
	
	@Override
	public DataResult<CarCare> getById(int id) {
		return new SuccessDataResult<CarCare>(this.carCareDao.getById(id), Messages.GetById);
	}

	@Override
	public Result sendCarToCare(CreateCarCareRequest createCarCareRequest) {
		//TODO recap Liste kullanımı doğru mu? sorgu mu atmalıydık?
		//Araç müşteride ise bakıma gönderilemez***********************************
		List<Rental> rental = this.rentalDao.getByCar_id(createCarCareRequest.getCarId());
		for (Rental checkRental : rental) {
			if(checkRental.getReturnDate()==null) {
				return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToGoToCare);
			}
		}
		//**************************************************************************
		
		Car car = this.carDao.getById(createCarCareRequest.getCarId());
		car.setCarIsAvailable(false);
		
		CarCare carCare = new CarCare();
		carCare.setCar(car);
		carCare.setCarCareFinishDate(createCarCareRequest.getCarCareFinishDate());
		carCare.setCarCareStartDate(createCarCareRequest.getCarCareStartDate());
		
		this.carCareDao.save(carCare);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result delete(DeleteCarCareRequest deleteCarCareRequest) {
		CarCare carCare = new CarCare();
		carCare.setId(deleteCarCareRequest.getId());

		this.carCareDao.delete(carCare);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateCarCareRequest updateCarCareRequest) {
		//Araç müşteride ise bakıma gönderilemez.
		List<Rental> rental = this.rentalDao.getByCar_id(updateCarCareRequest.getCarId());
		for (Rental checkRental : rental) {
		if(checkRental.getReturnDate()==null) {
		return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToGoToCare);
			}
		}
		//**************************************************************************
		
		Car car = this.carDao.getById(updateCarCareRequest.getCarId());
		car.setCarIsAvailable(false);
		
		CarCare carCare = this.carCareDao.getById(updateCarCareRequest.getId());
		carCare.setCar(car);
		carCare.setCarCareFinishDate(updateCarCareRequest.getCarCareFinishDate());
		carCare.setCarCareStartDate(updateCarCareRequest.getCarCareStartDate());
		carCare.setId(updateCarCareRequest.getId());
		this.carCareDao.save(carCare);
		return new SuccessResult(true, Messages.Update);
	}

}
