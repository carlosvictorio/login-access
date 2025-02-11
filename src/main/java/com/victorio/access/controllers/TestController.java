package com.victorio.access.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
	
	@GetMapping("/home")
	public ResponseEntity<String> home() {
		return ResponseEntity.ok().body("Home Page");
	}

}
