package com.victorio.access.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.victorio.access.dto.UserDTO;
import com.victorio.access.exceptions.UserAlreadyExistsException;
import com.victorio.access.model.User;
import com.victorio.access.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService{
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username);
	}
	
	public void registerUser(UserDTO data) {
		if(repository.existsByUsername(data.username())) {
			throw new UserAlreadyExistsException();
		}
		
		String passwordEncrypted = new BCryptPasswordEncoder().encode(data.password());
		User user = new User(data.username(), passwordEncrypted);
		repository.save(user);
	}

}
