package com.upwork.xyz.repository;

import java.util.Set;

import com.upwork.xyz.security.AuthoritiesConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.upwork.xyz.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,  JpaSpecificationExecutor<Product> {
	

	@Query(value = "SELECT * FROM product p inner join store_products s on p.id = s.product_id WHERE (?#{hasAuthority('ADMIN')}) OR (s.store_id = (select store_id from user u inner join user_authority a on u.id = a.user_id where u.username = ?#{ principal?.username } and a.authority_name = 'EMPLOYEE'))" , nativeQuery = true)
	public Set<Product> search();


}
