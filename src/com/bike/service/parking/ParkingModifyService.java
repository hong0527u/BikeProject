package com.bike.service.parking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class ParkingModifyService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String park_code =request.getParameter("park_code"); 
		
		
		Parking_infoDAO dao = Parking_infoDAO.getInstance();
		Parking_InfoVO vo = dao.parkingSelect(park_code);

		request.setAttribute("code", vo);
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/parking_modify.jsp");
		rd.forward(request, response);
	}

}
