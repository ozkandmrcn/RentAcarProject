package com.etiya.recap.core.outSourceService;

import org.springframework.stereotype.Service;

import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.Customer;
@Service
public class FindeksManager implements FindeksService {

	@Override
	public Result checkFindeksScore(Car car, Customer customer) {
		
		if (customer.getFindeksScore() < car.getFindeksScore()) {
			return new ErrorResult (Messages.ErrorFindeksScore);
		}
		return new SuccessResult();
	}
	
	

}
