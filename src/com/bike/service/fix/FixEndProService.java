package com.bike.service.fix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.service.Action;

public class FixEndProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String bike_code = request.getParameter("bike_code");
			String fix_code = request.getParameter("fix_code");
			
			BikeVO vo = new BikeVO();
			vo.setFix_pay(Integer.parseInt(request.getParameter("fix_pay")));
			vo.setPark_code(request.getParameter("park_code"));
			
			FixDAO dao = FixDAO.getInstance();
			int row = dao.fixEndPro(vo, fix_code, bike_code);
			
			request.setAttribute("bikecode", bike_code);
			request.setAttribute("row", row);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Admin?cmd=fix_list&pages=fix");
			rd.forward(request, response);
	}

}
