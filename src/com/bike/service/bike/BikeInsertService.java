package com.bike.service.bike;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.cor.CorDAO;
import com.bike.domain.cor.CorVO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class BikeInsertService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		String cmd = request.getParameter("cmd");
		request.setAttribute("cmd", cmd);
		CorDAO dao = CorDAO.getInstance();
		List<CorVO> list = dao.corList();
		Parking_infoDAO pdao = Parking_infoDAO.getInstance();
		List<Parking_InfoVO> plist = pdao.parkingList();
		
		request.setAttribute("cor", list);
		request.setAttribute("park", plist);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/bike_buy.jsp");
		rd.forward(request, response);
	}
}