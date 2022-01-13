package com.bike.controller;

import com.bike.service.Action;
import com.bike.service.bike.BikeCodeCheckService;
import com.bike.service.bike.BikeCreditService;
import com.bike.service.bike.BikeParkingCheckService;
import com.bike.service.bike.BikeParkingService;
import com.bike.service.bike.BikeRentalService;
import com.bike.service.bike.BikeViewService;
import com.bike.service.fix.FixInsertProService;
import com.bike.service.fix.FixInsertService;
import com.bike.service.virtual.QrCehckService;
import com.bike.service.virtual.VirtualListService;

public class VitualActionFactory {
	private VitualActionFactory() {}
	private static VitualActionFactory instance = new VitualActionFactory();
	public static VitualActionFactory getInstace() {
		return instance;
	}
	public Action getAction(String cmd) {
		Action action = null;
		if(cmd.equals("virtual")) {
			action = new VirtualListService();
		}else if(cmd.equals("code_check")) {
			action = new BikeCodeCheckService();
		}else if (cmd.equals("bike_credit")) {
			action = new BikeCreditService();
		}else if (cmd.equals("bike_info")){
			action = new BikeViewService();
		}else if(cmd.equals("fix_insert")) {
			action = new FixInsertService();
		}else if(cmd.equals("fix_insert_pro")) {
			action = new FixInsertProService();
		}else if (cmd.equals("park_check")) {
			action = new BikeParkingCheckService();
		}else if (cmd.equals("parking")) {
			action = new BikeParkingService();
		}else if (cmd.equals("rental")) {
			action = new BikeRentalService();
		}else if (cmd.equals("qr_check")) {
			action = new QrCehckService();
		}else if (cmd.equals("user_modify_pro")) {
		}
		return action;
	}
}