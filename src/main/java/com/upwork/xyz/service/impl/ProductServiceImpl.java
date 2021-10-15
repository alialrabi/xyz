package com.upwork.xyz.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public Product CreateProduct(Product product) {
		return null;
	}

   
}
