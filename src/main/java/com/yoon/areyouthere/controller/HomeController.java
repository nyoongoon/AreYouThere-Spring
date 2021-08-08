package com.yoon.areyouthere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yoon.areyouthere.service.ChatServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ChatServiceImpl chatService;
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String home(Model model){
		
		String result = chatService.chatSelect(); 
		System.out.println("controllerTest");
		System.out.println(result);
		model.addAttribute("result", result);
		
		
		return "index";
		
	}
	/*
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public String update(HttpServletRequest request, Model model){
		System.out.println("post요청 받음");
		String last = (String)request.getAttribute("lastId"); //안담김
		String result = chatService.chatUpdate(last); 
		System.out.println("UpdateControllerTest");
		System.out.println("UpdateResult" + result);
		model.addAttribute("result", result);
		
		
		return "index";
		
	}
	*/	
}
