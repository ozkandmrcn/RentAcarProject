package com.etiya.recap.core.services.posService;

import org.springframework.stereotype.Service;

import com.etiya.recap.entities.requests.create.CreatePosServiceRequest;

@Service
public interface PosService {
	
	public boolean withdraw(CreatePosServiceRequest createPosServiceRequest);

}
