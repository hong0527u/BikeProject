package com.bike.service.bike;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.cor.CorDAO;
import com.bike.domain.cor.CorVO;
import com.bike.service.Action;

public class BikeParkingService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pages = request.getParameter("pages");
		request.setAttribute("pages", pages);
		
		String bikecode = request.getParameter("bikecode");
		String parkcode = request.getParameter("parkcode");
		String rentalcode = request.getParameter("rentalcode");
		System.out.println("retalcode"+rentalcode);
		BikeDAO dao = BikeDAO.getInstance();
		
		double time = dao.rentalTimeCheck(rentalcode);
		System.out.println("time"+time);
		String userid = dao.SelectRentalInfo(rentalcode).getUser_id();
		int pay = 1500;
		if(time>2) {
			pay = pay + 500;
			
			if(time%1==0) {
				pay = pay + ((int)time-3)*500;
			}else {
				pay = pay + ((int)time-2)*500;
			}
			pay = pay-1500;
			System.out.println(pay);
			dao.PlusCredit(userid, rentalcode, pay);
		}
		
		int row = dao.bikeParking(bikecode, parkcode, rentalcode,pay);
		
		if(row==1) {
			HttpSession session = request.getSession();
			session.invalidate();
		}
		
		request.setAttribute("row", row);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Virtual?cmd=virtual&pages=virtual");
		rd.forward(request, response);
	}
}