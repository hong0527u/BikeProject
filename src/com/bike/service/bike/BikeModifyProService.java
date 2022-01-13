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

public class BikeModifyProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bike_code = request.getParameter("code");
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		BikeVO vo = new BikeVO();
		vo.setBike_code(bike_code);
		vo.setBike_state(request.getParameter("state"));
		vo.setPark_code(request.getParameter("park"));
		vo.setCor_code(request.getParameter("oldparkcode"));
		
		BikeDAO dao = BikeDAO.getInstance();
		int row = dao.bikeModify(vo);
		
		System.out.println("row:"+row);
		
		request.setAttribute("row", row);
		RequestDispatcher rd = request.getRequestDispatcher("Admin?cmd=bike_list");
		rd.forward(request, response);
		
	}

}
