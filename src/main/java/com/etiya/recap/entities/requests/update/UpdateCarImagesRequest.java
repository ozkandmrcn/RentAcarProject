package com.etiya.recap.entities.requests.update;

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
public class UpdateCarImagesRequest {
	
	private int id;

	@NotNull(message = "Boş geçilemez")
	private String imagePath;

	@NotNull(message = "Boş geçilemez")
	private Date date;

	private int carId;

}
