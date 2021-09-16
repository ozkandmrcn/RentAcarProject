package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.RentalService;
import com.etiya.recap.core.utilities.business.BusinessRentalRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.entities.concretes.Rental;
@Service
public class RentalManager implements RentalService {

	 private RentalDao rentalDao;
	
	@Autowired
	public RentalManager(RentalDao rentalDao) {
		super();
		this.rentalDao = rentalDao;
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), "Araba kiralama işlemleri listelendi");
	}

	@Override
	public Result add(Rental rental) {
		
		var result = BusinessRentalRules.run(checkCarIsReturned(rental.getCar().getId()));

		if (result != null) {
		return result;
	}
		
		this.rentalDao.save(rental);
		return new SuccessResult(true, " Yeni kiralama işlemi başarılı bir şekilde oluşturuldu.");
	}

	@Override
	public DataResult<Rental> getById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.getById(id), "Araba kiralama işlemi listelendi");
	}

	@Override
	public Result delete(Rental rental) {
		this.rentalDao.delete(rental);
		return new SuccessResult(true, " Kiralama işlemi silindi.");
	}

	@Override
	public Result update(Rental rental) {
		this.rentalDao.save(rental);
		return new SuccessResult(true, " Kiralama işlemi güncellendi.");
	}
	
	
	private Result checkCarIsReturned(int carId) {
		List<Rental> rentals = this.rentalDao.getByCar_id(carId);
		if (rentals != null) {
			for (Rental rental : this.rentalDao.getByCar_id(carId)) {
				if (rental.getReturnDate() == null) {
					return new ErrorResult("Malesef kiralama yapılamaz. Araç şuan müşteride");
				}
			}
		}
		return new SuccessResult();
	}

}
