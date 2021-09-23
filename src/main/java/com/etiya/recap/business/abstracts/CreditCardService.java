package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.CreditCard;
import com.etiya.recap.entities.requests.create.CreateCreditCardRequest;
import com.etiya.recap.entities.requests.delete.DeleteCreditCardRequest;
import com.etiya.recap.entities.requests.update.UpdateCreditCardRequest;

public interface CreditCardService {
	
    DataResult<List<CreditCard>> getAll();
    
    DataResult<CreditCard> getById(int id);
	
	Result add(CreateCreditCardRequest createCreditCardRequest);
	
	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);
	
	Result update(UpdateCreditCardRequest updateCreditCardRequest);

}
