package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.billing.BillingListService;
import com.bike.service.profile.ProfileListService;
import com.bike.service.user.UserPointService;

public class ProfileActionFactory {
	private ProfileActionFactory() {}
	private static ProfileActionFactory instance = new ProfileActionFactory();
	public static ProfileActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("profile")) {
			action = new ProfileListService();
		}else if (cmd.equals("point")) {
			action = new UserPointService();
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