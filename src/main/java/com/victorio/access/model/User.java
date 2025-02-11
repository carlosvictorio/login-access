package com.victorio.access.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.victorio.access.enums.AuthEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private AuthEnum role;
	@Column(unique = true)
	private String username;
	private String password;
	
	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.role = AuthEnum.USER;
		//this.role = role;
	}
	
	/*public User(Long id, String username, String password, AuthEnum role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		//this.role = role;
	}*/

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == AuthEnum.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
		
}
