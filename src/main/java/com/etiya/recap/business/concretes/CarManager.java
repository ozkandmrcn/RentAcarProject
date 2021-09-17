package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CarService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.entities.concretes.Brand;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.requests.CreateCarRequest;


@Service
public class CarManager implements CarService {

	private CarDao carDao;

	@Autowired
	public CarManager(CarDao carDao) {
		this.carDao = carDao;
	}

	@Override
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(),  Messages.GetAll);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Brand brand=new Brand();
		brand.setBrandId(createCarRequest.getBrandId());
		
		Color color=new Color();
		color.setColorId(createCarRequest.getColorId());
		
		
		
		
		Car car=new Car();
		
		car.setModelYear(createCarRequest.getModelYear());
		car.setDailyPrice(createCarRequest.getDailyPrice());
		car.setDescription(createCarRequest.getDescription());
		
		
		car.setBrand(brand);
		car.setColor(color);
		
		
		this.carDao.save(car);
		return new SuccessResult(true,  Messages.Add);

	}

	@Override
	public DataResult<Car> getById(int id) {
		return new SuccessDataResult<Car>(this.carDao.getById(id),  Messages.GetById);
	}

	@Override
	public Result delete(Car car) {
		this.carDao.delete(car);
		return new SuccessResult(true,  Messages.Delete);

	}

	@Override
	public Result update(Car car) {
		this.carDao.save(car);
		return new SuccessResult(true,  Messages.Update);
	}

	@Override
	public DataResult<List<CarDetailDto>> getAllCarsWithDetail() {
		return new SuccessDataResult<List<CarDetailDto>>(this.carDao.getCarWithDetails(), Messages.GetAll);
	}

}
