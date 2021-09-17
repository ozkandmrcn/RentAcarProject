package com.etiya.recap.entities.requests;



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
public class CreateCustomerRequest {

	private int id;
	
	@NotBlank(message="Boş olamaz")
	@NotNull
	@Size(min=2,max=30)
	private String companyName;
	
	
	private int userId;

}
