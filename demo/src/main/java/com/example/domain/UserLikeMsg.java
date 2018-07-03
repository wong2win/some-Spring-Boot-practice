package com.example.domain;

import java.io.Serializable;

public class UserLikeMsg implements Serializable{
	/**
	 * GENERATED SERIAL UID
	 */
	private static final long serialVersionUID = 276542077190328952L;
	
	private String uid;
	private int likeFlag;
	private int msgID;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getLikeFlag() {
		return likeFlag;
	}
	public void setLikeFlag(int likeFlag) {
		this.likeFlag = likeFlag;
	}
	public int getMsgID() {
		return msgID;
	}
	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
	public UserLikeMsg() {
		super();
	}
	public UserLikeMsg(String uid, int likeFlag, int msgID) {
		super();
		this.uid = uid;
		this.likeFlag = likeFlag;
		this.msgID = msgID;
	}
	
}
