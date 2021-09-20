package com.etiya.recap.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.recap.entities.abstracts.User;

public interface UserDao extends JpaRepository<User, Integer> {
	
	@Query("Select u.email From User u ")
	List<String> getEmails();
	
}
