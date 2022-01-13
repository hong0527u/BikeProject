package com.bike.domain.parking;

import java.io.Serializable;

public class Parking_InfoVO implements Serializable{
	private String park_code;
	private String park_name;
	private String park_area;
	private int park_startrow;
	private int park_parkrow;
	private int park_bike;
	private int park_pay;
	private double park_locx;
	private double park_locy;
	
	public String getPark_code() {
		return park_code;
	}
	public void setPark_code(String park_code) {
		this.park_code = park_code;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getPark_area() {
		return park_area;
	}
	public void setPark_area(String park_area) {
		this.park_area = park_area;
	}
	public int getPark_startrow() {
		return park_startrow;
	}
	public void setPark_startrow(int park_startrow) {
		this.park_startrow = park_startrow;
	}
	public int getPark_parkrow() {
		return park_parkrow;
	}
	public void setPark_parkrow(int park_parkrow) {
		this.park_parkrow = park_parkrow;
	}
	public int getPark_bike() {
		return park_bike;
	}
	public void setPark_bike(int park_bike) {
		this.park_bike = park_bike;
	}
	public int getPark_pay() {
		return park_pay;
	}
	public void setPark_pay(int park_pay) {
		this.park_pay = park_pay;
	}
	public double getPark_locx() {
		return park_locx;
	}
	public void setPark_locx(double park_locx) {
		this.park_locx = park_locx;
	}
	public double getPark_locy() {
		return park_locy;
	}
	public void setPark_locy(double park_locy) {
		this.park_locy = park_locy;
	}
}
