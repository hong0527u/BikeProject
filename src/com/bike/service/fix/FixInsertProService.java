package com.bike.service.fix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.service.Action;

public class FixInsertProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String bike_code = request.getParameter("bike_code");

			BikeVO vo = new BikeVO();
			vo.setBike_code(bike_code);
			vo.setFix_type(request.getParameter("fix_type"));
			vo.setFix_date(request.getParameter("fix_date"));
			vo.setFix_detail(request.getParameter("fix_detail"));
			
			FixDAO dao = FixDAO.getInstance();
			int row = dao.fixInsert(vo);
			
			request.setAttribute("bikecode", bike_code);
			request.setAttribute("row", row);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Virtual?cmd=bike_info&pages=bike&bikecode="+bike_code);
			rd.forward(request, response);
	}

}
