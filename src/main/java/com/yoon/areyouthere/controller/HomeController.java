package com.yoon.areyouthere.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ResponseBody
	@RequestMapping(value = "/update", produces = "application/text; charset=utf8", method=RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update요청 받음");
		String last = request.getParameter("lastId"); 
		System.out.println("last값 확인");
		System.out.println(last);
		
		String result = chatService.chatUpdate(last); 
		System.out.println("UpdateControllerTest");
		System.out.println("UpdateResult -> " + result);
		
		
		
		
		return result;
		
	}
	
	@ResponseBody //JSON으로 보내느 것이니까 숫자를 보내더라도 String으로 변환시켜서 보내야함!!
	@RequestMapping(value = "/submit", method=RequestMethod.POST)
	public String submit(HttpServletRequest request, HttpServletResponse response){
		
		String chatName = request.getParameter("chatName");
		String chatContent=request.getParameter("chatContent");
		System.out.println("submitTest = " + chatName + " : " + chatContent);
		if(chatName == null || chatName.equals("") || chatContent==null || chatContent.equals("")) {
			System.out.println("전송 실패시 출력값");
			return "0";
			
		}else {
			//response.getWriter().write(Integer.toString(new ChatDAO().submit(chatName, chatContent)));
			int result = chatService.chatSubmit(chatName, chatContent);
			System.out.println("전송 성공시 출력값 : " + result);
			return Integer.toString(result);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/refresh", produces = "application/text; charset=utf8", method=RequestMethod.POST)
	public String refresh() {
		System.out.println("컨트롤러에서 refresh");
		String result = chatService.chatRefresh();
		System.out.println("컨트롤러에서 result");
		System.out.println(result);
		
		
		
		
		return result;
		
	}

}
