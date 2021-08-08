package com.yoon.areyouthere.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yoon.areyouthere.service.ChatServiceImpl;

@Controller
public class UpdateController {
	@Autowired
	private ChatServiceImpl chatService;
	
	@RequestMapping(value = "/update", method=RequestMethod.GET)
	public String update(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException{
		System.out.println("update요청 받음");
		request.setCharacterEncoding("UTF-8");
		String last = request.getParameter("lastId"); //안담김 -> null임
		System.out.println("last값 확인");
		System.out.println(last);
		
		String result = chatService.chatUpdate(last); 
		System.out.println("UpdateControllerTest");
		System.out.println("UpdateResult -> " + result);
		model.addAttribute("result", result);
		
		
		
		return "index";
		
	}
}
