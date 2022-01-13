package com.bike.service.fix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.fix.FixDAO;
import com.bike.service.Action;

public class FIxDeleteProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		String code = request.getParameter("fix_code");
		
		FixDAO dao = FixDAO.getInstance();
		int row = dao.fixDelete(code);
		
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin?cmd=fix_list");
		rd.forward(request, response);
	}
}