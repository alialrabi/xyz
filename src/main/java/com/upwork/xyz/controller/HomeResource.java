package com.upwork.xyz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeResource {
	

	 @GetMapping(value = "/")
	 public ResponseEntity<String> home() {
	        return new ResponseEntity<>("Alialrabi", HttpStatus.OK);
	 }

}
