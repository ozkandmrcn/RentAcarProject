package com.etiya.recap.business.abstracts;

import java.io.IOException;
import java.util.List;


import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.create.CreateCarImagesRequest;
import com.etiya.recap.entities.requests.delete.DeleteCarImagesRequest;
import com.etiya.recap.entities.requests.update.UpdateCarImagesRequest;

public interface CarImagesService {
	
	DataResult<List<CarImages>> getAll();

	Result add(CreateCarImagesRequest createCarImagesRequest) throws IOException;
	
	DataResult<List<CarImages>> getById(int id);
	
	Result delete(DeleteCarImagesRequest deleteCarImagesRequest);
	
	Result update(UpdateCarImagesRequest updateCarImagesRequest) throws IOException;
	
	
}
