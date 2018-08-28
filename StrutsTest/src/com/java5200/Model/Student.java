package com.java5200.Model;

/**
 * 
 * @author CSH
 * @date 2017年12月15日 下午9:32:54
 */
public class Student {

	private String name;
	private String age;
	private String sex;

	/*
	 * private boolean tree;
	 * 
	 * public boolean isTree() { return tree; }
	 * 
	 * public void setTree(boolean tree) { this.tree = tree; }
	 */

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String name, String age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}

}
