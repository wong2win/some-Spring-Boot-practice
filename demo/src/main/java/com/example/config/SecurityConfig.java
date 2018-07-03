package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//import com.example.filter.CustomAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAppUserDetailsService myAppUserDetailsService;
	
	//@Autowired
	//private MyAppAuthenticationEntryPoint appAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests()
		    	.antMatchers("/signup","/register","/about","/resource/**").permitAll()
			    .antMatchers("/user**","/").permitAll()//hasRole("USER")
			    .antMatchers("/api/tags**","/api/comments**").permitAll()
			    .antMatchers("/api/message**").hasRole("USER")
			    .and()
			.formLogin()
				.loginPage("/login").permitAll()
				.and();
	} 
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
                auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
	}
//    @Bean
//    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
//        filter.setAuthenticationFailureHandler(new FailureHandler());
//        filter.setFilterProcessesUrl("/login/self");
//
//        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
}
