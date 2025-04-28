package com.example.myweb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myweb.model.UserD;
import com.example.myweb.repository.UserRepo;
import com.example.myweb.service.UserService;

@Service
public class UserServiceImpliment implements UserService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserD createUser(UserD userD) {

		return repo.save(userD);
	}

	@Override
	public boolean checkEmail(String email) {

		return repo.existsByEmail(email);
	}

	@Override
	public UserD logIn(String role, String email, String password) {
		// TODO Auto-generated method stub
		return repo.findByRoleAndEmailAndPassword(role, email,password);
	}

	
	
	
	
	

}
