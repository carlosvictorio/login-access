package com.victorio.access.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorio.access.dto.UserDTO;
import com.victorio.access.exceptions.UserAlreadyExistsException;
import com.victorio.access.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@InjectMocks
	private AuthService service;
	@Mock
	private UserRepository repository;
	
	@DisplayName("Deve retornar uma exceção quando o usuário já for registrado")
	@Test
	void shouldThrowExceptionWhenUserAlreadyExist() {
		UserDTO user = new UserDTO("victorio2004", "2004");
		when(repository.existsByUsername(user.username())).thenReturn(true);
		assertThrows(UserAlreadyExistsException.class, () -> service.registerUser(user));		
	}

}
