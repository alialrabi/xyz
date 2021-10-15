package com.upwork.xyz.service.impl;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.repository.ProductRpository;
import com.upwork.xyz.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRpository productRpository;

	public ProductServiceImpl(ProductRpository productRpository) {
		this.productRpository = productRpository;
	}

	@Override
	public Product CreateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Product> search(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(long productId) {
		productRpository.deleteById(productId);
		
	}

}
