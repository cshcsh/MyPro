package com.java5200.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java5200.dao.UserDao;
import com.java5200.model.User;
import com.java5200.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	@Override
	public User login(User user) {
		return userDao.login(user);
	}

}
