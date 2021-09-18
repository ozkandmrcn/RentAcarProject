package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.User;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.CreateUserRequest;

public interface UserService {
	
	DataResult<List<User>> getAll();

	Result add(CreateUserRequest createUserRequest);
	
	DataResult<User> getById(int id);
	
	Result delete(CreateUserRequest createUserRequest);
	
	Result update(CreateUserRequest createUserRequest);
	
	Result userLogin(UserLoginDto userLoginDto);

	Result userRegister(UserRegisterDto userRegisterDto);
	
	
	

}
