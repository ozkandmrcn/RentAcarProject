package com.etiya.recap.core.outSourceService;

import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.Customer;

public interface FindeksService {
	
	Result checkFindeksScore(Car car, Customer customer);

}
