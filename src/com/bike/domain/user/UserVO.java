package com.bike.domain.user;

public class UserVO {
	/*
	USER_ID VARCHAR2(20 BYTE) primary key, 
	USER_PASSWD VARCHAR2(200 BYTE), 
	USER_NAME VARCHAR2(20 BYTE), 
	USER_REGDATE DATE, 
	USER_LASTTIME DATE, 
	USER_TEL VARCHAR2(20 BYTE), 
	USER_COST VARCHAR2(20 BYTE), 
	USER_EMAIL VARCHAR2(20 BYTE), 
	USER_BIRTH
	*/
	
	private String user_id;
	private String user_passwd;
	private String user_name;
	private String user_regdate;
	private String user_lasttime;
	
	private String user_tel;
	private String user_cost;
	private String user_email;
	private String user_birth;
	private String user_roll;
	
	public String getUser_roll() {
		return user_roll;
	}
	public void setUser_roll(String user_roll) {
		this.user_roll = user_roll;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_passwd() {
		return user_passwd;
	}
	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_regdate() {
		return user_regdate;
	}
	public void setUser_regdate(String user_regdate) {
		this.user_regdate = user_regdate;
	}
	public String getUser_lasttime() {
		return user_lasttime;
	}
	public void setUser_lasttime(String user_lasttime) {
		this.user_lasttime = user_lasttime;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_cost() {
		return user_cost;
	}
	public void setUser_cost(String user_cost) {
		this.user_cost = user_cost;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}
	
		
	
}
