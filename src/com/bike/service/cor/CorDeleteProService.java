package com.bike.service.cor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.cor.CorDAO;
import com.bike.domain.cor.CorVO;
import com.bike.service.Action;

public class CorDeleteProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		String code = request.getParameter("code");
		
		CorDAO dao = CorDAO.getInstance();
		int row = dao.corDelete(code);
		
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin?cmd=cor_list");
		rd.forward(request, response);
	}
}