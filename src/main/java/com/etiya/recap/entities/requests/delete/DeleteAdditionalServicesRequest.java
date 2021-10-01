package com.etiya.recap.entities.requests.delete;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAdditionalServicesRequest {
	
	@NotNull
	private int id;

}
