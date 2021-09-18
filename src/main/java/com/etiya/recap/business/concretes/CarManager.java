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
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.dtos.CarDetailWithCarImgDto;
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
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Brand brand = new Brand();
		brand.setBrandId(createCarRequest.getBrandId());

		Color color = new Color();
		color.setColorId(createCarRequest.getColorId());

		Car car = new Car();
		car.setModelYear(createCarRequest.getModelYear());
		car.setDailyPrice(createCarRequest.getDailyPrice());
		car.setDescription(createCarRequest.getDescription());
		car.setFindeksScore(createCarRequest.getFindeksScore());
		car.setBrand(brand);
		car.setColor(color);
		
		
		this.carDao.save(car);
     	return new SuccessResult(true, Messages.Add);
		
		
	}

	@Override
	public DataResult<Car> getById(int id) {
		return new SuccessDataResult<Car>(this.carDao.getById(id), Messages.GetById);
	}

	@Override
	public Result delete(CreateCarRequest createCarRequest) {
		Car car = new Car();
		car.setId(createCarRequest.getId());

		this.carDao.delete(car);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(CreateCarRequest createCarRequest) {

		Brand brand = new Brand();
		brand.setBrandId(createCarRequest.getBrandId());

		Color color = new Color();
		color.setColorId(createCarRequest.getColorId());

		Car car = new Car();
		car.setId(createCarRequest.getId());
		car.setModelYear(createCarRequest.getModelYear());
		car.setDailyPrice(createCarRequest.getDailyPrice());
		car.setDescription(createCarRequest.getDescription());
		car.setFindeksScore(createCarRequest.getFindeksScore());

		car.setBrand(brand);
		car.setColor(color);

		this.carDao.save(car);
		return new SuccessResult(true, Messages.Update);
	}

	@Override
	public DataResult<List<CarDetailDto>> getAllCarsWithDetail() {
		return new SuccessDataResult<List<CarDetailDto>>(this.carDao.getCarWithDetails(), Messages.GetAll);
	}

	@Override
	public DataResult<List<CarImages>> getAllCarImages(int carId) {
		return new SuccessDataResult<List<CarImages>>(this.carDao.getCarImagesByCarId(carId), Messages.GetAll);
	}
	

	@Override
	public DataResult<List<Car>> getCarByBrandId(int brandId) {
		List<Car> cars=this.carDao.getByBrand_brandId(brandId);
        return new SuccessDataResult<List<Car>>(cars, Messages.GetAll);
	}

	@Override
	public DataResult<List<Car>> getCarByColorId(int colorId) {
		List<Car> cars=this.carDao.getByColor_colorId(colorId);
        return new SuccessDataResult<List<Car>>(cars, Messages.GetAll);
	}

	@Override
	public DataResult<List<CarDetailWithCarImgDto>>  getCarWithCarImg(int id) {
		List<CarDetailWithCarImgDto> cars=this.carDao.getCarWithCarImg(id);
		
        return new SuccessDataResult<List<CarDetailWithCarImgDto>>(cars, Messages.GetAll);
	}
	
	
	

	
}
	
