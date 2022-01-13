package com.bike.service.profile;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.user.UserDAO;
import com.bike.domain.user.UserVO;
import com.bike.service.Action;
import com.bike.util.PageIndex;

public class ProfileListService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		String userid = request.getParameter("userid");
		String roll = request.getParameter("roll");
		
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = dao.userSelect(userid);
		
		BikeDAO Bdao = BikeDAO.getInstance();
		
		request.setAttribute("vo", vo);
		request.setAttribute("pages", pages);
		request.setAttribute("userid", userid);
		request.setAttribute("roll", roll);
		
		String s_sql="", sql="", key="", addtag="";
		int totcnt = 0;
		
		if(request.getParameter("cont")!=null) {
			key=request.getParameter("cont");
			sql=request.getParameter("sel");
			s_sql= sql+" like '%"+ key + "%'";
			totcnt = Bdao.rentalCnt(s_sql, userid);
		}else {
			totcnt = Bdao.rentalCnt(userid);
		}
		int nowpage = 1;
		int maxlist = 5;
		int totpage = 1;
		
		if(totcnt % maxlist== 0) {
			totpage = totcnt / maxlist;
		}else {
			totpage = totcnt / maxlist +1;
		}
		
		if(request.getParameter("page")!=null) {
			nowpage = Integer.parseInt(request.getParameter("page"));
		}
		int startpage = (nowpage-1)*maxlist+1;
		int endpage = nowpage*maxlist;
		int listcnt = totcnt-((nowpage-1)*maxlist);
		List<BikeVO> list = null;
		
		if(key.equals("")) {
			list = Bdao.SelectRentalList(userid, startpage, endpage);
		}else {
			list = Bdao.SelectRentalList(userid, s_sql, startpage, endpage);
		}
		String pageSkip = "";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Profile?cmd=profile&pages=profile&userid="+userid, addtag);
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Profile?cmd=profile&pages=profile&userid="+userid, sql, key);
		}
		
		request.setAttribute("totcnt", totcnt);
		request.setAttribute("list", list);
		request.setAttribute("page", nowpage);
		request.setAttribute("totpage", totpage);
		request.setAttribute("listcnt", listcnt);
		request.setAttribute("sql", sql);
		request.setAttribute("key", key);
		request.setAttribute("pageSkip", pageSkip);
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/profile.jsp");
		rd.forward(request, response);
	}
}