package com.upwork.xyz.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.upwork.xyz.model.Store;
import com.upwork.xyz.service.StoreService;


@Service
public class StoreServiceImpl implements StoreService{
	
    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

	@Override
	public Store CreateStore(Store store) {
		return null;
	}
   
}
