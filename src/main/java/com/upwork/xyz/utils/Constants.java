package com.upwork.xyz.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
     
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
	public static final String SYSTEM = "system";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String _PRODUCT_NOT_EXISTS = "Product Not Exist.";

}
