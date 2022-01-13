package com.bike.service.bike;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.domain.user.UserVO;
import com.bike.service.Action;
import com.bike.util.PageIndex;

public class BikeRentalService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bikecode = request.getParameter("bikecode");
		String userid = request.getParameter("userid");
		BikeDAO dao = BikeDAO.getInstance();
		BikeVO vo = dao.bikeView(bikecode);
		
		String rentalcode = request.getParameter("rentalcode");
		String pages = request.getParameter("pages");
		Parking_infoDAO Pdao = Parking_infoDAO.getInstance();
		
		List<Parking_InfoVO> list = new ArrayList();
		list = Pdao.parkingList();
		
		request.setAttribute("parking", list);
		request.setAttribute("pages", pages);
		request.setAttribute("rentalcode", rentalcode);
		request.setAttribute("bikecde", bikecode);
		request.setAttribute("vo", vo);
		RequestDispatcher rd = request.getRequestDispatcher("/pages/bike_info.jsp");
		rd.forward(request, response);
	}
}