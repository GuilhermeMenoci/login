package com.br.login.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.login.dto.LoginRequestDTO;
import com.br.login.dto.RegisterRequestDTO;
import com.br.login.dto.ResponseDTO;
import com.br.login.entity.UserEntity;
import com.br.login.exception.InvalidParameterException;
import com.br.login.exception.LoginRegistered;
import com.br.login.exception.NotFoundException;
import com.br.login.infra.security.TokenService;
import com.br.login.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenSerivce;
	
	public ResponseDTO login(LoginRequestDTO body) {
		
		UserEntity user = this.repository.findByEmail(body.email()).orElseThrow(() -> new NotFoundException("User not found"));
		
		validateLogin(body, user);
		
		String token = this.tokenSerivce.generateToken(user);
		return new ResponseDTO(user.getName(), token);
		
	}

	private void validateLogin(LoginRequestDTO body, UserEntity user) {
		if(!passwordEncoder.matches(body.password(), user.getPassword())) {
			throw new InvalidParameterException("invalid login!");
		}
	}
	
	@Transactional
	public ResponseDTO register(RegisterRequestDTO body) {

		Optional<UserEntity> user = this.repository.findByEmail(body.email());
		
		validateRegister(user);
		
		UserEntity newUser = new UserEntity();
		newUser.setPassword(passwordEncoder.encode(body.password()));
		newUser.setEmail(body.email());
		newUser.setName(body.name());
		repository.save(newUser);
		
		String token = this.tokenSerivce.generateToken(newUser);
		return new ResponseDTO(newUser.getName(), token);

	}

	private void validateRegister(Optional<UserEntity> user) {
		if(user.isPresent()) {
			throw new LoginRegistered("invalid login!");
		}
	}
	
}
