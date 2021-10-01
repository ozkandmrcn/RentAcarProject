package com.etiya.recap.entities.requests.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

	@NotNull
	@Min(1900)
	private int modelYear;

	@NotNull
	@Min(0)
	private double dailyPrice;

	@NotNull
	@Min(0)
	@Max(1900)
	private int findeksScore;
	
	@NotNull
	@NotBlank
	private String city;
	
	@NotNull
	private int kilometer;
	
	@NotBlank
	@NotNull
	private String description;

	@NotNull
	private int brandId;
	
	@NotNull
	private int colorId;

}
