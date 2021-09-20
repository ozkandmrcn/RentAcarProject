package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CreditCardService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CreditCardDao;
import com.etiya.recap.entities.concretes.CreditCard;
import com.etiya.recap.entities.concretes.Customer;
import com.etiya.recap.entities.requests.create.CreateCreditCardRequest;
import com.etiya.recap.entities.requests.delete.DeleteCreditCardRequest;
import com.etiya.recap.entities.requests.update.UpdateCreditCardRequest;

@Service
public class CreditCardManager implements CreditCardService {
	
	private CreditCardDao creditCardDao;

	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao) {
		super();
		this.creditCardDao = creditCardDao;
	}

	@Override
	public DataResult<List<CreditCard>> getAll() {
		return new SuccessDataResult<List<CreditCard>>(this.creditCardDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		
		Customer customer=new Customer();
		customer.setCustomerId(createCreditCardRequest.getCustomerId());
		
		CreditCard creditCard=new CreditCard();
		creditCard.setNameOnTheCard(createCreditCardRequest.getNameOnTheCard());
		creditCard.setCardNumber(createCreditCardRequest.getCardNumber());
		creditCard.setExpirationDate(createCreditCardRequest.getExpirationDate());
		creditCard.setCvc(createCreditCardRequest.getCvc());
		
		creditCard.setCustomer(customer);

		
		this.creditCardDao.save(creditCard);
     	return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<CreditCard> getById(int id) {
		return new SuccessDataResult<CreditCard>(this.creditCardDao.getById(id), Messages.GetById);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {
		
		CreditCard creditCard=new CreditCard();
		creditCard.setId(deleteCreditCardRequest.getId());
		
		this.creditCardDao.delete(creditCard);
		return new  SuccessResult(true,Messages.Delete);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {
		Customer customer=new Customer();
		customer.setCustomerId(updateCreditCardRequest.getCustomerId());
		
		CreditCard creditCard=new CreditCard();
		creditCard.setId(updateCreditCardRequest.getId());
		creditCard.setNameOnTheCard(updateCreditCardRequest.getNameOnTheCard());
		creditCard.setCardNumber(updateCreditCardRequest.getCardNumber());
		creditCard.setExpirationDate(updateCreditCardRequest.getExpirationDate());
		creditCard.setCvc(updateCreditCardRequest.getCvc());
		
		creditCard.setCustomer(customer);

		
		this.creditCardDao.save(creditCard);
     	return new SuccessResult(true, Messages.Add);
	}

}
