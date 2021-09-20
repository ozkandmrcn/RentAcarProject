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

import com.etiya.recap.business.abstracts.UserService;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.abstracts.User;
import com.etiya.recap.entities.dtos.UserLoginDto;
import com.etiya.recap.entities.dtos.UserRegisterDto;
import com.etiya.recap.entities.requests.create.CreateUserRequest;
import com.etiya.recap.entities.requests.delete.DeleteUserRequest;
import com.etiya.recap.entities.requests.update.UpdateUserRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  CreateUserRequest createUserRequest) {
		return ResponseEntity.ok(this.userService.add(createUserRequest));
	}

	@GetMapping("/getallusers")
	public DataResult<List<User>> getAllUsers() {
		return this.userService.getAll();
	}
	
	@GetMapping("/getuserbyid")
	public  DataResult<User> getUserById(int id) {
		return this.userService.getById(id);
	}
	
	@DeleteMapping("/removeuser")
	public Result removeCar(DeleteUserRequest deleteUserRequest) {
		return this.userService.delete(deleteUserRequest);
	}
	
	@PostMapping("/updateuser")
	public ResponseEntity<?> updateCar(@Valid @RequestBody  UpdateUserRequest updateUserRequest) {
		return ResponseEntity.ok(this.userService.update(updateUserRequest));
	}
	
	@PostMapping("/registeruser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  UserRegisterDto userRegisterDto) {
		return ResponseEntity.ok(this.userService.userRegister(userRegisterDto));
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<?>addUser(@Valid @RequestBody  UserLoginDto userLoginDto) {
		return ResponseEntity.ok(this.userService.userLogin(userLoginDto));
	}
}
