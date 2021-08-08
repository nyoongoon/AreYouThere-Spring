package com.yoon.areyouthere.service;

import java.util.ArrayList;

import com.yoon.areyouthere.dto.Chat;

public interface ChatService {
	public String chatSelect();
	public String chatUpdate(String last);
	public int chatSubmit();
}
