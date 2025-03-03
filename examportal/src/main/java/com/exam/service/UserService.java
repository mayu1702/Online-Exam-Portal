package com.exam.service;

import java.util.Set;

import com.exam.model.User;
import com.exam.model.UserRole;

public interface UserService {
	
	// creating user 
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	
	//getuser by username
	public User getUser(String username);
	
	//delete user by id
	public void deleteUser(Long userId);
	
	
//	update user by id
//	public User updateUser(Long userId);

}
