package com.upwork.xyz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class UsernameAlreadyUsedException extends RuntimeException{

	   public UsernameAlreadyUsedException(String message) {
	        super(message);
	    }
}