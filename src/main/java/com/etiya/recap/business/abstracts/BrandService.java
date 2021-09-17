package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Brand;
import com.etiya.recap.entities.requests.CreateBrandRequest;

public interface BrandService {

	DataResult<List<Brand>> getAll();

	Result add(CreateBrandRequest createBrandRequest);
	
	DataResult<Brand> getById(int id);
	
	Result delete(CreateBrandRequest createBrandRequest);
	
	Result update(CreateBrandRequest createBrandRequest);
}
