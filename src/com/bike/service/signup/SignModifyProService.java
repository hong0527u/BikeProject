package com.bike.service.signup;

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

public class SignModifyProService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = new UserVO();
		
		String passwd1 = request.getParameter("passwdhd");
		String passwd2 = UserSHA256.getSHA256(request.getParameter("passwd"));
		
		vo.setUser_id(request.getParameter("userid"));
		vo.setUser_name(request.getParameter("name"));
		if(passwd2.equals("")||passwd1.equals(passwd2)){
			vo.setUser_passwd(passwd1);
		}else {
			vo.setUser_passwd(passwd2);
		}
		
		vo.setUser_email(request.getParameter("email"));
		vo.setUser_tel(request.getParameter("tel"));
		vo.setUser_birth(request.getParameter("birth"));
		
		int row = dao.userUpdate(vo);
		
		request.setAttribute("row", row);
		
		System.out.println(row);
		
		HttpSession session = request.getSession();//세션 객체 생성
		session.invalidate();
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/virtual-reality.jsp");
		rd.forward(request, response);
		
	}
}