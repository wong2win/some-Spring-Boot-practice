package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.domain.Message;
import com.example.service.MessageService;

@RestController
public class MessageRestController {
	
	@Autowired
	private MessageService messageService;
	
	/** ***************HIGH LIGHT*******************
	*req.getRemoteUser() will return the result of 
	*SecurityContextHolder.getContext().getAuthentication().getName() 
	*which is typically the current username.
	*/
	@Autowired
	private HttpServletRequest req;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageRestController.class);
	
	@GetMapping("/api/tags/{uid}")
	public List<Message>getTags(@PathVariable("uid") String uid){
		return messageService.getTagsByUid(uid);
	}
	
	@GetMapping("/api/comments/{tid}")
	public List<Message>getCommentss(@PathVariable("tid") int tid){
		return messageService.getCmntsByTid(tid);
	}
	//新评论
	@PostMapping(path="/api/newComment", consumes = "application/json" )
	public int newMessage(@RequestBody Message message){
		int flag = messageService.newMessage(message);
		return flag;
	}
	//Ajax请求处理
	@GetMapping(path="/showComments/{tid}")
	@ResponseBody
	public String showComments(@PathVariable("tid") int tid){
		return messageService.showCmnts(tid);
		//return messageService.showCmnt(tid);
	}
	//like
	@GetMapping("/likeMsg/{msgID}")
	public void likeMessage(@PathVariable("msgID") int msgID){
		//boolean likeFlag = (int)req.getAttribute("likeFlag")==0;
		messageService.likeMSG(req.getRemoteUser(), msgID, true);
	}
	@GetMapping("/hideMsg/{msgID}")
	public void hideMessage(@PathVariable("msgID") int msgID){
		messageService.hideMSG(msgID);
	}
}
