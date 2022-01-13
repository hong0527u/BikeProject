package com.bike.service.signup;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.service.Action;
import com.bike.util.GmailConfirm;

public class SignupEmailCheckService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		System.out.println(email);
		
		
		
		GmailConfirm instance = GmailConfirm.getInstance();
		String authNum = instance.connectEmail(email);
		
		//ajx
		PrintWriter out = response.getWriter();
		out.print(authNum);
		
		
//		request.setAttribute("authNum", authNum);
//		RequestDispatcher rd = request.getRequestDispatcher("/pages/sign-up.jsp");
//		rd.forward(request, response);
		
		
	}

}
