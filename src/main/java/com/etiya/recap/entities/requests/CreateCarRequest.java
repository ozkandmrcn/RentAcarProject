package com.etiya.recap.entities.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	private int id;
	
	
	@NotNull
	@Min(1900)
	private int modelYear;
	
	@NotNull
	@Min(0)
	private double dailyPrice;
	
	private String description;
	
	private int brandId;
	private int colorId;

}
