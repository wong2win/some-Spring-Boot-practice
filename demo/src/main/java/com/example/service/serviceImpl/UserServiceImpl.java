package com.example.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDao;
import com.example.domain.Message;
import com.example.domain.User;
import com.example.domain.UserAuth;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	//用户id正则：1到16位字母数字下划线
	private static Pattern uidValid = Pattern.compile("^[a-zA-Z_0-9]{1,16}$");
	//用户注册密码正则：6到24位字母数字下划线
	private static Pattern pwValid = Pattern.compile("^[a-zA-Z_0-9]{6,24}$");
	//密码加密
	private static BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(4);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void gatekeeper(String uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public int loginAuth(String uid,String pw,String ip) {
		//uid验证不合法
		if(!uidValid.matcher(uid).matches()) return 2;
		UserAuth ua = userDao.loginAuth(uid, bcryptEncoder.encode(pw));
		String uidInIPTable = userDao.checkIPloginRecord(ip);//查看此ip是否登入过
		if(null!=ua){
			if(uidInIPTable==null) userDao.newUserIP(uid, ip); //该IP不在表中
			return 1; //正常登陆
		}
		return 0; //登录信息有误
	
	}

	@Override
	@Transactional
	public int register(String uid, String pw,String ip) {
		if(!pwValid.matcher(pw).matches()) return 3;//密码不可用
		if(!uidValid.matcher(uid).matches() || isRegistered(uid)) return 0;//该uid不可用
		if(null!=userDao.checkIPloginRecord(ip)) return 2;//该ip已注册过
		userDao.newAnonymousUserInfo(uid);
		userDao.newUserIP(uid, ip);
		userDao.newUserAuth(uid, bcryptEncoder.encode(pw));
		return 1;
	}
	
	public int registerUinfo(User u){
		//if(); //regex check
		int flag = userDao.newRegisteredUserInfo(u.getUid(), u.getEmail(), u.getNickname());
		return flag;
	}

	@Override
	public boolean isRegistered(String uid) {
		return null!=userDao.checkRegistered(uid);
	}
	
	@Override
	public boolean isRegisteredIP(String ip) {
		if(null!=userDao.checkIPloginRecord(ip)) return true;
		else return false;
	}

	@Override
	@Transactional
	public String anonyService(String ip) {
		//int flag = 0;
		String uidInIPTable = userDao.checkIPloginRecord(ip);
		if(null!=uidInIPTable) return uidInIPTable;
		String newUid = System.currentTimeMillis()+"uid";
		userDao.newAnonymousUserInfo(newUid);
		userDao.newUserIP(newUid, ip);
		return newUid;
	}

	@Override
	public User getUserInfo(String uid) {
		// TODO Auto-generated method stub
		User u = userDao.getUserInfo(uid);
		return u;
	}
	
	// Ajax port1
	@Override
	public String getUserBrief(String uid){
		//匿名用户
		if(!isRegistered(uid)) return "<div class='profile'>匿名用户</div>";
		//注册用户
		List<Message> lm = userDao.getUserBrief(uid);
		StringBuffer profile = new StringBuffer("<div class='profile'>"
											   +"<a href='/user/"+uid+"'>"+uid+"</a>");
		for(Message m : lm){
			profile.append("<div class='tags'>"+m.getMtext()+"</div>");
		}
		profile.append("</div>");
		return profile.toString();
	}

	@Override
	public List<String> searchKeyWord(String keyword) {
		// TODO 
		List<String> lu_1 = userDao.searchByUid(keyword);
		List<String> lu = new ArrayList<String>(lu_1);
		List<String> lu_2 = userDao.searchByTag(keyword);
		lu.removeAll(lu_2);
		lu.addAll(lu_2);
		return lu;
	}

	@Override
	public List<String> randomUser() {
		// TODO Auto-generated method stub
		List<String> lu = userDao.randomUser();
		return lu;
	}

}
