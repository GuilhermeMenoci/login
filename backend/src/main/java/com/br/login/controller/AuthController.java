package com.br.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.login.dto.LoginRequestDTO;
import com.br.login.dto.RegisterRequestDTO;
import com.br.login.dto.ResponseDTO;
import com.br.login.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	@PostMapping("/login")
	public ResponseDTO login(@RequestBody LoginRequestDTO body){
		return userService.login(body);
	}
	
	@PostMapping("/register")
	public ResponseDTO register(@RequestBody RegisterRequestDTO body){
		return userService.register(body);
	}
	
}
