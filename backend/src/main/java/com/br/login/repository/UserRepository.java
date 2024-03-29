package com.br.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.login.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByEmail(String email);
	
}
