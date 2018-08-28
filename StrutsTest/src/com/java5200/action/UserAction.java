package com.java5200.action;

import com.java5200.Model.User;
import com.java5200.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	UserService userService=new UserService();
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("Ö´ÐÐUserAction·½·¨");
		User user=new User();
		user.setUserName(userName);
		user.setPassword(password);
		if(userService.login(user)){
			return SUCCESS;
		}else {
			return ERROR;
		}
	}

}
