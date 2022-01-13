package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.rtl.RtlListService;
import com.bike.service.signup.SignupListService;

public class RtlActionFactory {
	private RtlActionFactory() {}
	private static RtlActionFactory instance = new RtlActionFactory();
	public static RtlActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("rtl")) {
			action = new RtlListService();
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