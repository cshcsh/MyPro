package com.java5200.action;

import com.java5200.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction3 extends ActionSupport {

	private UserService userService=new UserService();
	private String[] hobby;
	

	public String[] getHobby() {
		return hobby;
	}


	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("Ö´ÐÐHobbyAction·½·¨");
		if(hobby!=null){
			for(String str:hobby){
				System.out.println(str);
			}
		}
		return SUCCESS;
	}

}
