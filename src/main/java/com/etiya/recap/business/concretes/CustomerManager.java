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
import com.etiya.recap.entities.concretes.Customer;
import com.etiya.recap.entities.concretes.User;
import com.etiya.recap.entities.requests.CreateCustomerRequest;
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
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(),  Messages.GetAll);
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) {
		
		User user=new User();
		user.setUserId(createCustomerRequest.getUserId());
		
		
		Customer customer=new Customer();
		
		customer.setCompanyName(createCustomerRequest.getCompanyName());
		customer.setUser(user);
		
		
		
		this.customerDao.save(customer);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<Customer> getById(int id) {
		
		return new SuccessDataResult<Customer>(this.customerDao.getById(id),  Messages.GetById);
		
	}

	@Override
	public Result delete(CreateCustomerRequest createCustomerRequest) {
		
		Customer customer=new Customer();
		customer.setCustomerId(createCustomerRequest.getId());
		
		
		this.customerDao.delete(customer);
		return new SuccessResult(true,  Messages.Delete);
	}

	@Override
	public Result update(CreateCustomerRequest createCustomerRequest) {
		
		User user=new User();
		user.setUserId(createCustomerRequest.getUserId());
		
		
		Customer customer=new Customer();
		
		customer.setCustomerId(createCustomerRequest.getId());
		customer.setCompanyName(createCustomerRequest.getCompanyName());
		customer.setUser(user);
		
		
		this.customerDao.save(customer);
		return new SuccessResult(true, Messages.Update);
	}

}
