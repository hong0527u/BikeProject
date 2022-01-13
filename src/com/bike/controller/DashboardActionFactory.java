package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.billing.BillingListService;
import com.bike.service.dashboard.DashboardListService;

public class DashboardActionFactory {
	private DashboardActionFactory() {}
	private static DashboardActionFactory instance = new DashboardActionFactory();
	public static DashboardActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("dashboard")) {
			action = new DashboardListService();
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