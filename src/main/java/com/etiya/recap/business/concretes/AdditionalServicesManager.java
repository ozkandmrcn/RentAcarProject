package com.etiya.recap.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.AdditionalServicesService;
import com.etiya.recap.business.constants.messages.AdditionalServiceMessages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.AdditionalServicesDao;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.dtos.AdditionalServicesDto;
import com.etiya.recap.entities.requests.addionalServiceRequests.CreateAdditionalServicesRequest;
import com.etiya.recap.entities.requests.addionalServiceRequests.DeleteAdditionalServicesRequest;
import com.etiya.recap.entities.requests.addionalServiceRequests.UpdateAdditionalServicesRequest;

@Service
public class AdditionalServicesManager implements AdditionalServicesService {
	
	private AdditionalServicesDao additionalServicesDao;
	private  ModelMapper modelMapper;
	
	@Autowired
	public AdditionalServicesManager(AdditionalServicesDao additionalServicesDao,ModelMapper modelMapper) {
		this.additionalServicesDao = additionalServicesDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<AdditionalServicesDto>> getAll() {
		List<AdditionalServices> additionalServices = this.additionalServicesDao.findAll();
		List<AdditionalServicesDto> additionalServicesDtos = additionalServices.stream().map(additionalServ -> modelMapper.map(additionalServ, AdditionalServicesDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<AdditionalServicesDto>>(additionalServicesDtos,AdditionalServiceMessages.GetAll);
	}

	@Override
	public Result add(CreateAdditionalServicesRequest createAdditionalServicesRequest) {
		
		var result = BusinessRules.run(checkAddionalServiceNameDuplication(createAdditionalServicesRequest.getAdditionalServiceName()));
		if (result != null) {
			return result;
		}
		
		AdditionalServices additionalServices = modelMapper.map(createAdditionalServicesRequest, AdditionalServices.class);
		
		this.additionalServicesDao.save(additionalServices);
		return new SuccessResult(true,AdditionalServiceMessages.Add);
		
	}

	@Override
	public DataResult<AdditionalServicesDto> getById(int id) {
		AdditionalServices additionalServices = this.additionalServicesDao.getById(id);
		AdditionalServicesDto additionalServicesDto = modelMapper.map(additionalServices, AdditionalServicesDto.class);
		return new SuccessDataResult<AdditionalServicesDto>(additionalServicesDto, AdditionalServiceMessages.GetById);
	}

	@Override
	public Result delete(DeleteAdditionalServicesRequest deleteAdditionalServicesRequest) {
		
		AdditionalServices additionalServices=modelMapper.map(deleteAdditionalServicesRequest,AdditionalServices.class);
		
		this.additionalServicesDao.delete(additionalServices);
		return new SuccessResult(true,AdditionalServiceMessages.Delete);
	}

	@Override
	public Result update(UpdateAdditionalServicesRequest updateAdditionalServicesRequest) {
		
		var result = BusinessRules.run(checkAddionalServiceNameDuplication(updateAdditionalServicesRequest.getAdditionalServiceName()));
		if (result != null) {
			return result;
		}
		
		AdditionalServices additionalService = modelMapper.map(updateAdditionalServicesRequest,AdditionalServices.class);
		
		this.additionalServicesDao.save(additionalService);
		return new SuccessResult(true, AdditionalServiceMessages.Update);
	}
	
	
	//Ayn?? isimde hizmet var m?? yok mu kontrol??
	private Result checkAddionalServiceNameDuplication(String additionalServiceName) {
		
		if (this.additionalServicesDao.existsAdditionalServicesByadditionalServiceName(additionalServiceName)) {
			return new ErrorResult(AdditionalServiceMessages.ErrorCheckAdditionalServiceName);
		}
		return new SuccessResult();
	}

}
