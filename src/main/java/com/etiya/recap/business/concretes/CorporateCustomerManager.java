package com.etiya.recap.business.concretes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.etiya.recap.business.abstracts.CorporateCustomerService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.requests.create.CreateCorporateCustomerRequest;
import com.etiya.recap.entities.requests.delete.DeleteCorporateCustomerRequest;
import com.etiya.recap.entities.requests.update.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{
	
	private CorporateCustomerDao corporateCustomerDao;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao) {
		this.corporateCustomerDao = corporateCustomerDao;
	}
	@Override
	public DataResult<List<CorporateCustomer>> getAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(), Messages.GetAll);
	}

	@Override
	public DataResult<CorporateCustomer> getById(int id) {
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.findById(id).get(), Messages.GetById);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(createCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		
		corporateCustomer.setCompanyName(createCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setCustomerId(deleteCorporateCustomerRequest.getId());
		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(updateCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(updateCorporateCustomerRequest.getId());
		
		corporateCustomer.setCompanyName(updateCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		corporateCustomer.setCustomerId(updateCorporateCustomerRequest.getId());
		corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(true, Messages.Update);
	}

}
