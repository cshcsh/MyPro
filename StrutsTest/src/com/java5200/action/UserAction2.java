package com.java5200.action;

import com.java5200.Model.User;
import com.java5200.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction2 extends ActionSupport {

	private UserService userService=new UserService();
	private User user;
	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("Ö´ÐÐUserAction·½·¨");
		if(userService.login(user)){
			return SUCCESS;
		}else {
			return ERROR;
		}
	}

}
