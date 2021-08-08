package com.yoon.areyouthere.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import com.yoon.areyouthere.dto.Chat;

@Repository
public class ChatDAOImpl implements ChatDAO{

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://3.36.90.150:3306/anonymouschat";
	private String username = "yoon";
	private String password = "tjrqls29";

	private DriverManagerDataSource dataSource; //DataSource 객체 선언
	private JdbcTemplate template;
	
	public ChatDAOImpl(){
		// 생성자에서 DataSource 생성 및 설정.
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClass(driver);
		dataSource.setJdbcUrl(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);

		template = new JdbcTemplate();
		template.setDataSource(dataSource);
	}
	
	 //JDBC Template 객체 선언

	@Override
	public ArrayList<Chat> getChatListByRecent() { // final설정
		ArrayList<Chat> chatList = null;
		//String sql = "SELECT * FROM chat WHERE chatID >(SELECT MAX(chatID) - ? FROM chat) ORDER BY chatTime DESC";
		String sqlex = "SELECT * FROM chat WHERE chatID >(SELECT MAX(chatID) - 5 FROM chat) ORDER BY chatTime DESC";
		chatList = (ArrayList<Chat>) template.query(sqlex, new RowMapper<Chat>(){
				@Override
				public Chat mapRow(ResultSet rs, int rowNum) throws SQLException{
					Chat chat = new Chat();//생성 후 입력, 저장
					chat.setChatID(rs.getInt("chatID"));
					chat.setChatName(rs.getString("chatName")); 
					//데이터 받기만 하고 가공은 서비스단에서 하기.
					chat.setChatContent(rs.getString("chatContent"));
					chat.setChatTime(rs.getString("chatTime"));
					return chat;
				}
			});
		System.out.println("daoTest");
		for(Chat chat : chatList) {
			System.out.print(chat.getChatID());
		}
		System.out.println();
		
		return chatList;
	}

	@Override
	public ArrayList<Chat> getUpdateChatList(final String last) {
		ArrayList<Chat> chatList = null;
		String sqlex = "SELECT * FROM chat WHERE chatID <= ? AND chatId >= ? ORDER BY chatTime DESC";
		chatList = (ArrayList<Chat>) template.query(sqlex, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException{
				pstmt.setInt(1,Integer.parseInt(last)-1);
				pstmt.setInt(2,Integer.parseInt(last)-5);
			}
		}, new RowMapper<Chat>(){
				@Override
				public Chat mapRow(ResultSet rs, int rowNum) throws SQLException{
					Chat chat = new Chat();//생성 후 입력, 저장
					chat.setChatID(rs.getInt("chatID"));
					chat.setChatName(rs.getString("chatName")); 
					//데이터 받기만 하고 가공은 서비스단에서 하기.
					chat.setChatContent(rs.getString("chatContent"));
					chat.setChatTime(rs.getString("chatTime"));
					return chat;
				}
			});
		
		
		return chatList;
	}

	@Override
	public int submit(String chatName, String chatContent) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
