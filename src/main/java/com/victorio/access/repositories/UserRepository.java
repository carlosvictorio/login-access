package com.victorio.access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.victorio.access.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	UserDetails findByUsername(String username);
	Boolean existsByUsername(String username);
	
}
