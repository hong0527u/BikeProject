package com.bike.service.bike;

import java.io.IOException;
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

public class BikeModifyService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages =request.getParameter("pages");
		request.setAttribute("pages", pages);
		String code = request.getParameter("bike_code");
		request.setAttribute("code", code);
		
		Parking_infoDAO Pdao = Parking_infoDAO.getInstance();
		List<Parking_InfoVO> list = Pdao.parkingList();
		
		System.out.println("code"+list.get(0).getPark_code());
		request.setAttribute("list", list);
		
		BikeDAO dao = BikeDAO.getInstance();
		BikeVO vo = dao.bikeSelect(code);
		
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/bike_modify.jsp");
		rd.forward(request, response);
	}

}
