package com.example.service;

import java.util.List;

import com.example.domain.Message;

public interface MessageService {
	/**
	 * 根据uid获得tags
	 * @param uid
	 * @return tag列表
	 */
	public List<Message> getTagsByUid(String uid);
	/**
	 * 根据tagID获得comments
	 * @param tid TagID
	 * @return comment列表
	 */
	public List<Message> getCmntsByTid(int tid);
	/**
	 * 新留言
	 * 根据isTag,refMsgID,reply2MsgID判断留言类型
	 * @return 
	 */
	public int newMessage(Message m);
	/**
	 * like/undo功能,不优雅
	 */
	public void likeMSG(String fromUid,int msgID,boolean likeFlag);
	/**
	 * 根绝msgID隐藏/显示message
	 */
	public void hideMSG(int msgID);
	/**
	 * 响应Ajax请求 显示评论列表
	 */
	public String showCmnts(int tid);
	List<Message> showCmnt(int tid);

}