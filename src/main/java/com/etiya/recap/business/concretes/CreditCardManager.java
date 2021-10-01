package com.etiya.recap.business.concretes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CreditCardService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.CreditCardDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.CreditCard;
import com.etiya.recap.entities.requests.create.CreateCreditCardRequest;
import com.etiya.recap.entities.requests.delete.DeleteCreditCardRequest;
import com.etiya.recap.entities.requests.update.UpdateCreditCardRequest;


@Service
public class CreditCardManager implements CreditCardService {
	
	private CreditCardDao creditCardDao;
	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}

	@Override
	public DataResult<List<CreditCard>> getAll() {
		return new SuccessDataResult<List<CreditCard>>(this.creditCardDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(createCreditCardRequest.getUserId());
		
		CreditCard creditCard=new CreditCard();
		creditCard.setNameOnTheCard(createCreditCardRequest.getNameOnTheCard());
		creditCard.setCardNumber(createCreditCardRequest.getCardNumber());
		creditCard.setExpirationDate(createCreditCardRequest.getExpirationDate());
		creditCard.setCvc(createCreditCardRequest.getCvc());
		
		creditCard.setApplicationUser(applicationUser);
		
		var result = BusinessRules.run(this.checkCreditCardNumber(creditCard));

		if (result != null) {
			return result;
		}

		
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
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(updateCreditCardRequest.getUserId());
		
		CreditCard creditCard=this.creditCardDao.getById(updateCreditCardRequest.getId());
		creditCard.setId(updateCreditCardRequest.getId());
		creditCard.setNameOnTheCard(updateCreditCardRequest.getNameOnTheCard());
		creditCard.setCardNumber(updateCreditCardRequest.getCardNumber());
		creditCard.setExpirationDate(updateCreditCardRequest.getExpirationDate());
		creditCard.setCvc(updateCreditCardRequest.getCvc());
		
		creditCard.setApplicationUser(applicationUser);
		
		var result = BusinessRules.run(this.checkCreditCardNumber(creditCard));

		if (result != null) {
			return result;
		}
		
		this.creditCardDao.save(creditCard);
     	return new SuccessResult(true, Messages.Add);
	}
	
	//Kredi kartı kontrolü
	private Result checkCreditCardNumber(CreditCard creditCard) {
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		
		Pattern pattern = Pattern.compile(regex);
		creditCard.setCardNumber(creditCard.getCardNumber().replaceAll("-", ""));
		Matcher matcher = pattern.matcher(creditCard.getCardNumber());
		if(matcher.matches()==true) {
			return new SuccessResult();
		}else {
			return new ErrorResult(Messages.ErrorIfCreditCardIsWrong);
		}
	}


}
