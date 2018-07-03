package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.domain.UserAuth;
import com.example.service.MessageService;
import com.example.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private MessageService ms;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private HttpServletRequest req;

	//前往注册.html
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(@ModelAttribute() UserAuth ua,BindingResult bindingresult,Model model){
		//绑定失败的话日志记录下
		if(bindingresult.hasErrors()) LOGGER.debug("there was an error "+bindingresult);
		model.addAttribute("userAuth",new UserAuth());
		return "signup";
	}
	//前往登陆.html
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute() UserAuth ua,BindingResult bindingresult,Model model){
		//绑定失败的话日志记录下
		if(bindingresult.hasErrors()) LOGGER.debug("there was an error "+bindingresult);
		model.addAttribute("userAuth",new UserAuth());
		return "login";
	}
	//注册基本信息。。
	@PostMapping("/signup")
	public String register(@ModelAttribute UserAuth ua,Model model){
		int flag = us.register(ua.getUid(),ua.getPassword(), req.getRemoteAddr());
		if(flag!=1){
			model.addAttribute("flag", flag);
			model.addAttribute("password", new BCryptPasswordEncoder(4).encode(ua.getPassword()));
			return "/signup";
		}
		return "redirect:/login"; //+"#"+ua.getUid();
	}
	//登陆验证***********doesn't work at all************
	@PostMapping("/login")
	public String loginAuth(@ModelAttribute UserAuth ua,Model model){
		int flag = us.loginAuth(ua.getUid(), ua.getPassword(), req.getRemoteAddr());
		if(flag!=1){ 
			model.addAttribute("flag", flag);
			return "/login";
		}
		//丢人开始
		//req.getSession().setAttribute("fromUid", ua.getUid());
		return "redirect:/"; //+ua.getUid();
	}
	//匿名用户入口
	@GetMapping("/anonymousDude")
	public String anonymousUser(Model model){
		String ip = req.getRemoteAddr();
		String uid = us.anonyService(ip);
		model.addAttribute("fromUid",uid);
		//丢人开始
		//req.getSession().setAttribute("fromUid", uid);
		return "redirect:/index";
	}
	//个人页面
	@GetMapping("/user/{uid}")
	public String goUserPage(@PathVariable("uid") String uid,Model model){
		// TODO 应跳转至错误页面error.html 提示该用户未注册
		if(!us.isRegistered(uid)) return "redirect:/"; 
		//该uid已注册，可以继续
		List<Message> lm = ms.getTagsByUid(uid);
		//System.out.print("lm is empty"+lm.isEmpty());
		String fromUid = req.getRemoteUser();
		if(fromUid==null){
			fromUid = us.anonyService(req.getRemoteAddr());
		}
		User toUid = us.getUserInfo(uid);
		model.addAttribute("msglist", lm);
		model.addAttribute("fromUid",fromUid);
		model.addAttribute("toU",toUid);
		return "user";
	}
	//鼠标在div上悬停,AJAX请求显示简单用户个人信息
	@GetMapping("/userbio/{uid}")
	@ResponseBody
	public String showUserBio(@PathVariable("uid") String uid){
		return us.getUserBrief(uid);
	}
	//根据keyword搜索用户
	@GetMapping("/search")
	public String showSearchResult(Model model){
		String keyword = (String)req.getAttribute("keyword");
		List<String> luid = us.searchKeyWord(keyword);
		model.addAttribute("luid",luid);
		return "index";
	}
	//进主页index
	@GetMapping({"/index","/"})
	public String toIndexPage(Model model){
		//从Authentication获取principle,即fromUid
		String from = req.getRemoteUser();
		//如果是匿名用户……
		if(from!=null) model.addAttribute("fromUid", from);
		List<String> luid = us.randomUser();
		model.addAttribute("luid", luid);
		return "index";
	}
}
