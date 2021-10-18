package com.upwork.xyz.service.impl;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.upwork.xyz.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upwork.xyz.model.Product;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.ProductRepository;
import com.upwork.xyz.service.ProductService;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.ProductDTO;
import com.upwork.xyz.service.mapper.ProductMapper;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRepository productRepository;
    
    private final UserService userService;
    
    private final ProductMapper productMapper;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, UserService userService) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.userService = userService;
	}

	@Override
	public ProductDTO CreateProduct(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product=productMapper.toEntity(productDTO);

		Store store = new Store();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		System.out.println(principal + "  principal ------------------------------>");

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			User user=userService.getUser(username);
			store = user.getStore();
		}
		Set<Store> stores = new HashSet<>();
		stores.add(store);
		product.setStoreProducts(stores);
		product = productRepository.save(product);
		return productMapper.toDto(product);
	}

	@Override
	public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
		return productRepository
	            .findById(productDTO.getId())
	            .map(
	                existingStore -> {
	                    productMapper.partialUpdate(existingStore, productDTO);

	                    return existingStore;
	                }
	            )
	            .map(productRepository::save)
	            .map(productMapper::toDto);
	}

	@Override
	public Set<Product> search() {
		Set<Product> products= productRepository.search();
		return products;
	}

	@Override
	public void deleteProduct(long productId) {
		log.debug("Service to delee product " + productId);
		if(productRepository.existsById(productId)) {
		    productRepository.deleteById(productId);
		}
	}

}
