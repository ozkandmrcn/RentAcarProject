package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.create.CreateDeliverTheCar;
import com.etiya.recap.entities.requests.create.CreateRentalRequest;
import com.etiya.recap.entities.requests.delete.DeleteRentalRequest;
import com.etiya.recap.entities.requests.update.UpdateRentalRequest;

public interface RentalService {
	
	DataResult<List<Rental>> getAll();
	
	DataResult<Rental> getById(int id);

	Result rentCorporateCustomer(CreateRentalRequest createRentalRequest);
	
	Result rentIndividualCustomer(CreateRentalRequest createRentalRequest);
	
	Result delete(DeleteRentalRequest deleteRentalRequest);
	
	Result updateIndividualCustomerRent(UpdateRentalRequest updateRentalRequest);
	
	Result updateCorporateCustomerRent(UpdateRentalRequest updateRentalRequest);
	
	Result deliverCorporateCustomerCar(CreateDeliverTheCar createDeliverTheCar);
	
	Result deliverIndividualCustomerCar(CreateDeliverTheCar createDeliverTheCar);

}
