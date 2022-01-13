package com.bike.service.qa;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;

public class QaModifyService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));
		request.setAttribute("idx", idx);
		
		QaDAO dao = QaDAO.getInstance();
		QaVO vo = dao.qaSelect2(idx);
		
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Qa/qa_modify.jsp");
		rd.forward(request, response);
	}
}