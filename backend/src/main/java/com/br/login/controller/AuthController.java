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
import com.br.login.infra.security.TokenService;
import com.br.login.repository.UserRepository;
import com.br.login.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final TokenService tokenSerivce;
	
	private final UserService userService;
	
	@PostMapping("/login")
	public ResponseDTO login(@RequestBody LoginRequestDTO body){
		
		return userService.login(body);
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body){
		
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
