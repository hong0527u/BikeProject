package com.bike.domain.graph;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.domain.Bike.BikeVO;
import com.bike.util.DBManager;

public class GraphDAO {
	private GraphDAO( ) {}
	private static GraphDAO instance = new GraphDAO();
	public static GraphDAO getInstance() {
		return instance;
	}
	Connection conn= null;
	PreparedStatement pstmt = null;
	ResultSet rs =null;
	public int daypaySelect(String rental_firsttime) {

		String query = "select sum(rental_pay) from rental_info where substr(TO_CHAR(sysdate,'yyyy/mm/dd'), 0, 10)=substr(rental_firsttime, 0, 10)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int dayuserSelect(String rental_firsttime) {

		String query = "select count(*) from rental_info where substr(TO_CHAR(sysdate,'yyyy/mm/dd'), 0, 10)=substr(rental_firsttime, 0, 10)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int monthpaySelect(String rental_firsttime) {

		String query = "select sum(rental_pay) from rental_info where substr(TO_CHAR(sysdate,'yyyy/mm/dd'), 0, 7)=substr(rental_firsttime, 0, 7)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int monthfixpaySelect(String fix_date) {

		String query = "select sum(fix_pay) from fix_info where substr(TO_CHAR(sysdate,'yy/mm/dd'), 0, 5)=substr(fix_date, 0, 5)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int yesterdaypaySelect(String rental_firsttime) {

		String query = "select sum(rental_pay) from rental_info where substr(TO_CHAR(sysdate-1,'yyyy/mm/dd'), 0, 10)=substr(rental_firsttime, 0, 10)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int yesterdayuserSelect(String rental_firsttime) {

		String query = "select count(*) from rental_info where substr(TO_CHAR(sysdate-1,'yyyy/mm/dd'), 0, 10)=substr(rental_firsttime, 0, 10)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int bamonthpaySelect(String rental_firsttime) {

		String query = "select sum(rental_pay) from rental_info where substr(TO_CHAR(add_months(sysdate,-1),'yyyy/mm/dd'), 0, 7)=substr(rental_firsttime, 0, 7)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int bamonthfixpaySelect(String fix_date) {

		String query = "select sum(fix_pay) from fix_info where substr(TO_CHAR(add_months(sysdate,-1),'yy/mm/dd'), 0, 5)=substr(fix_date, 0, 5)";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public List<BikeVO> monthuseSelect() {

		String query = "select COUNT(*) as cnt from rental_info group by substr(rental_lasttime, 0 , 7) order by substr(rental_lasttime, 0 , 7) asc";
		
		List<BikeVO> list = new ArrayList<BikeVO>();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_pay(rs.getInt("cnt"));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public int yearpaySelect() {

		String query = "select sum(rental_pay) from rental_info";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int yearuseSelect() {

		String query = "select count(*) from rental_info";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int yearfixpaySelect() {

		String query = "select sum(fix_pay) from fix_info";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public int bikenumSelect() {
		String query = "select count(*) from bike_info";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				row=rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	public List<BikeVO> monthlypaySelect() {

		String query = "select sum(rental_pay) as cnt from rental_info group by substr(rental_lasttime, 0 , 7) order by substr(rental_lasttime, 0 , 7) asc";
		
		List<BikeVO> list = new ArrayList<BikeVO>();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_pay(rs.getInt("cnt"));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public List<BikeVO> monthlyfixpaySelect() {

		String query = "select sum(fix_pay) as cnt from fix_info group by substr(fix_date, 0, 5)";
		
		List<BikeVO> list = new ArrayList<BikeVO>();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBuy_pay(rs.getInt("cnt"));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
}
