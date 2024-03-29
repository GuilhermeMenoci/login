package com.br.login.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.login.dto.LoginRequestDTO;
import com.br.login.dto.RegisterRequestDTO;
import com.br.login.dto.ResponseDTO;
import com.br.login.entity.UserEntity;
import com.br.login.infra.TokenService;
import com.br.login.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final TokenService tokenSerivce;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO body){
		
		UserEntity user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
		if(passwordEncoder.matches(user.getPassword(), body.password())) {
			String token = this.tokenSerivce.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body){
		
		Optional<UserEntity> user = this.repository.findByEmail(body.email());
		if(user.isEmpty()) {
			UserEntity newUser = new UserEntity();
			newUser.setPassword(passwordEncoder.encode(body.password()));
			newUser.setEmail(body.email());
			newUser.setName(body.name());
			repository.save(newUser);
			
			String token = this.tokenSerivce.generateToken(newUser);
			return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
}
