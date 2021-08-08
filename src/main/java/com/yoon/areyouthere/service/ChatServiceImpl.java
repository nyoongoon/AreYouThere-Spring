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
	
	public String chatSelect(){
		ArrayList<Chat> chatList = chatDao.getChatListByRecent();
		System.out.println("서비스단- 리스트 갯수" + chatList.size());
		for(Chat chat : chatList) {
			// 데이터 가공
			//chatContent 특수문자 처리
			chat.setChatContent(chat.getChatContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			int chatTime =Integer.parseInt(chat.getChatTime().substring(11, 13));
			String timeType = "오전";
			if(Integer.parseInt(chat.getChatTime().substring(11, 13))>=12){
				timeType = "오후";
				chatTime -= 12;
			}
			//chatTime 날짜 표현 처리
			chat.setChatTime(chat.getChatTime().substring(0, 11) + " " + timeType + " " + chatTime + ":" + chat.getChatTime().substring(14, 16) + "");
		}
		
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if (i != chatList.size() - 1) {
				result.append(", ");
			}
		}
		// 현재 뽑힌 list에서 마지막 챗 객체의 chatId
		System.out.println("현재 뽑힌 list에서 마지막 챗 객체의 chatId " + chatList.get(chatList.size() - 1).getChatID());
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		return result.toString();
	
	}
	public String chatUpdate(String last){
		ArrayList<Chat> chatList = chatDao.getUpdateChatList(last);
		for(Chat chat : chatList) {
			// 데이터 가공
			//chatContent 특수문자 처리
			chat.setChatContent(chat.getChatContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			int chatTime =Integer.parseInt(chat.getChatTime().substring(11, 13));
			String timeType = "오전";
			if(Integer.parseInt(chat.getChatTime().substring(11, 13))>=12){
				timeType = "오후";
				chatTime -= 12;
			}
			//chatTime 날짜 표현 처리
			chat.setChatTime(chat.getChatTime().substring(0, 11) + " " + timeType + " " + chatTime + ":" + chat.getChatTime().substring(14, 16) + "");
		}
		
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if (i != chatList.size() - 1) {
				result.append(", ");
			}
		}
		// 현재 뽑힌 list에서 마지막 챗 객체의 chatId
		System.out.println("현재 뽑힌 list에서 마지막 챗 객체의 chatId " + chatList.get(chatList.size() - 1).getChatID());
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}
	public int chatSubmit(){
		
		return -1;
	}
	

}
