package com.etiya.recap.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	
	private int modelYear;
	private double dailyPrice;
	private String description;
	
	
	private int brandId;
	private int colorId;

}
