package com.br.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.login.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

}
