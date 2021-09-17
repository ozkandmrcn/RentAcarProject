package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.UserService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.UserDao;
import com.etiya.recap.entities.concretes.User;
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
		return new SuccessDataResult<List<User>>(this.userDao.findAll(), "Kullanıcılar başarıyla listelendi");
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) {
		
		User user=new User();
		user.setFirstName(createUserRequest.getFirstName());
		user.setLastName(createUserRequest.getLastName());
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(createUserRequest.getPassword());
		
		
		this.userDao.save(user);
		return new SuccessResult(true, "Kullanıcı başarıyla eklendi");
	}

	@Override
	public DataResult<User> getById(int id) {
		
		return new SuccessDataResult<User>(this.userDao.getById(id), "Kullanıcı başarıyla listelendi");
	}

	@Override
	public Result delete(CreateUserRequest createUserRequest) {
		
		User user=new User();
		user.setUserId(createUserRequest.getId());
		
		
		this.userDao.delete(user);
		return new SuccessResult(true , " Kullanıcı silindi.");
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
		return new SuccessResult(true , "Kullanıcı güncelendi.");
	}

}
