package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CarDamageInformationService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarDamageInformationDao;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarDamageInformation;
import com.etiya.recap.entities.requests.create.CreateCarDamageInfoRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarDamageInfoRequest;
import com.etiya.recap.entities.requests.update.UpdateCarDamageInformationRequest;

@Service
public class CarDamageInformationManager implements CarDamageInformationService{

	private CarDamageInformationDao carDamageInformationDao;
	private CarDao carDao;
	
	@Autowired
	public CarDamageInformationManager(CarDamageInformationDao carDamageInformationDao,CarDao carDao) {
		this.carDamageInformationDao = carDamageInformationDao;
		this.carDao = carDao;
	}
	
	@Override
	public DataResult<List<CarDamageInformation>> getAll() {
		return new SuccessDataResult<List<CarDamageInformation>>(this.carDamageInformationDao.findAll(), Messages.GetAll);
	}

	@Override
	public DataResult<CarDamageInformation> getById(int id) {
		return new SuccessDataResult<CarDamageInformation>(this.carDamageInformationDao.getById(id), Messages.GetById);
	}

	@Override
	public Result add(CreateCarDamageInfoRequest createCarDamageInfoRequest) {
		CarDamageInformation carDamageInformation = new CarDamageInformation();
		Car car = this.carDao.getById(createCarDamageInfoRequest.getCarId());
		carDamageInformation.setCar(car);
		carDamageInformation.setDamagePrice(createCarDamageInfoRequest.getDamagePrice());
		carDamageInformation.setDescription(createCarDamageInfoRequest.getDescription());
		
		this.carDamageInformationDao.save(carDamageInformation);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result delete(DeleteCarDamageInfoRequest deleteCarDamageInfoRequest) {
		CarDamageInformation carDamageInformation = new CarDamageInformation();
		carDamageInformation.setId(deleteCarDamageInfoRequest.getId());
		
		this.carDamageInformationDao.delete(carDamageInformation);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateCarDamageInformationRequest updateCarDamageInformationRequest) {
		CarDamageInformation carDamageInformation = this.carDamageInformationDao.getById(updateCarDamageInformationRequest.getId());
		Car car = this.carDao.getById(updateCarDamageInformationRequest.getCarId());
		carDamageInformation.setCar(car);
		carDamageInformation.setDamagePrice(updateCarDamageInformationRequest.getDamagePrice());
		carDamageInformation.setDescription(updateCarDamageInformationRequest.getDescription());
		carDamageInformation.setId(updateCarDamageInformationRequest.getId());
		
		this.carDamageInformationDao.save(carDamageInformation);
		return new SuccessResult(true, Messages.Update);
	}

	@Override
	public DataResult<List<CarDamageInformation>> getCarDamageInfoByCarId(int carId) {
		return new SuccessDataResult<List<CarDamageInformation>>(this.carDamageInformationDao.getByCar_id(carId), Messages.GetById);
	}

}
