package com.etiya.recap.core.services.posService;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class IsPaymentService {

	public boolean withdraw(String nameOnTheCard,String cardNumber,Date expirationDate, String cvc,double feePayable) {
		double limit = 3000;
		if(limit >= feePayable) {
			System.out.println("Para yetti");
			return true;
		}
		else {
			System.out.println("Para yetmedi");
			return false;
		}
	}
	
}
