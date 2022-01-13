package com.bike.service.bike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.fix.FixDAO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;
import com.bike.util.PageIndex;

public class BikeViewService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bikecode = request.getParameter("bikecode");
		String pages= request.getParameter("pages");
		
		BikeDAO dao = BikeDAO.getInstance();
		BikeVO vo = dao.bikeView(bikecode);
		
		FixDAO fixdao = FixDAO.getInstance();
		
		String s_sql="", sql="", key="", addtag="";
		int totcnt = 0;
		
		if(request.getParameter("cont")!=null) {
			key=request.getParameter("cont");
			sql=request.getParameter("sel");
			s_sql= sql+" like '%"+ key + "%'";
			totcnt = fixdao.FixCnt(s_sql,bikecode);
		}else {
			totcnt = fixdao.FixCnt(bikecode);
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
			list = fixdao.fixList(startpage, endpage, bikecode);
		}else {
			list = fixdao.FixList(s_sql, startpage, endpage, bikecode);
		}
		String pageSkip = "";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Virtual?cmd=bike_info&pages=bike&bikecode="+bikecode, addtag);
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Virtual?cmd=bike_info&pages=bike&bikecode="+bikecode, sql, key);
		}
		Parking_infoDAO Pdao = Parking_infoDAO.getInstance();
		
		List<Parking_InfoVO> Plist = new ArrayList();
		Plist = Pdao.parkingList();
		
		request.setAttribute("parking", Plist);
		request.setAttribute("totcnt", totcnt);
		request.setAttribute("list", list);
		request.setAttribute("page", nowpage);
		request.setAttribute("totpage", totpage);
		request.setAttribute("listcnt", listcnt);
		request.setAttribute("sql", sql);
		request.setAttribute("key", key);
		request.setAttribute("pageSkip", pageSkip);
		request.setAttribute("pages", pages);
		request.setAttribute("bike_code", bikecode);
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/bike_info.jsp");
		rd.forward(request, response);
	}
}