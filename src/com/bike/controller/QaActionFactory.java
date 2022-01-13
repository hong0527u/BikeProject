package com.bike.controller;

import com.bike.service.Action;

import com.bike.service.bike.BikeCodeCheckService;
import com.bike.service.bike.BikeCreditService;
import com.bike.service.billing.BillingListService;
import com.bike.service.qa.*;
import com.bike.service.virtual.VirtualListService;

public class QaActionFactory {
	private QaActionFactory() {}
	private static QaActionFactory instance = new QaActionFactory();
	public static QaActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("qa_list")) {
			action = new QaListService();
		}else if(cmd.equals("qa_write")) {
			action = new QaWriteService();
		}else if (cmd.equals("qa_write_pro")) {
			action = new QaWriteProService();
		}else if (cmd.equals("qa_view")){
			action = new QaViewService();
		}else if (cmd.equals("qa_modify")) {
			action = new QaModifyService();
		}else if (cmd.equals("qa_modify_pro")) {
			action = new QaModifyProService();
		}else if (cmd.equals("qa_delete_pro")) {
			action = new QaDeleteService();
		
		}else if (cmd.equals("user_modify")) {
		}else if (cmd.equals("user_modify_pro")) {
		}
		return action;
	}
}