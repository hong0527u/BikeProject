package com.bike.service.parking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class ParkingNameCheckService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String park_name = request.getParameter("park_name");
		Parking_infoDAO dao = Parking_infoDAO.getInstance();
		int row= dao.parkCheck(park_name);
		
		PrintWriter out = response.getWriter();
		out.print(row);
		
	}

}
