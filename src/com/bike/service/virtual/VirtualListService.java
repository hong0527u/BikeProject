package com.bike.service.virtual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class VirtualListService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		Parking_infoDAO dao = Parking_infoDAO.getInstance();
		
		List<Parking_InfoVO> list = new ArrayList();
		list = dao.parkingList();
		
		request.setAttribute("parking", list);
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/virtual-reality.jsp");
		rd.forward(request, response);
	}
}