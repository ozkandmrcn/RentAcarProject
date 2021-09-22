package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.create.CreateApplicationUserRequest;
import com.etiya.recap.entities.requests.delete.DeleteApplicationUserRequest;
import com.etiya.recap.entities.requests.update.UpdateApplicationUserRequest;

public interface ApplicationUserService {
	
	DataResult<List<ApplicationUser>> getAll();

	Result add(CreateApplicationUserRequest createApplicationUserRequest);
	
	DataResult<ApplicationUser> getById(int id);
	
	Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest);
	
	Result update(UpdateApplicationUserRequest updateApplicationUserRequest);
	
	Result userLogin(UserLoginDto userLoginDto);

	Result userRegister(UserRegisterDto userRegisterDto);
	
}
