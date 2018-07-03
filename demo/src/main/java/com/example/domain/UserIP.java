package com.example.domain;

public class UserIP {
	
	private String uid;
	private String ip;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	//**************构造***************
	public UserIP() {
		super();
	}
	public UserIP(String uid, String ip) {
		super();
		this.uid = uid;
		this.ip = ip;
	}
	public UserIP(String uid) {
		super();
		this.uid = uid;
	}
}
