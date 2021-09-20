package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.abstracts.User;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.create.CreateUserRequest;
import com.etiya.recap.entities.requests.delete.DeleteUserRequest;
import com.etiya.recap.entities.requests.update.UpdateUserRequest;

public interface UserService {
	
	DataResult<List<User>> getAll();

	Result add(CreateUserRequest createUserRequest);
	
	DataResult<User> getById(int id);
	
	Result delete(DeleteUserRequest deleteUserRequest);
	
	Result update(UpdateUserRequest updateUserRequest);
	
	Result userLogin(UserLoginDto userLoginDto);

	Result userRegister(UserRegisterDto userRegisterDto);
	
}
