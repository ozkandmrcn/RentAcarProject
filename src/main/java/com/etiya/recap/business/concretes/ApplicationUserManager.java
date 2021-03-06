package com.etiya.recap.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.ApplicationUserService;
import com.etiya.recap.business.constants.messages.UserMessages;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.dtos.ApplicationUserDto;
import com.etiya.recap.entities.requests.applicationUserRequests.CreateUserLoginRequest;
import com.etiya.recap.entities.requests.applicationUserRequests.CreateUserRegisterRequest;
import com.etiya.recap.entities.requests.applicationUserRequests.DeleteApplicationUserRequest;
import com.etiya.recap.entities.requests.applicationUserRequests.UpdateApplicationUserRequest;

@Service
public class ApplicationUserManager implements ApplicationUserService {

	private ApplicationUserDao applicationUserDao;
	private final ModelMapper modelMapper;

	@Autowired
	public ApplicationUserManager(ApplicationUserDao applicationUserDao,ModelMapper modelMapper) {
		this.applicationUserDao = applicationUserDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<ApplicationUserDto>> getAll() {
		List<ApplicationUser> applicationUsers = this.applicationUserDao.findAll();
		List<ApplicationUserDto> applicationUserDtos = applicationUsers.stream().map(user -> modelMapper.map(user, ApplicationUserDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ApplicationUserDto>>(applicationUserDtos, UserMessages.GetAll);
	}

	@Override
	public DataResult<ApplicationUserDto> getById(int id) {
		
		ApplicationUser applicationUser= this.applicationUserDao.getById(id);
		ApplicationUserDto applicationUserDto = modelMapper.map(applicationUser, ApplicationUserDto.class);
		return new SuccessDataResult<ApplicationUserDto>(applicationUserDto, UserMessages.GetById);
		
	}

	@Override
	public Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest) {

		//ApplicationUser applicationUser = this.applicationUserDao.getById(deleteApplicationUserRequest.getId());
		
		ApplicationUser applicationUser = modelMapper.map(deleteApplicationUserRequest,ApplicationUser.class);
		
		this.applicationUserDao.delete(applicationUser);
		return new SuccessResult(true, UserMessages.Delete);
	}

	@Override
	public Result update(UpdateApplicationUserRequest updateApplicationUserRequest) {

		ApplicationUser applicationUser = modelMapper.map(updateApplicationUserRequest,ApplicationUser.class);

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(true, UserMessages.Update);
	}
	
	@Override
	public Result userLogin(CreateUserLoginRequest createUserLoginRequest) {

		
		ApplicationUser applicationUser = modelMapper.map(createUserLoginRequest,ApplicationUser.class);

		var result = BusinessRules.run(checkEmailAndPassForLogin(applicationUser));

		if (result != null) {
			return result;
		}
		return new SuccessResult(true, UserMessages.SuccessLogin);
	}
	
	@Override
	public Result userRegister(CreateUserRegisterRequest createUserRegisterRequest) {

		ApplicationUser applicationUser = modelMapper.map(createUserRegisterRequest,ApplicationUser.class);

		var result = BusinessRules.run(checkEmailForRegister(applicationUser));

		if (result != null) {
			return result;
		}

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(true, UserMessages.SuccessRegister);
	}
	
	// Kay??t olurken ayn?? email daha ??nce kullan??lm???? m??
	private Result checkEmailForRegister(ApplicationUser applicationUser) {
			if(this.applicationUserDao.existsByEmail(applicationUser.getEmail())==true) {
				return new ErrorResult(UserMessages.ErrorMailIsAlreadyExist);
			}
			return new SuccessResult();
		}

	// Giri?? yaparken email ve ??ifre dbde var m??
	private Result checkEmailAndPassForLogin(ApplicationUser applicationUser) {
		if(this.applicationUserDao.existsByEmail(applicationUser.getEmail())==false ||
				this.applicationUserDao.existsByPassword(applicationUser.getPassword())==false){
			return new ErrorResult(UserMessages.ErrorLogin);
		}
		return new SuccessResult();
	}

}
