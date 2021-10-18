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

import com.upwork.xyz.exception.EntityNotFoundException;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.ProductRepository;
import com.upwork.xyz.repository.StoreRpository;
import com.upwork.xyz.service.ProductService;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.ProductDTO;
import com.upwork.xyz.service.mapper.ProductMapper;
import com.upwork.xyz.utils.Constants;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRepository productRepository;
    
    private final StoreRpository storeRpository;
    
    private final UserService userService;
    
    private final ProductMapper productMapper;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
			UserService userService,StoreRpository storeRpository) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.userService = userService;
		this.storeRpository = storeRpository;
	}

	@Override
	public ProductDTO CreateProduct(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product=productMapper.toEntity(productDTO);
		Optional<Store> store = storeRpository.findById(productDTO.getStoreId());
		if(store.isPresent()) {
		    Set<Store> stores = new HashSet<>();
		    stores.add(store.get());
		    product.setStoreProducts(stores);
		    product = productRepository.save(product);
		}else {
			throw new EntityNotFoundException(Constants._STORE_NOT_EXISTS);
		}
		return productMapper.toDto(product);
	}

	@Override
	public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
		Optional<ProductDTO>  updatedProduct =  productRepository
	            .findById(productDTO.getId())
	            .map(
	                existingStore -> {
	                    productMapper.update(existingStore, productDTO);

	                    return existingStore;
	                }
	            )
	            .map(productRepository::save)
	            .map(productMapper::toDto);
		System.out.println(updatedProduct);
		if(updatedProduct.isEmpty()) {
			throw new EntityNotFoundException(Constants._PRODUCT_NOT_EXISTS);
		}
		return updatedProduct;
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
