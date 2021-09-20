package com.etiya.recap.entities.requests.update;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.etiya.recap.entities.concretes.CarImages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	private int id;

	@NotNull
	@Min(1900)
	private int modelYear;

	@NotNull
	@Min(0)
	private double dailyPrice;

	private String description;

	@NotNull
	@Min(0)
	@Max(1900)
	private int findeksScore;

	private int brandId;
	private int colorId;

	private List<CarImages> carImages;

}
