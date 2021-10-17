package com.upwork.xyz.service;

import java.util.Optional;
import java.util.Set;

import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.dto.ProductDTO;

public interface ProductService {
	
	 ProductDTO CreateProduct(ProductDTO productDTO);
	
	 Optional<ProductDTO> updateProduct(ProductDTO productDTO);
	
	 Set<Product> search();
	
	 void deleteProduct(long productId);
	
}
