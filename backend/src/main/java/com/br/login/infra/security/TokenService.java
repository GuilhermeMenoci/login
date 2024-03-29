package com.br.login.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.login.entity.UserEntity;
import com.br.login.exception.NotAuthorizedException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	//GERAR TOKEN
	public String generateToken(UserEntity user) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.create()
					.withIssuer("login-auth-api")
					.withSubject(user.getEmail())
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
			
		} catch (JWTCreationException e) {
			throw new NotAuthorizedException("Error while authenticating");
		}
		
		
	}
	
	public String validateToken(String token) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("login-auth-api")
					.build()
					.verify(token)
					.getSubject();
			
		} catch (JWTVerificationException e) {
			return null;
		}
		
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
