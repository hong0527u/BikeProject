package com.bike.service.parking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class ParkingInsertProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		Parking_InfoVO vo = new Parking_InfoVO();
		vo.setPark_area(request.getParameter("area"));
		vo.setPark_name(request.getParameter("name"));
		vo.setPark_pay(Integer.parseInt(request.getParameter("pay")));
		vo.setPark_locx(Double.parseDouble(request.getParameter("locx")));
		vo.setPark_locy(Double.parseDouble(request.getParameter("locy")));
		
		Parking_infoDAO dao = Parking_infoDAO.getInstance();
		int row = dao.parkingInsert(vo);
		
		request.setAttribute("row", row);
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/parking_insert_pro.jsp");
		rd.forward(request, response);
	}

}
