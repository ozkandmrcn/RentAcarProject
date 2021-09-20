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
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorDataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarImagesDao;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.create.CreateCarImagesRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarImagesRequest;
import com.etiya.recap.entities.requests.update.UpdateCarImagesRequest;

import java.io.File;
import java.io.FileOutputStream;


@Service
public class CarImagesManager implements CarImagesService {

	private CarImagesDao carImagesDao;

	@Autowired
	public CarImagesManager(CarImagesDao carImagesDao) {
		this.carImagesDao = carImagesDao;
	}

	@Override
	public DataResult<List<CarImages>> getAll() {
		return new SuccessDataResult<List<CarImages>>(this.carImagesDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCarImagesRequest createCarImagesRequest, MultipartFile file) throws IOException {
		var result = BusinessRules.run(checkIfCarHasMoreThanFiveImages(createCarImagesRequest.getCarId(), 5), checkImageIsNull(file),checkImageType(file));
		if (result != null) {
			return result;
		}

		CarImages carImages = new CarImages();
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());

		String imageNameRandom = UUID.randomUUID().toString();

		File myFile = new File("C:\\Users\\ozkan.demircan\\Desktop\\İmg\\" + imageNameRandom + "." + file.getContentType().toString().substring(6));
		myFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(myFile);
		fos.write(file.getBytes());
		fos.close();


		Car car = new Car();
		car.setId(createCarImagesRequest.getCarId());

		carImages.setDate(dateNow);
		carImages.setCar(car);
		carImages.setImagePath(myFile.toString());

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
	public Result update(UpdateCarImagesRequest updateCarImagesRequest) {

		Car car = new Car();
		car.setId(updateCarImagesRequest.getCarId());

		CarImages carImages = new CarImages();
		carImages.setId(updateCarImagesRequest.getId());
		carImages.setDate(updateCarImagesRequest.getDate());

		carImages.setCar(car);

		this.carImagesDao.save(carImages);
		return new SuccessResult(true, Messages.Update);
	}

	private Result checkIfCarHasMoreThanFiveImages(int carId, int limit) {
		if (this.carImagesDao.countByCar_id(carId) >= limit) {
			return new ErrorResult(Messages.ErrorIfCarHasMoreImages);
		}
		return new SuccessResult();
	}
	
	private Result checkImageType(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return new ErrorResult();
		}
		if (file.getContentType().toString().substring(6) != "jpeg"
				&& file.getContentType().toString().substring(6) != "jpg"
				&& file.getContentType().toString().substring(6) != "png") {
			return new ErrorResult("Lütfen jpeg, jpg, png uzantılı resim seçiniz");
		}
		return new SuccessResult();

	}

	private Result checkImageIsNull(MultipartFile file) {
		if (file == null) {
			return new ErrorResult("Herhangi bir resim seçmediniz");
		}
		return new SuccessResult();
	}
	
	
	private DataResult<List<CarImages>> returnCarImageWithDefaultImageIfCarImageIsNull(int carId) {

		if (this.carImagesDao.existsByCar_id(carId)) {
			return new ErrorDataResult<List<CarImages>>(this.carImagesDao.getByCar_id(carId));
		}

		List<CarImages> carImages = new ArrayList<CarImages>();
		CarImages carImage = new CarImages();
		carImage.setImagePath("C:\\Users\\ozkan.demircan\\Desktop\\İmg\\default.jpg");

		carImages.add(carImage);

		return new SuccessDataResult<List<CarImages>>(carImages, "Resimi olmayan Araba Default resim ile listelendi");
	
	
	
	
	}
}
