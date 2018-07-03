package com.example.service;

public interface LikeService {
	public boolean getLikeData(String uid,String msgID);
	public void putLikeData(String uid,String msgID);
	public void postLikeData(String uid,String msgID);
	public void deleteLikeData(String uid,String msgID);
}
