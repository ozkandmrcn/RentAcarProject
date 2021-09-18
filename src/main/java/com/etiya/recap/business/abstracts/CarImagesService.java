package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CarImages;
import com.etiya.recap.entities.requests.CreateCarImagesRequest;

public interface CarImagesService {
	
	DataResult<List<CarImages>> getAll();

	Result add(CreateCarImagesRequest createCarImagesRequest);
	
	DataResult<CarImages> getById(int id);
	
	Result delete(CreateCarImagesRequest createCarImagesRequest);
	
	Result update(CreateCarImagesRequest createCarImagesRequest);
	
	
}
