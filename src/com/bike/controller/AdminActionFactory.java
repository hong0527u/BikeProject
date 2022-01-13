package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.bike.*;
import com.bike.service.cor.*;
import com.bike.service.fix.*;
import com.bike.service.graph.*;
import com.bike.service.parking.*;
import com.bike.service.qa.*;
import com.bike.service.rental.RentalListService;
import com.bike.service.user.UserListService;

public class AdminActionFactory {
	private AdminActionFactory() {}
	private static AdminActionFactory instance = new AdminActionFactory();
	public static AdminActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("cor_insert")) {
			action = new CorInsertService();
		}else if (cmd.equals("cor_insert_pro")) {
			action = new CorInsertProService();
		}else if (cmd.equals("cor_list")) {
			action = new CorListService();
		}else if (cmd.equals("cor_modify")){
			action = new CorModifyService();
		}else if (cmd.equals("cor_modify_pro")) {
			action = new CorModifyProService();
		}else if (cmd.equals("cor_del")) {
			action = new BikeDeleteProService();
		}else if (cmd.equals("bike_buy")) {
			action = new BikeInsertService();
		}else if (cmd.equals("bike_buy_pro")) {
			action = new BikeInsertProService();
		}else if (cmd.equals("parking_insert")) {
			action = new ParkingInsertService();
		}else if(cmd.equals("parking_insert_pro")) {
			action = new ParkingInsertProService();
		}else if(cmd.equals("parking_modify")) {
			action = new ParkingModifyService();
		}else if(cmd.equals("parking_modify_pro")){
			action = new ParkingModifyProService();
		}else if(cmd.equals("parking_list")) {
			action = new ParkingListService();
		}else if(cmd.equals("parking_delete_pro")) {
			action = new ParkingDeleteService();
		}else if(cmd.equals("parking_name_check")) {
			action = new ParkingNameCheckService();
		}else if(cmd.equals("buy_list")) {
			action = new BuyListService();
		}else if (cmd.equals("buy_list_con")) {
			action = new BuyListConService();
		}else if (cmd.equals("bike_list")) {
			action = new BikeListService();
		}else if (cmd.equals("bike_del")) {
			action = new BikeDeleteProService();
		}else if (cmd.equals("qa_reply")) {
			action = new QaReplyService();
		}else if (cmd.equals("qa_reply_pro")) {
			action = new QaReplyProService();
		}else if(cmd.equals("fix_list")) {
			action = new FixListService();
		}else if(cmd.equals("fix_view")) {
			action = new FixViewService();
		}else if(cmd.equals("graph")) {
			action = new GraphService();
		}else if (cmd.equals("fix_end_pro")) {
			action = new FixEndProService();
		}else if (cmd.equals("fix_delete")) {
			action = new FIxDeleteProService();
		}else if (cmd.equals("user_list")) {
			action = new UserListService();
		}else if (cmd.equals("rental_list")) {
			action = new RentalListService();
		}else if(cmd.equals("bike_modify")) {
			action = new BikeModifyService();
		}else if (cmd.equals("bike_modify_pro")) {
			action = new BikeModifyProService();
		}
		return action;
	}
}