package com.bike.service.qa;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.service.Action;

public class QaDeleteService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userid = request.getParameter("userid");
		QaDAO dao = QaDAO.getInstance();
		int row = dao.qaDelete(idx, userid);
		
		request.setAttribute("row", row);
		
		
			RequestDispatcher rd = request.getRequestDispatcher("/Qa?cmd=qa_list");
			rd.forward(request, response);
		
	}
}