package com.upwork.xyz.service.impl;


import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upwork.xyz.model.Product;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.ProductRpository;
import com.upwork.xyz.service.ProductService;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.ProductDTO;
import com.upwork.xyz.service.mapper.ProductMapper;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRpository productRpository;
    
    private final UserService userService;
    
    private final ProductMapper productMapper;

	public ProductServiceImpl(ProductRpository productRpository,ProductMapper productMapper,UserService userService) {
		this.productRpository = productRpository;
		this.productMapper = productMapper;
		this.userService = userService;
	}

	@Override
	public ProductDTO CreateProduct(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product=productMapper.toEntity(productDTO);
		product = productRpository.save(product);
		return productMapper.toDto(product);
	}

	@Override
	public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
		return productRpository
	            .findById(productDTO.getId())
	            .map(
	                existingStore -> {
	                    productMapper.partialUpdate(existingStore, productDTO);

	                    return existingStore;
	                }
	            )
	            .map(productRpository::save)
	            .map(productMapper::toDto);
	}

	@Override
	public Set<Product> search() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
		   String username = ((UserDetails)principal).getUsername();
		   User user=userService.getUser(username);
	       Set<Product> products=productRpository.search(user.getStore().getId());
		   return products;
		} 
		
		return null;
	}

	@Override
	public void deleteProduct(long productId) {
		log.debug("Service to delee product " + productId);
		if(productRpository.existsById(productId)) {
		    productRpository.deleteById(productId);
		}
	}

}
