package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.domain.UserAuth;

@Mapper
public interface UserDao {
	/**
	 * 检查IP是否有登录记录,可能为空？
	 * @return uid
	 */
	@Select("select uid from UserIPTable where ip=#{ip}")
	String checkIPloginRecord(String ip);
	
	/**
	 * 检查是否注册用户,可能为空
	 * @return uid
	 */
	@Select("select uid from UserAuth where uid=#{uid}")
	String checkRegistered(String uid);
	/**
	 * 给spring security用的
	 * @param uid
	 * @return
	 */
	@Select("select uid,password from UserAuth where uid=#{uid}")
	@Results({
        @Result(property = "uid", column = "uid"),
        @Result(property = "password", column = "password"),
	})
	UserAuth getUserAuth(String uid);
	
	/**
	 * 插入IP记录
	 */
	@Insert("insert into UserIPTable(uid,IP) values(#{uid},#{IP})")
	int newUserIP(@Param("uid") String uid,@Param("IP") String IP);
	
	/**
	 * 更新IP记录中的uid
	 */
	@Update("update UserIPTable set uid=#{uid} where IP=#{IP}")
	int updateIPuid(@Param("uid") String uid,@Param("IP") String IP);
	
	/**
	 * 注册用户登陆,可能为空
	 * @return uid
	 */
	@Select("select uid,password from UserAuth where uid=#{uid} and password=#{password}")
	UserAuth loginAuth(@Param("uid") String uid,@Param("password") String password);
	/**
	 * 注册用户,基本信息
	 */
	@Insert("insert into UserInfo(uid,email,nickname) values(#{uid},#{email},#{nickname}) ")
	int newRegisteredUserInfo(@Param("uid") String uid,@Param("email") String email,@Param("nickname") String nickname);
	
	/**
	 * 注册用户,账号密码
	 */
	@Insert("insert into UserAuth(uid,password) values(#{uid},#{password}) ")
	int newUserAuth(@Param("uid") String uid,@Param("password") String password);
	
	/**
	 * 匿名用户,基本信息
	 */
	@Insert("insert into UserInfo(uid) values(#{uid})")//,#{nickname}) ")
	int newAnonymousUserInfo(String uid);//,@Param("nickname") String nickname);
	/**
	 * 获取用户个人信息
	 */
	@Select("select * from userinfo where uid = #{uid}")
	User getUserInfo(String uid);
	/**
	 * 获取用户简评
	 */
	@Select("select toUid,mtext,likesCount from message where toUid = ${uid} order by likesCount")
	List<Message> getUserBrief(String uid);
	/**
	 * 在用户id中模糊查询
	 */
	@Select("select uid from userinfo where uid like '%${keyword}%'")
	List<String> searchByUid(String keyword);
	/**
	 * 在tag中模糊查询
	 */
	@Select("select toUid from message where istag=1 and mtext like '%${keyword}%'")
	List<String> searchByTag(String keyword);
	/**
	 * 在留言表选择被评价次数最少的人
	 */
	@Select("select toUid from message group by toUid order by count(toUid) limit 2")
	List<String> randomUser();
}
