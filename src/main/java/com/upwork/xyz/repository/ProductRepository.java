package com.upwork.xyz.repository;

import java.util.Set;

import com.upwork.xyz.security.AuthoritiesConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.upwork.xyz.security.AuthoritiesConstants;

import com.upwork.xyz.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,  JpaSpecificationExecutor<Product> {
	

	@Query(value = "SELECT * FROM product p inner join"
			+ " store_products s on p.id = s.product_id "
			+ "WHERE (?#{hasAuthority('"+AuthoritiesConstants.ADMIN+"')}) "
					+ "OR (?#{hasAuthority('"+AuthoritiesConstants.EMPLOYEE+"')} "
							+ "and s.store_id = (select store_id from user u "
							+ "where u.username = ?#{ principal?.username }  ))" , nativeQuery = true)
	public Set<Product> search();


}
