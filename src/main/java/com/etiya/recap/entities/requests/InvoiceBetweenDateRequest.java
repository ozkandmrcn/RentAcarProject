package com.etiya.recap.entities.requests;

import java.util.Date;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceBetweenDateRequest {
	
	@NotNull
	private Date minDate;

	@NotNull
	private Date maxDate;

}
