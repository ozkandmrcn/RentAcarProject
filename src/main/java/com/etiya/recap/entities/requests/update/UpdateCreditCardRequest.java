package com.etiya.recap.entities.requests.update;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	@NotNull
	private int id;

	@NotBlank(message = "Boş olamaz")
	@NotNull
	@Size(min = 10, max = 30)
	private String nameOnTheCard;

	@NotBlank(message = "Boş olamaz")
	@NotNull
	@Size(min = 10, max = 30)
	private String cardNumber;

	
	private Date expirationDate;

	@NotBlank(message = "Boş olamaz")
	@NotNull
	@Size(min = 0, max = 10)
	private String cvc;

	private int customerId;

}
