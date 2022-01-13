package com.bike.service.parking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class ParkingDeleteService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String park_code = request.getParameter("park_code");

		Parking_infoDAO dao = Parking_infoDAO.getInstance();
		int row = dao.ParkingDelete(park_code);

		request.setAttribute("row", row);
		RequestDispatcher rd = request.getRequestDispatcher("Admin/parking_delete_pro.jsp");
		rd.forward(request, response);
	}

}
