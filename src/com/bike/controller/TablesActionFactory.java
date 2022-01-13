package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.rtl.RtlListService;
import com.bike.service.signup.SignupListService;
import com.bike.tables.virtual.TablesListService;

public class TablesActionFactory {
	private TablesActionFactory() {}
	private static TablesActionFactory instance = new TablesActionFactory();
	public static TablesActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("tables")) {
			action = new TablesListService();
		}else if(cmd.equals("qa_reply_pro")) {
			
		}else if(cmd.equals("notice_write")) {
		}else if(cmd.equals("notice_write_pro")){
		}else if (cmd.equals("notice_modify")) {
		}else if (cmd.equals("notice_modify_pro")) {
		}else if (cmd.equals("notice_delete_pro")) {
		}else if (cmd.equals("user_list")) {
		}else if (cmd.equals("user_modify")) {
		}else if (cmd.equals("user_modify_pro")) {
		}
		return action;
	}
}