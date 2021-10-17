package com.upwork.xyz.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.upwork.xyz.model.Product;

/**
 * 
 * @author ali
 *
 */
public interface ProductRpository extends JpaRepository<Product, Long>,  JpaSpecificationExecutor<Product> {
	

	@Query("SELECT p FROM Product p join p.storeProducts s WHERE s.id= :id")
	public Set<Product> search(long id);
	

}
