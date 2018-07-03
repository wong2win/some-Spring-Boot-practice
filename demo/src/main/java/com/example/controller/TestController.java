package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.RelationDao;
import com.example.domain.Relation;

@Controller
public class TestController {
	
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private RelationDao RMapper;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		List<Relation> lr = new ArrayList<Relation>();
		lr.add(new Relation("3号", "3号"));
		lr.add(RMapper.selectR2Uid("1").get(0));
		lr.add(new Relation("2", "2"));
		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("shadiaoList", lr);
		return mav;
	}
}
