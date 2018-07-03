package com.example.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.domain.UserAuth;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//System.out.print("username = " + username);
		UserAuth ua = userDao.getUserAuth(username);
		if(null==ua) return null; 
		GrantedAuthority authority = new SimpleGrantedAuthority("USER");
		UserDetails userDetails = (UserDetails)new User(ua.getUid(),ua.getPassword(),Arrays.asList(authority));
		return userDetails;
	}

}
