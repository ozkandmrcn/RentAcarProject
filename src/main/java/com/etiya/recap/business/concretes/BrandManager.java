package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.etiya.recap.business.abstracts.BrandService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.BrandDao;
import com.etiya.recap.entities.concretes.Brand;
import com.etiya.recap.entities.requests.CreateBrandRequest;

@Service
public class BrandManager implements BrandService{

	
	private BrandDao brandDao;
	
	@Autowired
	public BrandManager(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), Messages.GetAll) ;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
	
	
		
		Brand brand=new Brand();
		brand.setBrandName(createBrandRequest.getBrandName());
		
		this.brandDao.save(brand);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<Brand> getById(int id) {
		return new SuccessDataResult<Brand>(this.brandDao.getById(id), Messages.GetById) ;
	}

	@Override
	public Result delete(CreateBrandRequest createBrandRequest) {
		
		Brand brand=new Brand();
		brand.setBrandId(createBrandRequest.getId());
		
		
		this.brandDao.delete(brand);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(CreateBrandRequest createBrandRequest) {
		
		Brand brand=new Brand();
		brand.setBrandId(createBrandRequest.getId());
		brand.setBrandName(createBrandRequest.getBrandName());
		
		this.brandDao.save(brand);
		return new SuccessResult(true, Messages.Update);
		
	}

}
