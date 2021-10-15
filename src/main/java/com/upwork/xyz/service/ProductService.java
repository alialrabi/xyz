package com.upwork.xyz.service;

import java.util.Set;

import com.upwork.xyz.model.Product;

public interface ProductService {
	
	public Product CreateProduct(Product product);
	
	public Product updateProduct(Product product);
	
	public Set<Product> search(String name);
	
	public void deleteProduct(long productId);
	
}
