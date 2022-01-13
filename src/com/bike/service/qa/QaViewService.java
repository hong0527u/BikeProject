package com.bike.service.qa;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;

public class QaViewService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		QaDAO dao = QaDAO.getInstance();
		QaVO vo = dao.qaSelect(idx);
		vo.setContents(vo.getContents());
		
		boolean bool = false;
		Cookie info = null;
		Cookie cookies[] = request.getCookies();
		for(int x=0; x<cookies.length; x++) {
			info = cookies[x];
			if(info.getName().equals("qa"+idx)) {
				bool = true;
				break;
			}
		}
		String newValue=""+System.currentTimeMillis();
		if(!bool) {
			dao.ReadCnt(idx);
			info = new Cookie("qa"+idx,newValue);
			info.setMaxAge(60*60);//1Day=60*60*24
			response.addCookie(info);
		}
		request.setAttribute("idx", idx);
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Qa/qa_view.jsp");
		rd.forward(request, response);
	}
}