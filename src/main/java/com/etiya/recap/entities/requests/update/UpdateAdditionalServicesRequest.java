package com.etiya.recap.entities.requests.update;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalServicesRequest {
	
	@NotNull
	private int id;
	
	@NotNull
    private String additionalService;
	
	@NotNull
    private double additionalServicePrice;
	
	

}
