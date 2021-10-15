package com.upwork.xyz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upwork.xyz.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

   public Optional<User> findByEmail(String email);

   @Query("SELECT u FROM User u WHERE u.username = :username")
   public User getUserByUsername(String username);
}
