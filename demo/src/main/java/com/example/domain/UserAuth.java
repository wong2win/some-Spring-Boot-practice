package com.example.domain;

import java.io.Serializable;

public class UserAuth implements Serializable{

	private static final long serialVersionUID = 8488396745990288686L;
	private String uid;
	private String password;
	
	//******Getters,Setters******
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//******构造方法******
	public UserAuth() {
		super();
	}
	public UserAuth(String uid, String password) {
		super();
		this.uid = uid;
		this.password = password;
	}
	public UserAuth(String uid) {
		super();
		this.uid = uid;
	}
	
}
