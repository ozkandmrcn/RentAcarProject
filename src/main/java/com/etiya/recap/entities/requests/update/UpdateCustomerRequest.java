package com.etiya.recap.entities.requests.update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class UpdateCustomerRequest {
	

	private int id;
	
	@NotBlank(message="Boş olamaz")
	@NotNull
	@Size(min=2,max=30)
	private String companyName;
	

	@NotNull
	@Min(0)
	@Max(1900)
	private int findeksScore;
	
	private int userId;

}
