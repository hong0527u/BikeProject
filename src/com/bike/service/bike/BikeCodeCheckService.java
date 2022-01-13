package com.bike.service.bike;

import java.io.IOException;
import java.io.PrintWriter;
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

public class BikeCodeCheckService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bikecode = request.getParameter("bikecode");
		BikeDAO dao = BikeDAO.getInstance();
		int row = dao.bikeCodeCheck(bikecode);
		
		PrintWriter out = response.getWriter();
		out.print(row);
	}
}