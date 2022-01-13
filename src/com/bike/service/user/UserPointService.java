package com.bike.service.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.service.Action;
import com.bike.util.PageIndex;
import com.bike.*;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.domain.parking.Parking_infoDAO;
import com.bike.domain.user.UserDAO;
import com.bike.domain.user.UserVO;

public class UserPointService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO dao = UserDAO.getInstance();
		String s_query = "", query = "", key = "", addtag = "";

		int totcount = 0;

		// 총게시글수 카운트

		// 검색키가 있는 값의 유무
		if (request.getParameter("key") != null) {
			key = request.getParameter("key"); // 검색어
			query = request.getParameter("search"); // 제목, 내용 ,
			s_query = query + " like '%" + key + "%'";
			totcount = dao.userCount(s_query);
		} else {
			totcount = dao.userCount();
		}

		// 총 페이지수 계산
		int nowpage = 1; // 현재페이지
		int maxlist = 10; // 페이지당 글 수
		int totpage = 1; // 총 페이지

		if (totcount % maxlist == 0) {
			totpage = totcount / maxlist;
		} else {
			totpage = totcount / maxlist + 1;
		}

		// 페이지 번호를 눌렀을때 처리
		if (request.getParameter("page") != null) {
			nowpage = Integer.parseInt(request.getParameter("page"));
		}

		// 시작페이지, 끝페이지
		int startpage = (nowpage - 1) * maxlist + 1;
		int endpage = nowpage * maxlist;
		int listcount = totcount - ((nowpage - 1) * maxlist); // 리스트 페이지에 번호 출력용

		// 게시글 불러오기
		List<UserVO> list = null;
		if (key.equals("")) {
			list = dao.userList(startpage, endpage);
		} else {
			list = dao.userList(s_query, startpage, endpage);
		}

		// 페이지 번호 생성
		String pageSkip = "";
		if (key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Profile?cmd=point&pages=profile", addtag);
		} else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Profile?cmd=point&pages=profile", query, key);
		}

		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		request.setAttribute("totcount", totcount);
		request.setAttribute("listcount", listcount);
		request.setAttribute("page", nowpage); // 현재페이지
		request.setAttribute("totpage", totpage); // 총페이지
		request.setAttribute("list", list);
		request.setAttribute("query", query);
		request.setAttribute("key", key);
		request.setAttribute("pageSkip", pageSkip);

		RequestDispatcher rd = request.getRequestDispatcher("/pages/point_charge.jsp");
		rd.forward(request, response);
	}

}
