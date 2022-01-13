package com.bike.service.cor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.cor.CorDAO;
import com.bike.domain.cor.CorVO;
import com.bike.service.Action;

public class CorModifyProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		CorVO vo = new CorVO();
		vo.setCor_name(request.getParameter("Name"));
		vo.setCor_tel(request.getParameter("Tel"));
		vo.setCor_adr(request.getParameter("Address"));
		vo.setCor_code(request.getParameter("code"));
		
		CorDAO dao = CorDAO.getInstance();
		int row = dao.corModify(vo);
		
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin?cmd=cor_list");
		rd.forward(request, response);
	}
}