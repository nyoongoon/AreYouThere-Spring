package com.yoon.areyouthere.service;

import java.util.ArrayList;

import com.yoon.areyouthere.dto.Chat;

public interface ChatService {
	public ArrayList<Chat>chatSelect(int number);
	public ArrayList<Chat>chatUpdate();
	public int chatSubmit();
}
