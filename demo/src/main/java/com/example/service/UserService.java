package com.example.service;

import java.util.List;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.domain.UserAuth;

public interface UserService {
	/**
	 * spring security 的例行检查
	 * 暂时停用 
	 */
	public void gatekeeper(String uid);
	
	/**
	 * 登录验证
	 */
	public int loginAuth(String uid,String pw,String ip);
	
	/**
	 * 注册新账号
	 */
	public int register(String uid,String pw,String ip);
	
	/**
	 * 补全用户详细信息
	 */
	public int registerUinfo(User u);
	
	/**
	 * 匿名用户IP注册
	 */
	public String anonyService(String ip);
	
	/**
	 * 您注册了吗？
	 */
	public boolean isRegistered(String uid);
	
	/**
	 * 获取用户信息
	 */
	public User getUserInfo(String uid);
	
	/**
	 * IP是否已注册
	 */
	public boolean isRegisteredIP(String ip);
	/**
	 * 获取用户个人简介
	 */
	public String getUserBrief(String uid);
	/**
	 * 关键字搜索
	 * 返回所有可能关联用户
	 */
	public List<String> searchKeyWord(String keyword);
	/**
	 * 随便给几个用户id把我打发了吧
	 */
	public List<String> randomUser();
}
