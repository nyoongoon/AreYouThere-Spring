package com.yoon.areyouthere.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;

import com.yoon.areyouthere.dto.Chat;

public class ChatDAOEx extends HttpServlet{
	public Connection conn = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;
	public Statement stmt= null;
	
	public ChatDAOEx() {
		String url = "jdbc:mysql://3.36.90.150:3306/anonymouschat";
		String username = "yoon";
		String password = "tjrqls29";
		try{
			//ServletContext sc = this.getServletContext();
			//conn = (Connection) sc.getAttribute("conn");
			Class.forName("com.mysql.jdbc.Driver");		
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("DB 예외 발생!");
		}
	}
	

	
	
	public ArrayList<Chat> getChatList(String nowTime){
		ArrayList<Chat> chatList = null;
		String SQL = "SELECT * FROM chat WHERE chatTime > ? ORDER BY chatTime DESC";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nowTime);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(Integer.parseInt(rs.getString("chatTime").substring(11, 13))>=12){
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public ArrayList<Chat> getChatListByRecent(int number){
		ArrayList<Chat> chatList = null;
		String quary = "USE anonymouschat";
		String SQL = "SELECT * FROM chat WHERE chatID >(SELECT MAX(chatID) - ? FROM chat) ORDER BY chatTime DESC";
		try {
			stmt = conn.createStatement();
	        stmt.executeUpdate(quary);
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();//생성 후 입력, 저장
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName")); 
				//콘텐트 특수문자 처리
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(Integer.parseInt(rs.getString("chatTime").substring(11, 13))>=12){
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public ArrayList<Chat> getUpdateChatList(String last){
		ArrayList<Chat> chatList = null;
		String SQL = "SELECT * FROM chat WHERE chatID <= ? AND chatId >= ? ORDER BY chatTime DESC";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,Integer.parseInt(last)-1);
			pstmt.setInt(2,Integer.parseInt(last)-5);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(Integer.parseInt(rs.getString("chatTime").substring(11, 13))>=12){
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public int submit(String chatName, String chatContent) {
		String SQL = "INSERT INTO chat(chatName, chatContent, chatTime) VALUES(?, ?, now())";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatContent);
			return pstmt.executeUpdate(); 
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}
