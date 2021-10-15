package com.upwork.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upwork.xyz.model.Product;

public interface ProductRpository extends JpaRepository<Product, Long>{

}
