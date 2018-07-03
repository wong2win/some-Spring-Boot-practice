package com.example.domain;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7273740515623934624L;
	
	private int msgID;
	private String mtext;
	private int likesCount;
	private int isTag;
	private int visibility;
	private int refMessageID;
	private int reply2MsgID;
	private int msgType;
	private String fromUid;
	private String toUid;
	
	public int getMsgID() {
		return msgID;
	}
	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
	public String getMtext() {
		return mtext;
	}
	public void setMtext(String text) {
		this.mtext = text;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
	public int getIsTag() {
		return isTag;
	}
	public void setIsTag(int isTag) {
		this.isTag = isTag;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public int getRefMessageID() {
		return refMessageID;
	}
	public void setRefMessageID(int refMessageID) {
		this.refMessageID = refMessageID;
	}
	public int getReply2MsgID() {
		return reply2MsgID;
	}
	public void setReply2MsgID(int reply2MsgID) {
		this.reply2MsgID = reply2MsgID;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getFromUid() {
		return fromUid;
	}
	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}
	public String getToUid() {
		return toUid;
	}
	public void setToUid(String toUid) {
		this.toUid = toUid;
	}
	public Message() {
		super();
	}
	public Message(int msgID, String mtext, int likesCount, int isTag, int refMessageID, int reply2MsgID, int msgType,
			String fromUid, String toUid, int visibility) {
		super();
		this.msgID = msgID;
		this.mtext = mtext;
		this.likesCount = likesCount;
		this.isTag = isTag;
		this.refMessageID = refMessageID;
		this.reply2MsgID = reply2MsgID;
		this.msgType = msgType;
		this.fromUid = fromUid;
		this.toUid = toUid;
		this.visibility = visibility;
	}
	/**
	 * tag用构造
	 */
	public Message(String mtext, int isTag, int msgType, String fromUid, String toUid) {
		super();
		this.mtext = mtext;
		this.isTag = isTag;
		this.msgType = msgType;
		this.fromUid = fromUid;
		this.toUid = toUid;
	}
	/**
	 * 回复tag的评论用构造
	 */
	public Message(String mtext, int isTag, int refMessageID, int msgType, String fromUid, String toUid) {
		super();
		this.mtext = mtext;
		this.isTag = isTag;
		this.refMessageID = refMessageID;
		this.msgType = msgType;
		this.fromUid = fromUid;
		this.toUid = toUid;
	}
	/**
	 * 回复评论的评论构造
	 */
	public Message(String mtext, int isTag, int refMessageID, int reply2MsgID, int msgType, String fromUid, String toUid) {
		super();
		this.mtext = mtext;
		this.isTag = isTag;
		this.refMessageID = refMessageID;
		this.reply2MsgID = reply2MsgID;
		this.msgType = msgType;
		this.fromUid = fromUid;
		this.toUid = toUid;
	}
	/**
	 * 个人简介用tag构造
	 */
	public Message(String toUid, String mtext, int likesCount){
		super();
		this.toUid = toUid;
		this.mtext = mtext;
		this.likesCount = likesCount;
	}
}
