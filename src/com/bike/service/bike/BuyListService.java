package com.bike.service.bike;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.service.Action;
import com.bike.util.PageIndex;

public class BuyListService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BikeDAO dao = BikeDAO.getInstance();
		
		String s_sql="", sql="", key="", addtag="";
		int totcnt = 0;
		
		if(request.getParameter("cont")!=null) {
			key=request.getParameter("cont");
			sql=request.getParameter("sel");
			s_sql= sql+" like '%"+ key + "%'";
			totcnt = dao.buyCnt(s_sql);
		}else {
			totcnt = dao.buyCnt();
		}
		int nowpage = 1;
		int maxlist = 8;
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
			list = dao.buyList(startpage, endpage);
		}else {
			list = dao.buyList(s_sql, startpage, endpage);
		}
		String pageSkip = "";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Admin?cmd=buy_list&pages=buy", addtag);
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Admin?cmd=buy_list&pages=buy", sql, key);
		}
		String pages = request.getParameter("pages");
		
		String parkcode = dao.bikeParkingCheck(list.get(0).getBike_code());
		
		request.setAttribute("parkcode", parkcode);
		request.setAttribute("pages", pages);
		request.setAttribute("totcnt", totcnt);
		request.setAttribute("list", list);
		request.setAttribute("page", nowpage);
		request.setAttribute("totpage", totpage);
		request.setAttribute("listcnt", listcnt);
		request.setAttribute("sql", sql);
		request.setAttribute("key", key);
		request.setAttribute("pageSkip", pageSkip);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/buy_list.jsp");
		rd.forward(request, response);
	}
}