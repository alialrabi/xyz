package com.upwork.xyz.controller;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upwork.xyz.exception.EntityNotFoundException;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.ProductService;
import com.upwork.xyz.service.dto.ProductDTO;
import com.upwork.xyz.service.impl.ProductServiceImpl;
import com.upwork.xyz.utils.Constants;

/**
 * 
 * @author ali
 *
 */
@RestController
@RequestMapping("/api")
public class ProductResource {
	
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	 private final ProductService productService;
	 
	 public ProductResource(ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/product")
	public ResponseEntity<ProductDTO> createUser(@Valid @RequestBody ProductDTO productDTO) {
	   log.debug("Rest service to Create new product" + productDTO);
	   ProductDTO newProduct= productService.CreateProduct(productDTO);		  
	   return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
	}
	
	@PutMapping("/product")
	public ResponseEntity<Optional<ProductDTO>> updateUser(@Valid @RequestBody ProductDTO productDTO) {
		log.debug("Rest service to edit product" + productDTO);
		Optional<ProductDTO> updatedProduct= productService.updateProduct(productDTO);	
	    return new ResponseEntity<>(updatedProduct,HttpStatus.CREATED);
	}
	 
	
	@GetMapping(value = "/search")
	public ResponseEntity<Set<Product>> searchProduct() {
       log.debug("Rest service to search product");	
	   Set<Product> products=productService.search();
	   return  new ResponseEntity<Set<Product>>(products,HttpStatus.OK);
	}
	 
	 
	@DeleteMapping(value = "/product/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long productId) {
	   log.debug("Rest service to delete product" + productId);	
       productService.deleteProduct(productId);
       return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
