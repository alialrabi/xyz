package com.upwork.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.upwork.xyz.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

	public Authority findByName(String name);

}
