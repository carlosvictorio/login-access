package com.victorio.access.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.access.dto.UserDTO;
import com.victorio.access.model.User;
import com.victorio.access.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository repository;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO data) {
		if(repository.existsByUsername(data.username())) return ResponseEntity.badRequest().body("User already registered!");
		
		String passwordEncrypted = new BCryptPasswordEncoder().encode(data.password());
		User user = new User(data.username(), passwordEncrypted);
		repository.save(user);
		return ResponseEntity.status(201).body("User registered successfully!");
				
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		return ResponseEntity.ok().body("Login successfully!");
	}

}
