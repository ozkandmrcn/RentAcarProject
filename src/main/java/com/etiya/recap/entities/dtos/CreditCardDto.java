package com.etiya.recap.entities.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDto {

	private String nameOnTheCard;
	private String cardNumber;
	private Date expirationDate;
	private String cvc;
}
