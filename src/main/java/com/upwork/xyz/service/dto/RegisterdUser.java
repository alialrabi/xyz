package com.upwork.xyz.service.dto;

import javax.validation.constraints.Size;

public class RegisterdUser extends UserDTO {

	public static final int PASSWORD_MIN_LENGTH = 8;

	public static final int PASSWORD_MAX_LENGTH = 20;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;

	public RegisterdUser(long id, String username) {
			super(id, username);
			// TODO Auto-generated constructor stub
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
