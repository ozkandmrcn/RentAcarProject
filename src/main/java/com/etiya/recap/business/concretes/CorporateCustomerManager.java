package com.etiya.recap.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CorporateCustomerService;
import com.etiya.recap.business.constants.messages.CorporateCustomerMessages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.dtos.CorporateCustomerDto;
import com.etiya.recap.entities.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.etiya.recap.entities.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.etiya.recap.entities.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{
	
	private CorporateCustomerDao corporateCustomerDao;
	private  ModelMapper modelMapper;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao,ModelMapper modelMapper) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapper = modelMapper;
	}
	@Override
	public DataResult<List<CorporateCustomerDto>> getAll() {
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
		List<CorporateCustomerDto> corporateCustomerDtos = corporateCustomers.stream().map(corporate -> modelMapper.map(corporate, CorporateCustomerDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CorporateCustomerDto>>(corporateCustomerDtos, CorporateCustomerMessages.GetAll);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int id) {
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);
		CorporateCustomerDto corporateCustomerDto = modelMapper.map(corporateCustomer, CorporateCustomerDto.class);
		return new SuccessDataResult<CorporateCustomerDto>(corporateCustomerDto, CorporateCustomerMessages.GetById);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(createCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = modelMapper.map(createCorporateCustomerRequest, CorporateCustomer.class);
     	corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(true, CorporateCustomerMessages.Add);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = modelMapper.map(deleteCorporateCustomerRequest, CorporateCustomer.class);
		
		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(true, CorporateCustomerMessages.Delete);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(updateCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = modelMapper.map(updateCorporateCustomerRequest, CorporateCustomer.class);
     	corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(true, CorporateCustomerMessages.Update);
	}

}
