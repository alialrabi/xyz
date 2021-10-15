package com.upwork.xyz.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upwork.xyz.model.Product;

public interface ProductRpository extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE p.productName= :productName")
	public Set<Product> search(String productName);

}
