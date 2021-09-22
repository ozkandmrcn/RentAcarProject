package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CustomerService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CustomerDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.Customer;
import com.etiya.recap.entities.requests.create.CreateCustomerRequest;
import com.etiya.recap.entities.requests.delete.DeleteCustomerRequest;
import com.etiya.recap.entities.requests.update.UpdateCustomerRequest;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;

	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}

	@Override
	public DataResult<List<Customer>> getAll() {
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) {

		
		

		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(createCustomerRequest.getUserId());


		Customer customer = new Customer();

		customer.setCompanyName(createCustomerRequest.getCompanyName());
		customer.setFindeksScore(createCustomerRequest.getFindeksScore());
		customer.setApplicationUser(applicationUser);

		this.customerDao.save(customer);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<Customer> getById(int id) {

		return new SuccessDataResult<Customer>(this.customerDao.getById(id), Messages.GetById);

	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) {

		Customer customer = new Customer();
		customer.setCustomerId(deleteCustomerRequest.getId());

		this.customerDao.delete(customer);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateCustomerRequest updateCustomerRequest) {

		
		
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(updateCustomerRequest.getUserId());

		Customer customer = new Customer();

		customer.setCustomerId(updateCustomerRequest.getId());
		customer.setCompanyName(updateCustomerRequest.getCompanyName());
		customer.setFindeksScore(updateCustomerRequest.getFindeksScore());
		customer.setApplicationUser(applicationUser);

		this.customerDao.save(customer);
		return new SuccessResult(true, Messages.Update);
	}

}
