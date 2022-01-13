package com.bike.service.qa;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;

public class QaReplyProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QaVO vo = new QaVO();
		request.setCharacterEncoding("utf-8");
		
		vo.setIdx(Integer.parseInt(request.getParameter("idx")));
		
		vo.setQ_answer(request.getParameter("reply"));
		vo.setUserid(request.getParameter("userid"));
		QaDAO dao = QaDAO.getInstance();
		int row = dao.qaReplyModify(vo);
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Qa?cmd=qa_list");
		rd.forward(request, response);
	}
}