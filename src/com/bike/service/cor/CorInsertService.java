package com.bike.service.cor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.service.Action;

public class CorInsertService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		String cmd = request.getParameter("cmd");
		request.setAttribute("cmd", cmd);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/cor_insert.jsp");
		rd.forward(request, response);
	}
}