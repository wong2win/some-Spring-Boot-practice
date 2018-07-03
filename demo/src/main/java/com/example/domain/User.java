package com.example.domain;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -8627131595598550930L;
	private String uid;
	private String nickname;
	private String email;
	private String location;
	private String school;
	private String phone;
	private String links;

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public User(String uid, String nickname, String email) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.email = email;
	}
	public User(String uid, String nickname, String email, String links) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.email = email;
		this.links = links;
	}
	public User(String uid, String nickname, String email, String phone, String links) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.links = links;
	}
	public User(String uid, String nickname, String email, String location, String school, String phone, String links) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.email = email;
		this.location = location;
		this.school = school;
		this.phone = phone;
		this.links = links;
	}
	
	
}
