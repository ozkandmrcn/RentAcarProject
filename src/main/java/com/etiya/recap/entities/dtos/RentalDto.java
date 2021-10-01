package com.etiya.recap.entities.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
	
	private int id;
	private Date rentDate;
	private Date returnDate;
	private int carId;
	private int customerId;
	private String rentalStartingCity;
	private String returnCity;
	private int kilometer;
	
	
}
