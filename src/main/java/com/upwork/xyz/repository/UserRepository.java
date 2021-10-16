package com.upwork.xyz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upwork.xyz.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

   public Optional<User> findByEmail(String email);

   @EntityGraph(attributePaths = "authorities")
   public User getUserByUsername(String username);
}
