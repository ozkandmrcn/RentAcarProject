package com.etiya.recap.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recap.business.abstracts.ApplicationUserService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.create.CreateApplicationUserRequest;
import com.etiya.recap.entities.requests.delete.DeleteApplicationUserRequest;
import com.etiya.recap.entities.requests.update.UpdateApplicationUserRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private ApplicationUserService applicationUserService;

	@Autowired
	public UserController(ApplicationUserService applicationUserService) {
		super();
		this.applicationUserService = applicationUserService;
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  CreateApplicationUserRequest createUserRequest) {
		return ResponseEntity.ok(this.applicationUserService.add(createUserRequest));
	}

	@GetMapping("/getallusers")
	public DataResult<List<ApplicationUser>> getAllUsers() {
		return this.applicationUserService.getAll();
	}
	
	@GetMapping("/getuserbyid")
	public  DataResult<ApplicationUser> getUserById(int id) {
		return this.applicationUserService.getById(id);
	}
	
	@DeleteMapping("/removeuser")
	public Result removeCar(DeleteApplicationUserRequest deleteApplicationUserRequest) {
		return this.applicationUserService.delete(deleteApplicationUserRequest);
	}
	
	@PostMapping("/updateuser")
	public ResponseEntity<?> updateCar(@Valid @RequestBody  UpdateApplicationUserRequest updateApplicationUserRequest) {
		return ResponseEntity.ok(this.applicationUserService.update(updateApplicationUserRequest));
	}
	
	@PostMapping("/registeruser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  UserRegisterDto userRegisterDto) {
		return ResponseEntity.ok(this.applicationUserService.userRegister(userRegisterDto));
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  UserLoginDto userLoginDto) {
		return ResponseEntity.ok(this.applicationUserService.userLogin(userLoginDto));
	}
}
