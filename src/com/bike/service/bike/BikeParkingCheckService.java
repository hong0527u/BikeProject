package com.bike.service.bike;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class BikeParkingCheckService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		
		BikeDAO dao = BikeDAO.getInstance();
		String park_code = dao.bikeParkingCheck(lat, lng);
		System.out.println("park"+park_code);
		PrintWriter out = response.getWriter();
		out.print(park_code);
	}
}