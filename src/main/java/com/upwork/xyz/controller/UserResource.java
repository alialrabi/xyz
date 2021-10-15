package com.upwork.xyz.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upwork.xyz.model.User;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.RegisterdUser;


@RestController
@RequestMapping("/api")
public class UserResource {
	
	 private final UserService userService;
	 
	 public UserResource(UserService userService) {
	        this.userService = userService;
	    }
	
	 @PostMapping("/user")
	 public ResponseEntity<User> createUser(@Valid @RequestBody RegisterdUser registerdUser) {
		  User user= userService.registerUser(registerdUser, "");		  
	      return new ResponseEntity<>(user,HttpStatus.OK);
	 }
	 
	 @GetMapping(value = "/user")
	 public String geUsers(ModelMap Model) {
	      return  "SSSSSSSSSSSSSSSSSSSS";
	 }
	
}
