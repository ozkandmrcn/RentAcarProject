package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.ApplicationUserService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.create.CreateApplicationUserRequest;
import com.etiya.recap.entities.requests.delete.DeleteApplicationUserRequest;
import com.etiya.recap.entities.requests.update.UpdateApplicationUserRequest;

@Service
public class ApplicationUserManager implements ApplicationUserService {

	private ApplicationUserDao applicationUserDao;

	@Autowired
	public ApplicationUserManager(ApplicationUserDao applicationUserDao) {
		super();
		this.applicationUserDao = applicationUserDao;
	}

	@Override
	public DataResult<List<ApplicationUser>> getAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.applicationUserDao.findAll(), Messages.GetAll);
	
	}
	

	@Override
	public Result add(CreateApplicationUserRequest createUserRequest) {

		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setFirstName(createUserRequest.getFirstName());
		applicationUser.setLastName(createUserRequest.getLastName());
		applicationUser.setEmail(createUserRequest.getEmail());
		applicationUser.setPassword(createUserRequest.getPassword());
		
		
		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<ApplicationUser> getById(int id) {

		return new SuccessDataResult<ApplicationUser>(this.applicationUserDao.getById(id), Messages.GetAll);
	}

	@Override
	public Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest) {

		
		
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(deleteApplicationUserRequest.getId());

		this.applicationUserDao.delete(applicationUser);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result update(UpdateApplicationUserRequest updateApplicationUserRequest) {

		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setUserId(updateApplicationUserRequest.getId());
		applicationUser.setFirstName(updateApplicationUserRequest.getFirstName());
		applicationUser.setLastName(updateApplicationUserRequest.getLastName());
		applicationUser.setEmail(updateApplicationUserRequest.getEmail());
		applicationUser.setPassword(updateApplicationUserRequest.getPassword());

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(true, Messages.Update);
	}

	@Override
	public Result userLogin(UserLoginDto userLoginDto) {

		
		ApplicationUser applicationUser=new ApplicationUser();
		
		applicationUser.setEmail(userLoginDto.getEmail());
		applicationUser.setPassword(userLoginDto.getPassword());

		var result = BusinessRules.run(checkEmailAndPassForLogin(applicationUser));

		if (result != null) {
			return result;
		}

		return new SuccessResult(true, Messages.SuccessLogin);

	}

	@Override
	public Result userRegister(UserRegisterDto userRegisterDto) {

		
		ApplicationUser applicationUser=new ApplicationUser();
		applicationUser.setFirstName(userRegisterDto.getFirstName());
		applicationUser.setLastName(userRegisterDto.getLastName());
		applicationUser.setEmail(userRegisterDto.getEmail());
		applicationUser.setPassword(userRegisterDto.getPassword());

		var result = BusinessRules.run(checkEmailForRegister(applicationUser));

		if (result != null) {
			return result;
		}

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(true, Messages.SuccessRegister);
	}

	//Kayıt olurken aynı email daha önce kullanılmış mı
	private Result checkEmailForRegister(ApplicationUser applicationUser) {
		for (String email : this.applicationUserDao.getEmails()) {

			if (email.equals(applicationUser.getEmail())) {
				return new ErrorResult(Messages.ErrorMail);
			}
		}
		return new SuccessResult();
	}

	//Giriş yaparken email ve şifre dbde var mı
	private Result checkEmailAndPassForLogin(ApplicationUser applicationUser) {
		for (ApplicationUser users : this.applicationUserDao.findAll()) {

			if (users.getEmail().equals(applicationUser.getEmail()) && users.getPassword().equals(applicationUser.getPassword())) {
				return new SuccessResult();
			}
		}
		return new ErrorResult(Messages.ErrorLogin);
	}

}
