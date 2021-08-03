package com.yoon.areyouthere.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoon.areyouthere.dao.ChatDAOImpl;
import com.yoon.areyouthere.dto.Chat;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatDAOImpl chatDao;
	
	public ArrayList<Chat>chatSelect(int number){
		ArrayList<Chat> chatList = chatDao.getChatListByRecent(number);
		for(Chat chat : chatList) {
			// 데이터 가공
			chat.setChatContent(chat.getChatContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			int chatTime =Integer.parseInt(chat.getChatTime().substring(11, 13));
			String timeType = "오전";
			if(Integer.parseInt(chat.getChatTime().substring(11, 13))>=12){
				timeType = "오후";
				chatTime -= 12;
			}
			chat.setChatTime(chat.getChatTime().substring(0, 11) + " " + timeType + " " + chatTime + ":" + chat.getChatTime().substring(14, 16) + "");
		}
		
		return chatList;
	}
	public ArrayList<Chat>chatUpdate(){
		
		return null;
	}
	public int chatSubmit(){
		
		return -1;
	}
	

}
