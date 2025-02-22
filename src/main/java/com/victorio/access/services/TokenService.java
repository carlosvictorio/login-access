package com.victorio.access.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.victorio.access.model.User;

@Service
public class TokenService {
	
	@Value("${secret.crypt}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("access")
					.withSubject(user.getUsername())
					.withExpiresAt(generateInstant())
					.sign(algorithm);
			
			return token;
		} catch(JWTCreationException e) {
			throw new RuntimeException("Error while generation token: ", e);
		}
	}
	
	public String validateToken(String token) {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("access")
					.build()
					.verify(token)
					.getSubject();
		} catch(JWTVerificationException e) {
			return "";
		}
	}
	
	private Instant generateInstant() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
