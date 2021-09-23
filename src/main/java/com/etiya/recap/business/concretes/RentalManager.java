package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CustomerFindeksScoreCheckService;
import com.etiya.recap.business.abstracts.RentalService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.create.CreateRentalRequest;
import com.etiya.recap.entities.requests.delete.DeleteRentalRequest;
import com.etiya.recap.entities.requests.update.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CarDao carDao;
	private CustomerFindeksScoreCheckService customerFindeksScoreCheckService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarDao carDao,
			CustomerFindeksScoreCheckService customerFindeksScoreCheckService) {
		super();
		this.rentalDao = rentalDao;
		this.carDao = carDao;
		this.customerFindeksScoreCheckService = customerFindeksScoreCheckService;
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result rentCorporateCustomer(CreateRentalRequest createRentalRequest) {

		Car car = new Car();
		car.setId(createRentalRequest.getCarId());
		car.setFindeksScore(this.carDao.getFindeksScoreByCarId(car.getId()));
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(createRentalRequest.getUserId());
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);

		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkCorporateCustomerFindeksScore(corporateCustomer, car));

		if (result != null) {
			return result;
		}

		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result rentIndividualCustomer(CreateRentalRequest createRentalRequest) {

		Car car = new Car();
		car.setId(createRentalRequest.getCarId());
		car.setFindeksScore(this.carDao.getFindeksScoreByCarId(car.getId()));
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(createRentalRequest.getUserId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);

		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkIndividualCustomerFindeksScore(individualCustomer, car));

		if (result != null) {
			return result;
		}

		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<Rental> getById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.getById(id), Messages.GetById);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {

		Rental rental = new Rental();
		rental.setId(deleteRentalRequest.getId());

		this.rentalDao.delete(rental);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result updateIndividualCustomerRent(UpdateRentalRequest updateRentalRequest) {
		Car car = new Car();
		car.setId(updateRentalRequest.getCarId());
		car.setFindeksScore(this.carDao.getFindeksScoreByCarId(car.getId()));
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(updateRentalRequest.getUserId());
		
		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setId(updateRentalRequest.getId());

		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkIndividualCustomerFindeksScore(individualCustomer, car));

		if (result != null) {
			return result;
		}

		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Update);
	}
	
	@Override
	public Result updateCorporateCustomerRent(UpdateRentalRequest updateRentalRequest) {
		Car car = new Car();
		car.setId(updateRentalRequest.getCarId());
		car.setFindeksScore(this.carDao.getFindeksScoreByCarId(car.getId()));
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(updateRentalRequest.getUserId());
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setId(updateRentalRequest.getId());

		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkCorporateCustomerFindeksScore(corporateCustomer, car));

		if (result != null) {
			return result;
		}

		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Update);
	}

	private Result checkCarIsReturned(int carId) {
		List<Rental> checkRentalReturnDate = this.rentalDao.getByCar_id(carId);
		for (Rental rental2 : checkRentalReturnDate) {
			if (rental2.getReturnDate() == null) {
				return new ErrorResult(Messages.ErrorIfCarIsNotAvailable);
			}
		}
		return new SuccessResult();
	}
	
	
	private Result checkIndividualCustomerFindeksScore(IndividualCustomer individualCustomer,Car car) {
		int individualCustomerFindeksScore = this.customerFindeksScoreCheckService.checkIndividualCustomerFindeksScore(individualCustomer);
		if(car.getFindeksScore()>individualCustomerFindeksScore) {
			return new ErrorResult(Messages.ErrorFindeksScore);
		}
		return new SuccessResult();
			
	}
	
	private Result checkCorporateCustomerFindeksScore(CorporateCustomer corporateCustomer, Car car) {
		int corporateCustomerFindeksScore = this.customerFindeksScoreCheckService.checkCorporateCustomerFindeksScore(corporateCustomer);
		if(car.getFindeksScore()>corporateCustomerFindeksScore) {
			return new ErrorResult(Messages.ErrorFindeksScore);
		}
		return new SuccessResult();
	}
}
