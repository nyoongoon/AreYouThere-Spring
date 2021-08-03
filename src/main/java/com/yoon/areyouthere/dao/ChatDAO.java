package com.yoon.areyouthere.dao;

import java.util.ArrayList;

import com.yoon.areyouthere.dto.Chat;

public interface ChatDAO {
	public ArrayList<Chat> getChatListByRecent(int number);
	public ArrayList<Chat> getUpdateChatList(String last);
	public int submit(String chatName, String chatContent);

}
