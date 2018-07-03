package com.example.domain;

import java.io.Serializable;

public class Relation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8907194024814807035L;
	private String fromUid;
	private String toUid;
	private int rCount;
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
	public int getRCount() {
		return rCount;
	}
	public void setRCount(int rCount) {
		this.rCount = rCount;
	}
	public Relation(String fromUid, String toUid, int rCount) {
		super();
		this.fromUid = fromUid;
		this.toUid = toUid;
		this.rCount = rCount;
	}
	public Relation( String fromUid, String toUid) {
		super();
		this.fromUid = fromUid;
		this.toUid = toUid;
	}
	

}
