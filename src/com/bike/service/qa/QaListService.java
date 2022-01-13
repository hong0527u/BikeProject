package com.bike.service.qa;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.qa.QaDAO;
import com.bike.domain.qa.QaVO;
import com.bike.service.Action;
import com.bike.util.PageIndex;

public class QaListService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QaDAO dao = QaDAO.getInstance();
		
		String s_sql="", sql="", key="", addtag="";
		int totcnt = 0;
		
		if(request.getParameter("cont")!=null) {
			key=request.getParameter("cont");
			sql=request.getParameter("sel");
			s_sql= sql+" like '%"+ key + "%'";
			totcnt = dao.qaCnt(s_sql);
		}else {
			totcnt = dao.qaCnt();
		}
		int nowpage = 1;
		int maxlist = 10;
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
		List<QaVO> list = null;
		if(key.equals("")) {
			list = dao.qaList(startpage, endpage);
		}else {
			list = dao.qaList(s_sql, startpage, endpage);
		}
		
		String pageSkip = "";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Qa?cmd=qa_list", addtag);
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Qa?cmd=qa_list", sql, key);
		}
		String pages = request.getParameter("pages");
		
		request.setAttribute("pages", pages);
		request.setAttribute("totcnt", totcnt);
		request.setAttribute("list", list);
		request.setAttribute("page", nowpage);
		request.setAttribute("totpage", totpage);
		request.setAttribute("listcnt", listcnt);
		request.setAttribute("sql", sql);
		request.setAttribute("key", key);
		request.setAttribute("pageSkip", pageSkip);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Qa/qa_list.jsp");
		rd.forward(request, response);
	}
}