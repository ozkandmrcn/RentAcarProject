package com.etiya.recap.entities.requests.create;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.etiya.recap.entities.dtos.CreditCardDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

	@NotNull(message = "Boş geçilemez")
	private Date rentDate;
	
	private Date returnDate;
	
	@NotNull
	@NotBlank
	private String returnCity;
	
	@NotNull
	private int kilometer;

	@NotNull
	private int carId;

	@NotNull
	private int userId;
	
	private CreditCardDto creditCardDto;
	
	private boolean saveCreditCard;
	
	private List<Integer> additionalServicesId;
	
	
	
}
