package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CarImagesService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRentalRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarImagesDao;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.CreateCarImagesRequest;

@Service
public class CarImagesManager implements CarImagesService {
	
	
	private CarImagesDao carImagesDao;
	
    @Autowired
	public CarImagesManager(CarImagesDao carImagesDao) {
		super();
		this.carImagesDao = carImagesDao;
	}

	@Override
	public DataResult<List<CarImages>> getAll() {
		return  new SuccessDataResult<List<CarImages>>(this.carImagesDao.findAll(),Messages.GetAll);
	}

	@Override
	public Result add(CreateCarImagesRequest createCarImagesRequest) {
		
		Car car=new Car();
		car.setId(createCarImagesRequest.getCarId());
		
		
		CarImages carImages=new CarImages();
		carImages.setImagePath(createCarImagesRequest.getImagePath());
		carImages.setDate(createCarImagesRequest.getDate());
		carImages.setCar(car);
		
		var result=BusinessRentalRules.run(checkCarImagesCount(createCarImagesRequest.getCarId(),5));
				
		
		if(result!=null)
		{
			return result;
		}
		
		this.carImagesDao.save(carImages);
		return new SuccessResult(true,  Messages.Add);
	}

	@Override
	public DataResult<CarImages> getById(int id) {
		
		return new SuccessDataResult<CarImages>(this.carImagesDao.getById(id),  Messages.GetById);
	}

	@Override
	public Result delete(CreateCarImagesRequest createCarImagesRequest) {
		
		CarImages carImages=new CarImages();
		carImages.setId(createCarImagesRequest.getId());
		
		
		this.carImagesDao.delete(carImages);
		return new SuccessResult(true,  Messages.Delete);
	}

	@Override
	public Result update(CreateCarImagesRequest createCarImagesRequest) {
		Car car=new Car();
		car.setId(createCarImagesRequest.getCarId());
		
		
		CarImages carImages=new CarImages();
		carImages.setId(createCarImagesRequest.getId());
		carImages.setImagePath(createCarImagesRequest.getImagePath());
		carImages.setDate(createCarImagesRequest.getDate());
		carImages.setCar(car);
		
		this.carImagesDao.save(carImages);
		return new SuccessResult(true,  Messages.Update);
	}
	
	private Result checkCarImagesCount(int carId,int limit)
	{
		 
		  if(this.carImagesDao.countByCar_Id(carId)>=limit)
		  {
			  return new ErrorResult (Messages.ErrorIfCarHasMoreImages);
		  }
		  
		  return new SuccessResult();
	}

	@Override
	public DataResult<List<CarImages>> getImagesByCarId(int carId) {
		
		return  new SuccessDataResult<List<CarImages>>(this.carImagesDao.getCarImagesByCarId(carId),Messages.GetAll);
	}

}
