package com.bike.service.bike;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.service.Action;

public class BikeDeleteProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		String code = request.getParameter("bikecode");
		String parkcode = request.getParameter("parkcode");
		
		BikeDAO dao = BikeDAO.getInstance();
		int row = dao.bikeDelete(code, parkcode);
		
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin?cmd=bike_list");
		rd.forward(request, response);
	}
}