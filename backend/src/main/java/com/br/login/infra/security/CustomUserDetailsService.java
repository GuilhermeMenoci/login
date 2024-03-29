package com.br.login.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.br.login.entity.UserEntity;
import com.br.login.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
		
	}

}
