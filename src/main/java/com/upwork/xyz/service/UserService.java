package com.upwork.xyz.service;

import org.springframework.stereotype.Service;

import com.upwork.xyz.model.User;
import com.upwork.xyz.service.dto.UserDTO;


public interface UserService {
	
	public User registerUser(UserDTO userDTO,String password);
	
}
