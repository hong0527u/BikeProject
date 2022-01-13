package com.bike.service.signup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.user.UserDAO;
import com.bike.domain.user.UserVO;
import com.bike.service.Action;
import com.bike.util.UserSHA256;

public class SignupProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = new UserVO();
		
		vo.setUser_id(request.getParameter("userid"));
		vo.setUser_name(request.getParameter("name"));
		vo.setUser_passwd(UserSHA256.getSHA256(request.getParameter("passwd")));
		vo.setUser_email(request.getParameter("email"));
		vo.setUser_tel(request.getParameter("tel"));
		vo.setUser_birth(request.getParameter("birth"));
		
		
		int row = dao.userInsert(vo);
		
		request.setAttribute("row", row);
		RequestDispatcher rd = request.getRequestDispatcher("/pages/virtual-reality.jsp");
		rd.forward(request, response);
		
	}

}
