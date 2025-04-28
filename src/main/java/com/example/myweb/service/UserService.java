package com.example.myweb.service;

import com.example.myweb.model.UserD;

public interface UserService {
	
	public UserD createUser(UserD userD);
	
	public boolean checkEmail(String email);
	
	public UserD logIn(String role , String email, String password);

}
