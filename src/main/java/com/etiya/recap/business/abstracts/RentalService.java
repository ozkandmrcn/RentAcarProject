package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.create.CreateRentalRequest;
import com.etiya.recap.entities.requests.delete.DeleteRentalRequest;
import com.etiya.recap.entities.requests.update.UpdateRentalRequest;

public interface RentalService {
	
	DataResult<List<Rental>> getAll();

	Result add(CreateRentalRequest createRentalRequest);
	
	DataResult<Rental> getById(int id);
	
	Result delete(DeleteRentalRequest deleteRentalRequest);
	
	Result update(UpdateRentalRequest updateRentalRequest);

}
