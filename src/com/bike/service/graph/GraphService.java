package com.bike.service.graph;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.domain.Bike.BikeVO;
import com.bike.domain.graph.GraphDAO;
import com.bike.service.Action;
import com.sun.org.apache.xpath.internal.operations.Mult;

public class GraphService implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			GraphDAO dao =GraphDAO.getInstance();
			
			//금일 수익
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fix_date= form.format(System.currentTimeMillis());
			String rental_firsttime = form.format(System.currentTimeMillis());
			int daypay=0 , dayuser=0, monthpay=0, monthfixpay=0;
			int yesterdaypay=0, yesterdayuser=0, bamonthpay=0, bamonthfixpay;
			int yearpay=0, yearuse=0, bikenum=0, yearfixpay=0;

			List<BikeVO> monthuse =null;
			List<BikeVO> monthlypay =null;
			List<BikeVO> monthlyfixpay = null;
			
			daypay = dao.daypaySelect(rental_firsttime);
			dayuser = dao.dayuserSelect(rental_firsttime);
			monthpay = dao.monthpaySelect(rental_firsttime);
			monthfixpay = dao.monthfixpaySelect(fix_date);
			
			yesterdaypay = dao.yesterdaypaySelect(rental_firsttime);
			yesterdayuser = dao.yesterdayuserSelect(rental_firsttime);
			bamonthpay = dao.bamonthpaySelect(rental_firsttime);
			bamonthfixpay= dao.bamonthfixpaySelect(fix_date);
			
			yearpay = dao.yearpaySelect();
			yearuse = dao.yearuseSelect();
			bikenum = dao.bikenumSelect();			
			yearfixpay = dao.yearfixpaySelect();
			
			monthuse =dao.monthuseSelect();
			monthlypay = dao.monthlypaySelect();
			monthlyfixpay = dao.monthlyfixpaySelect();
			
			String MonthUse ="";
			for(int x=0; x<monthuse.size(); x++) {
				MonthUse = MonthUse+"," + monthuse.get(x).getFix_pay();
			}
			String MonthlyPay ="";
			for(int x=0; x<monthlypay.size(); x++) {
				MonthlyPay = MonthlyPay+"," + monthlypay.get(x).getFix_pay();
			}
			String MonthlyfixPay ="";
			for(int x=0; x<monthlyfixpay.size(); x++) {
				MonthlyfixPay = MonthlyfixPay+"," + monthlypay.get(x).getBuy_pay();
			}
			
			String pages = request.getParameter("pages");
			request.setAttribute("pages", pages);
			
			request.setAttribute("daypay", daypay);
			request.setAttribute("dayuser", dayuser);
			request.setAttribute("monthpay", monthpay);
			request.setAttribute("monthfixpay", monthfixpay);
		
			request.setAttribute("yesterdaypay", yesterdaypay);
			request.setAttribute("yesterdayuser", yesterdayuser);
			request.setAttribute("bamonthpay", bamonthpay);
			request.setAttribute("bamonthfixpay", bamonthfixpay);
			
			request.setAttribute("monthuse", MonthUse.substring(1));
			request.setAttribute("monthlypay", MonthlyPay.substring(1));
			request.setAttribute("monthlyfixpay", MonthlyfixPay);
			
			request.setAttribute("yearpay", yearpay);
			request.setAttribute("yearuse", yearuse);
			request.setAttribute("bikenum", bikenum);
			request.setAttribute("yearfixpay", yearfixpay);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Admin/graph.jsp");
			rd.forward(request, response);
	}

}
