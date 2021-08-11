package com.yoon.areyouthere.dao;

import java.util.ArrayList;

import com.yoon.areyouthere.dto.Chat;

public interface ChatDAO {
	public ArrayList<Chat> getByRecent();
	public ArrayList<Chat> getUpdateChatList(String last);
	public int submit(String name, String content);
	public ArrayList<Chat> refresh();
	
}
