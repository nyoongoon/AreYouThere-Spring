package com.yoon.areyouthere.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yoon.areyouthere.service.ChatServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ChatServiceImpl chatService;
	
	@RequestMapping(value = "/",  method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ModelAndView mav = new ModelAndView();
		String result = chatService.chatSelect(0); 
		System.out.println("controllerTest");
		System.out.println(result);
		mav.addObject(result);
		mav.setViewName("index");
		try {
		response.getWriter().write(result);
		}catch(Exception e) {
			response.getWriter().write("");
		}
		return "index";
		
		
		
	}
	//@RequestMapping(value = "/", method = RequestMethod.GET
	//		)
	@ResponseBody
	public ModelAndView select(HttpServletRequest req) { //HttpServletRequest ->커맨드 객체?
		ModelAndView mav = new ModelAndView();
		
		System.out.println(req.getParameter("listType"));
		//ArrayList<Chat> chatList = chatService.chatSelect(Integer.parseInt(req.getParameter("listType"))); // 뷰와 컨트롤러 데이터 주고받기?
		//mav.addObject("chatList", chatList);
		mav.setViewName("index");
		
		
		return mav;
	}
	
}
