package com.victorio.access.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.access.dto.UserDTO;
import com.victorio.access.exceptions.UserAlreadyExistsException;
import com.victorio.access.model.User;
import com.victorio.access.repositories.UserRepository;
import com.victorio.access.services.AuthService;
import com.victorio.access.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO user) {
		try {
			authService.registerUser(user);
			return ResponseEntity.status(201).body("User registered successfully!");
		} catch(UserAlreadyExistsException e) {
			return ResponseEntity.status(401).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO data) {
		
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((User) auth.getPrincipal());
		
			return ResponseEntity.ok(token);
		} catch (BadCredentialsException e) {
	        return ResponseEntity.status(401).body("Invalid username or password!");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
	    }
	}

}
