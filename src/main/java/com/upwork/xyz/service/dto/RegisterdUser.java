package com.upwork.xyz.service.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class RegisterdUser extends UserDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;

	public static final int PASSWORD_MIN_LENGTH = 8;

	public static final int PASSWORD_MAX_LENGTH = 20;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;

	

	public RegisterdUser(@Size(min = 8, max = 20) String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
