package com.bike.service.signup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bike.domain.user.UserDAO;
import com.bike.service.Action;

public class SignDelService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDAO dao = UserDAO.getInstance();
		
		String userid = request.getParameter("userid");
		
		int row = dao.userDel(userid);
		
		System.out.println("del : " + row);
		
		HttpSession session = request.getSession();//세션 객체 생성
		session.invalidate();
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/virtual-reality.jsp");
		rd.forward(request, response);	
		
	}

}
