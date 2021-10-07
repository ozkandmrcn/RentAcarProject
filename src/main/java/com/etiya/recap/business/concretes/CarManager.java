package com.etiya.recap.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CarService;
import com.etiya.recap.business.constants.messages.CarMessages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.entities.concretes.Brand;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.dtos.CarDetailDto;
import com.etiya.recap.entities.dtos.CarDetailWithCarImgDto;
import com.etiya.recap.entities.requests.carRequests.CreateCarRequest;
import com.etiya.recap.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.recap.entities.requests.carRequests.UpdateCarAvailableRequest;
import com.etiya.recap.entities.requests.carRequests.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private final ModelMapper modelMapper;

	@Autowired
	public CarManager(CarDao carDao,ModelMapper modelMapper) {
		this.carDao = carDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<CarDetailDto>> getAll() {
		List<Car> carDetails = this.carDao.findAll();
		List<CarDetailDto> carDetailDtos = carDetails.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetAll);
	}
	
	@Override
	public DataResult<List<CarDetailDto>> getCarsByCityId(int cityId) {
		List<Car> carDetails = this.carDao.getByCity_cityId(cityId);
		List<CarDetailDto> carDetailDtos = carDetails.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetCarsByCityId);
	}

	@Override
	public DataResult<List<CarDetailDto>> getAllCarsInCare() {
		List<Car> carDetails = this.carDao.getByCarIsAvailableIsFalse();
		List<CarDetailDto> carDetailDtos = carDetails.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetCarsInCare);
	}

	@Override
	public DataResult<List<CarDetailDto>> getAllCarsNotInCare() {
		List<Car> carDetails = this.carDao.getByCarIsAvailableIsTrue();
		List<CarDetailDto> carDetailDtos = carDetails.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetCarsAreNotInCare);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Car car = modelMapper.map(createCarRequest, Car.class);
		
		this.carDao.save(car);
     	return new SuccessResult(true, CarMessages.Add);
	}

	@Override
	public DataResult<CarDetailDto> getById(int id) {
		Car car = this.carDao.getById(id);
		CarDetailDto carDetailDto = modelMapper.map(car, CarDetailDto.class);
		return new SuccessDataResult<CarDetailDto>(carDetailDto, CarMessages.GetById);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		Car car = modelMapper.map(deleteCarRequest, Car.class);

		this.carDao.delete(car);
		return new SuccessResult(true, CarMessages.Delete);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		
		Brand brand = modelMapper.map(updateCarRequest, Brand.class);
		Color color = modelMapper.map(updateCarRequest, Color.class);

		Car car = modelMapper.map(updateCarRequest, Car.class);
		car.setBrand(brand);
		car.setColor(color);
		
		

		this.carDao.save(car);
		return new SuccessResult(true, CarMessages.Update);
	}
	
	@Override
	public Result updateCarAvailable(UpdateCarAvailableRequest updateCarAvailableRequest) {
		Car car = this.carDao.getById(updateCarAvailableRequest.getId());
		car.setCarIsAvailable(updateCarAvailableRequest.isCarIsAvailable());
		this.carDao.save(car);
		return new SuccessResult(true, CarMessages.ChangeCarSituation);
	}


	@Override
	public DataResult<List<CarDetailDto>> getCarByBrandId(int brandId) {
		List<Car> cars = this.carDao.getByBrand_brandId(brandId);
		List<CarDetailDto> carDetailDtos = cars.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetCarsByBrandId);
	}

	@Override
	public DataResult<List<CarDetailDto>> getCarByColorId(int colorId) {
		List<Car> cars = this.carDao.getByColor_colorId(colorId);
		List<CarDetailDto> carDetailDtos = cars.stream().map(carDetail -> modelMapper.map(carDetail, CarDetailDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, CarMessages.GetCarsByColorId);
	}

	@Override
	public DataResult<List<CarDetailWithCarImgDto>>  getCarWithCarImgByCarId(int id) {
		List<CarDetailWithCarImgDto> cars=this.carDao.getCarWithCarImg(id);
        return new SuccessDataResult<List<CarDetailWithCarImgDto>>(cars, CarMessages.GetCarsWithImages);
	}


	

}
	
