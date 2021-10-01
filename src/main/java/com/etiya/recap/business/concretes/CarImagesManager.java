package com.etiya.recap.business.concretes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recap.business.abstracts.CarImagesService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.filehelper.FileHelperService;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorDataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.dataAccess.abstracts.CarImagesDao;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.create.CreateCarImagesRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarImagesRequest;
import com.etiya.recap.entities.requests.update.UpdateCarImagesRequest;



@Service
public class CarImagesManager implements CarImagesService {

	private CarImagesDao carImagesDao;
	private CarDao carDao;
	private FileHelperService fileHelperService;

	@Autowired
	public CarImagesManager(CarImagesDao carImagesDao,CarDao carDao,FileHelperService fileHelperService) {
		this.carImagesDao = carImagesDao;
		this.carDao = carDao;
		this.fileHelperService=fileHelperService;
	}

	@Override
	public DataResult<List<CarImages>> getAll() {
		return new SuccessDataResult<List<CarImages>>(this.carImagesDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCarImagesRequest createCarImagesRequest) throws IOException {
		
		var result = BusinessRules.run(checkIfCarHasMoreThanFiveImages(createCarImagesRequest.getCarId(), 5),
				checkImageIsNullOrCheckImageTypeIsWrong(createCarImagesRequest.getFile()));
		if (result != null) {
			return result;
		}

		CarImages carImages = new CarImages();
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());

		String imageNameRandom = UUID.randomUUID().toString();
		fileHelperService.createCarImagePathName(createCarImagesRequest, imageNameRandom);
		

		Car car = this.carDao.getById(createCarImagesRequest.getCarId());

		carImages.setDate(dateNow);
		carImages.setCar(car);
		carImages.setImagePath(imageNameRandom);

		this.carImagesDao.save(carImages);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<List<CarImages>> getById(int id) {
		return new SuccessDataResult<List<CarImages>>(returnCarImageWithDefaultImageIfCarImageIsNull(id).getData(),
				Messages.GetById);
	}

	@Override
	public Result delete(DeleteCarImagesRequest deleteCarImagesRequest) {

		CarImages carImages = new CarImages();
		carImages.setId(deleteCarImagesRequest.getId());

		this.carImagesDao.delete(carImages);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateCarImagesRequest updateCarImagesRequest)throws IOException {
		var result = BusinessRules.run(checkIfCarHasMoreThanFiveImages(updateCarImagesRequest.getCarId(), 5), checkImageIsNullOrCheckImageTypeIsWrong(updateCarImagesRequest.getFile()));
		if (result != null) {
			return result;
		}

		CarImages carImages = new CarImages();
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());

		String imageNameRandom = UUID.randomUUID().toString();

	    fileHelperService.updateCarImagePathName(updateCarImagesRequest, imageNameRandom);


		Car car = this.carDao.getById(updateCarImagesRequest.getCarId());

		carImages.setDate(dateNow);
		carImages.setCar(car);
		carImages.setImagePath(imageNameRandom);
		carImages.setId(updateCarImagesRequest.getId());

		this.carImagesDao.save(carImages);
		return new SuccessResult(true, Messages.Update);
	}

	//Arabanın 5'ten fazla resmi var mı kontrolü
	private Result checkIfCarHasMoreThanFiveImages(int carId, int limit) {
		if (this.carImagesDao.countByCar_id(carId) >= limit) {
			return new ErrorResult(Messages.ErrorIfCarHasMoreImages);
		}
		return new SuccessResult();
	}

	//Arabanın resmi boş mu yada format yanlış mı kontrolü
	private Result checkImageIsNullOrCheckImageTypeIsWrong(MultipartFile file) throws IOException {
		if (file == null) {
			return new ErrorResult(Messages.ErrorCarImageNull);
		}
		if(file.getOriginalFilename().endsWith("jpg")!=true &&(file.getOriginalFilename().endsWith("png")!=true  && file.getOriginalFilename().endsWith("jpeg")!=true))
		{
			return new ErrorResult(Messages.ErrorCarImageType);
		}
          return new SuccessResult();
	}
	
	//Eğer arabanın resmi yoksa default resim atama
	private DataResult<List<CarImages>> returnCarImageWithDefaultImageIfCarImageIsNull(int carId) {

		if (this.carImagesDao.existsByCar_id(carId)) {
			return new ErrorDataResult<List<CarImages>>(this.carImagesDao.getByCar_id(carId));
		}

		List<CarImages> carImages = new ArrayList<CarImages>();
		CarImages carImage = new CarImages();
		carImage.setImagePath("C:\\Users\\samet.cavur\\Desktop\\İmg\\default.jpg");

		carImages.add(carImage);

		return new SuccessDataResult<List<CarImages>>(carImages, Messages.GetAll);
	}
}
