package com.bike.service.qa;

import java.io.File;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;

public class QaModifyProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QaVO vo = new QaVO();
		request.setCharacterEncoding("utf-8");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		vo.setIdx(idx);
		vo.setSubject(request.getParameter("subject"));
		vo.setContents(request.getParameter("contents"));
		vo.setUserid(request.getParameter("userid"));
		
		QaDAO dao = QaDAO.getInstance();
		int row = dao.qaModify(vo);
		
		request.setAttribute("row", row);
		request.setAttribute("idx", idx);
	
		
		RequestDispatcher rd = request.getRequestDispatcher("/Qa?cmd=qa_list");
		rd.forward(request, response);
	}
}