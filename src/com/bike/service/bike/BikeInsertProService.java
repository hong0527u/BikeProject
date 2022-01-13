package com.bike.service.bike;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.cor.CorDAO;
import com.bike.domain.cor.CorVO;
import com.bike.service.Action;

public class BikeInsertProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		int each = Integer.parseInt(request.getParameter("each"));
		
		BikeVO vo = new BikeVO();
		vo.setBike_type(request.getParameter("type"));
		vo.setPark_code(request.getParameter("parking"));
		vo.setCor_code(request.getParameter("cor"));
		
		BikeDAO dao = BikeDAO.getInstance();
		int row = dao.bikeInsert(vo,each);
		
		request.setAttribute("row", row);
		
		String path = "";
		if(pages.equals("cor")) {
			path = "/Admin?cmd=cor_list";
		}else if(pages.equals("buy")) {
			path = "/Admin?cmd=buy_list";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}