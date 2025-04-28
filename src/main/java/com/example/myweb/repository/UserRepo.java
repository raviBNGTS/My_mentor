package com.example.myweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.model.UserD;

public interface UserRepo extends JpaRepository<UserD, Integer> {

	public boolean existsByEmail(String email);
	
	public UserD findByRoleAndEmailAndPassword(String role, String email,String password);
}
