package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.signup.*;

public class SignupActionFactory {
	private SignupActionFactory() {}
	private static SignupActionFactory instance = new SignupActionFactory();
	public static SignupActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("signup")) {
			action = new SignupListService();
		}else if(cmd.equals("user_insert_pro")) {
			action = new SignupProService();
		}else if(cmd.equals("user_idcheck")) {
			action = new SignupIdCheckService();
		}else if(cmd.equals("user_email_check")){
			action = new SignupEmailCheckService();
		}else if (cmd.equals("user_modify")) {
			action = new SignModifyService();
		}else if (cmd.equals("user_modify_pro")) {
			action = new SignModifyProService();
		}else if (cmd.equals("user_del_pro")) {
			action = new SignDelService();
		}else if (cmd.equals("user_list")) {
		}else if (cmd.equals("user_modify")) {
		}else if (cmd.equals("user_modify_pro")) {
		}
		return action;
	}
}