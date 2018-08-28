package com.java5200.service;

import com.java5200.Model.User;

public class UserService {

	public boolean login(User user){
		if("java5200".equals(user.getUserName())&&"123456".equals(user.getPassword())){
			return true;
		}else {
			return false;
		}
	}
}
