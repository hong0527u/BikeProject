package com.bike.service.fix;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.service.Action;

public class FixViewService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fix_code = request.getParameter("fix_code");
		String pages = request.getParameter("pages");
		
		FixDAO dao = FixDAO.getInstance();
		
		if(fix_code==null) {
			String bike_code = request.getParameter("bike_code");
			fix_code = dao.selectFixcode(bike_code);
		}
		
		BikeVO vo = dao.fixviewSelect(fix_code);
		
		Parking_infoDAO pdao = Parking_infoDAO.getInstance();
		List<Parking_InfoVO> list = pdao.parkingList();
		
		request.setAttribute("list", list);
		request.setAttribute("pages", pages);
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/fix_view.jsp");
		rd.forward(request, response);
	}

}
