package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.AdditionalServicesService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.AdditionalServicesDao;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.requests.create.CreateAdditionalServicesRequest;
import com.etiya.recap.entities.requests.delete.DeleteAdditionalServicesRequest;
import com.etiya.recap.entities.requests.update.UpdateAdditionalServicesRequest;

@Service
public class AdditionalServicesManager implements AdditionalServicesService {
	
	private AdditionalServicesDao additionalServicesDao;
	
	@Autowired
	public AdditionalServicesManager(AdditionalServicesDao additionalServicesDao) {
		this.additionalServicesDao = additionalServicesDao;
	}

	@Override
	public DataResult<List<AdditionalServices>> getAll() {
		
		return new SuccessDataResult<List<AdditionalServices>>(this.additionalServicesDao.findAll(),Messages.GetAll);
	}

	@Override
	public Result add(CreateAdditionalServicesRequest createAdditionalServicesRequest) {
		
		var result = BusinessRules.run(checkAddionalServiceNameDuplication(createAdditionalServicesRequest.getAdditionalService()));
		if (result != null) {
			return result;
		}
		
		AdditionalServices additionalServices=new AdditionalServices();
		additionalServices.setAdditionalServiceName(createAdditionalServicesRequest.getAdditionalService());
		additionalServices.setAdditionalServicePrice(createAdditionalServicesRequest.getAdditionalServicePrice());
		
		this.additionalServicesDao.save(additionalServices);
		return new SuccessResult(true, Messages.Add);
		
	}

	@Override
	public DataResult<AdditionalServices> getById(int id) {
		
		return new SuccessDataResult<AdditionalServices>(this.additionalServicesDao.getById(id), Messages.GetById);
	}

	@Override
	public Result delete(DeleteAdditionalServicesRequest deleteAdditionalServicesRequest) {
		
		AdditionalServices additionalServices=new AdditionalServices();
		additionalServices.setId(deleteAdditionalServicesRequest.getId());
		
		return new SuccessResult(true,Messages.Delete);
	}

	@Override
	public Result update(UpdateAdditionalServicesRequest updateAdditionalServicesRequest) {
		
		AdditionalServices additionalServices=this.additionalServicesDao.getById(updateAdditionalServicesRequest.getId());
		additionalServices.setId(updateAdditionalServicesRequest.getId());
		additionalServices.setAdditionalServiceName(updateAdditionalServicesRequest.getAdditionalService());
        additionalServices.setAdditionalServicePrice(updateAdditionalServicesRequest.getAdditionalServicePrice());
		
		this.additionalServicesDao.save(additionalServices);
		return new SuccessResult(true, Messages.Update);
	}
	
	
	//Aynı isimde hizmet var mı yok mu kontrolü
	private Result checkAddionalServiceNameDuplication(String additionalServiceName) {
		
		if (this.additionalServicesDao.existsAdditionalServicesByadditionalServiceName(additionalServiceName)) {
			return new ErrorResult(Messages.ErrorCheckAdditionalServiceName);
		}
		return new SuccessResult();
	}

}
