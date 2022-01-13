package com.bike.service.fix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.service.Action;

public class FixInsertService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bike_code = request.getParameter("bike_code");
		String pages = request.getParameter("pages");
		
		FixDAO dao = FixDAO.getInstance();
		BikeVO vo = dao.bikeSelect(bike_code);
		
		request.setAttribute("pages", pages);
		request.setAttribute("vo", vo);
		RequestDispatcher rd = request.getRequestDispatcher("/pages/fix_insert.jsp");
		rd.forward(request, response);
	}

}
