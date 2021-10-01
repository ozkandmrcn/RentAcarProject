package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.IndividualCustomerService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.requests.create.CreateIndividualCustomerRequest;
import com.etiya.recap.entities.requests.delete.DeleteIndividualCustomerRequest;
import com.etiya.recap.entities.requests.update.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao) {
		this.individualCustomerDao = individualCustomerDao;
	}
	@Override
	public DataResult<List<IndividualCustomer>> getAll() {
		return new SuccessDataResult<List<IndividualCustomer>>(this.individualCustomerDao.findAll(), Messages.GetAll);
	}

	@Override
	public DataResult<IndividualCustomer> getById(int id) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.findById(id).get(), Messages.GetById);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(createIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setApplicationUser(applicationUser);
		individualCustomer.setFirstName(createIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(createIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(createIndividualCustomerRequest.getIdentityNumber());

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setCustomerId(deleteIndividualCustomerRequest.getId());
		this.individualCustomerDao.delete(individualCustomer);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(updateIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(updateIndividualCustomerRequest.getId());
		individualCustomer.setApplicationUser(applicationUser);
		individualCustomer.setCustomerId(updateIndividualCustomerRequest.getId());
		individualCustomer.setFirstName(updateIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(updateIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(updateIndividualCustomerRequest.getIdentityNumber());

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(true, Messages.Update);
	}

}


