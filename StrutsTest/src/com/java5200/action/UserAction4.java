package com.java5200.action;

import java.util.List;

import com.java5200.Model.Student;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction4 extends ActionSupport {

	private List<Student> students;
	
	
	public List<Student> getStudents() {
		return students;
	}


	public void setStudents(List<Student> students) {
		this.students = students;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("Ö´ÐÐstudentAction·½·¨");
		//System.out.println("true");
		for(Student s:students){
			System.out.println(s);
		}
		return SUCCESS;
	}

}	
