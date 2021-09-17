package com.etiya.recap.entities.requests;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateRentalRequest {
	
	private int id;
	
	
	@NotNull(message = "Boş geçilemez")
	private Date rentDate;
	
	private Date returnDate;
	
	private int carId;
	private int customerId;


}
