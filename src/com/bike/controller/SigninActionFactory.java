package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.signin.*;

public class SigninActionFactory {
	private SigninActionFactory() {}
	private static SigninActionFactory instance = new SigninActionFactory();
	public static SigninActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("signin")) {
			action = new SigninListService();
		}else if(cmd.equals("user_login_pro")) {
			action = new SigninProService();
		}else if(cmd.equals("user_logout")) {
			action = new SignOutService();
		}else if(cmd.equals("notice_write_pro")){
		}else if (cmd.equals("notice_modify")) {
		}else if (cmd.equals("notice_modify_pro")) {
		}
		return action;
	}
}