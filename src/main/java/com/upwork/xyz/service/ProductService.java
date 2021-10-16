package com.upwork.xyz.service;

import java.util.Set;

import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.dto.ProductDTO;

public interface ProductService {
	
	 ProductDTO CreateProduct(ProductDTO productDTO);
	
	 Product updateProduct(Product product);
	
	 Set<Product> search(String name);
	
	 void deleteProduct(long productId);
	
}
