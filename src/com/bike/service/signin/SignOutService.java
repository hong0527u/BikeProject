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

public class SignOutService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/Signin?cmd=signin&pages=signin");
		
	}

}
