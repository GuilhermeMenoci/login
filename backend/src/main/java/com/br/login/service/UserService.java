package com.br.login.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.login.dto.LoginRequestDTO;
import com.br.login.dto.ResponseDTO;
import com.br.login.entity.UserEntity;
import com.br.login.exception.InvalidParameterException;
import com.br.login.exception.NotFoundException;
import com.br.login.infra.security.TokenService;
import com.br.login.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenSerivce;
	
	public ResponseDTO login(LoginRequestDTO body) {
		
		UserEntity user = this.repository.findByEmail(body.email()).orElseThrow(() -> new NotFoundException("User not found"));
		
		if(!passwordEncoder.matches(body.password(), user.getPassword())) {
			throw new InvalidParameterException("invalid login!");
		}
		
		String token = this.tokenSerivce.generateToken(user);
		return new ResponseDTO(user.getName(), token);
		
	}
	
}
