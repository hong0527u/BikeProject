package com.bike.service.signin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bike.domain.user.UserDAO;
import com.bike.domain.user.UserVO;
import com.bike.service.Action;
import com.bike.util.UserSHA256;

public class SigninProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String passwd = UserSHA256.getSHA256(request.getParameter("passwd"));
		
		String address = "";
		int flag = 0;
		
		UserDAO dao = UserDAO.getInstance();
		
		int row = dao.userLogin(userid, passwd);
		request.setAttribute("row", row);
		
		if(row==1) {
			UserVO vo = dao.userSelect(userid);
			HttpSession session = request.getSession();
			session.setAttribute("user", vo);
			session.setMaxInactiveInterval(1800);//30분
			
			address = "/Virtual?cmd=virtual&pages=virtual";
			
		}else if(row==0 || row==-1) {
			flag = 1;
			request.setAttribute("flag", flag);
			address = "/Signin?cmd=signin";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(address);
		rd.forward(request, response);
		
	}

}
