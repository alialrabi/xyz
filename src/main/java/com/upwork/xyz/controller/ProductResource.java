package com.upwork.xyz.controller;

import java.util.Set;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.ProductService;
import com.upwork.xyz.service.dto.ProductDTO;

@RestController
@RequestMapping("/api")
public class ProductResource {
	
	 private final ProductService productService;
	 
	 public ProductResource(ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/product")
	public ResponseEntity<ProductDTO> createUser(@Valid @RequestBody ProductDTO productDTO) {
	   ProductDTO newProduct= productService.CreateProduct(productDTO);		  
	   return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
	}
	
	@PutMapping("/product")
	public ResponseEntity<Product> updateUser(@Valid @RequestBody Product product) {
	   Product updatedroduct= productService.updateProduct(product);		  
	   return new ResponseEntity<>(updatedroduct,HttpStatus.OK);
	}
	 
	@GetMapping(value = "/search")
	public ResponseEntity<Set<Product>> searchProduct(@RequestBody String productName) {
	   Set<Product> products=productService.search(productName);
	   return  new ResponseEntity<Set<Product>>(products,HttpStatus.OK);
	}
	 
	 
	@DeleteMapping(value = "/product")
	public void deleteUser(@PathVariable("id") long productId) {
       productService.deleteProduct(productId);
    }
	
}
