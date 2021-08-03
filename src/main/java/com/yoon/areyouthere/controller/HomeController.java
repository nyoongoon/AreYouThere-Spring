package com.yoon.areyouthere.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoon.areyouthere.dto.Chat;
import com.yoon.areyouthere.service.ChatServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ChatServiceImpl chatService;
	
	
 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView select(HttpServletRequest req) { //HttpServletRequest ->커맨드 객체?
		ModelAndView mav = new ModelAndView();
		
		
		ArrayList<Chat> chatList = chatService.chatSelect(Integer.parseInt(req.getParameter("listType"))); // 뷰와 컨트롤러 데이터 주고받기?
		mav.addObject("chatList", chatList);
		mav.setViewName("index");
		
		
		return mav;
	}
	
}
