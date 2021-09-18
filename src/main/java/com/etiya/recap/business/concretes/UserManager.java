package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.UserService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.UserDao;
import com.etiya.recap.entities.concretes.User;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.CreateUserRequest;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	
	@Autowired
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<List<User>>(this.userDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) {
		
		User user=new User();
		user.setFirstName(createUserRequest.getFirstName());
		user.setLastName(createUserRequest.getLastName());
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(createUserRequest.getPassword());
		
		
		this.userDao.save(user);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<User> getById(int id) {
		
		return new SuccessDataResult<User>(this.userDao.getById(id), Messages.GetAll);
	}

	@Override
	public Result delete(CreateUserRequest createUserRequest) {
		
		User user=new User();
		user.setUserId(createUserRequest.getId());
		
		
		this.userDao.delete(user);
		return new SuccessResult(true , Messages.Delete);
	}

	@Override
	public Result update(CreateUserRequest createUserRequest) {
		
		
		User user=new User();
		user.setUserId(createUserRequest.getId());
		user.setFirstName(createUserRequest.getFirstName());
		user.setLastName(createUserRequest.getLastName());
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(createUserRequest.getPassword());
		
		this.userDao.save(user);
		return new SuccessResult(true , Messages.Update);
	}

	@Override
	public Result userLogin(UserLoginDto userLoginDto) {
		
		
	    User user=new User();
		user.setEmail(userLoginDto.getEmail());
		user.setPassword(userLoginDto.getPassword());
		
		var result = BusinessRules.run(checkEmailAndPassForLogin(user));

		if (result != null) {
			return result;
		}
		
		return new SuccessResult(true,Messages.SuccessLogin);
		
	}

	@Override
	public Result userRegister(UserRegisterDto userRegisterDto) {
		
		User user=new User();
		user.setFirstName(userRegisterDto.getFirstName());
		user.setLastName(userRegisterDto.getLastName());
		user.setEmail(userRegisterDto.getEmail());
		user.setPassword(userRegisterDto.getPassword());
		

		var result = BusinessRules.run(checkEmailForRegister(user));

		if (result != null) {
			return result;
		}
			
		this.userDao.save(user);
		return new SuccessResult(true, Messages.Add);
	}
	
	private Result checkEmailForRegister(User user)
	{
		for (String email : this.userDao.getEmails()) {
			
			if(email.equals(user.getEmail()))
			{
				return new ErrorResult(Messages.ErrorMail);
			}		
		}
		
		return new SuccessResult();
	}
	
	private Result checkEmailAndPassForLogin(User user)
	{
		for (User users: this.userDao.findAll()){
			
			if(users.getEmail().equals(user.getEmail()) && users.getPassword().equals(user.getPassword()))
			{
				return new SuccessResult();
			}		
		}
		
		return new ErrorResult(Messages.ErrorLogin);
	}

}
