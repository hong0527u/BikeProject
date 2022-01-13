package com.bike.domain.parking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.util.DBManager;
import com.bike.util.UserSHA256;

public class Parking_infoDAO {
	private Parking_infoDAO() {
	}

	private static Parking_infoDAO instance = new Parking_infoDAO();

	public static Parking_infoDAO getInstance() {
		return instance;
	}

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public int parkingInsert(Parking_InfoVO vo) {

		String query = "insert into parking_info(park_code,park_name,park_area,park_pay,park_locx,park_locy) values ('park'||parking_code_seq.nextval,?,?,?,?,?)";

		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPark_name());
			pstmt.setString(2, vo.getPark_area());
			pstmt.setInt(3, vo.getPark_pay());
			pstmt.setDouble(4, vo.getPark_locx());
			pstmt.setDouble(5, vo.getPark_locy());

			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public int parkingModify(Parking_InfoVO vo) {

		String query = "update parking_info set park_name=?,park_area=?,park_pay=?,park_locx=?,park_locy=? where park_code=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPark_name());
			pstmt.setString(2, vo.getPark_area());
			pstmt.setInt(3, vo.getPark_pay());
			pstmt.setDouble(4, vo.getPark_locx());
			pstmt.setDouble(5, vo.getPark_locy());
			pstmt.setString(6, vo.getPark_code());

			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public List<Parking_InfoVO> parkingList() {

		String query = "select * from parking_info order by park_code desc";
		List<Parking_InfoVO> list = new ArrayList();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Parking_InfoVO vo = new Parking_InfoVO();
				vo.setPark_name(rs.getString("park_name"));
				vo.setPark_area(rs.getString("park_area"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setPark_pay(rs.getInt("park_pay"));
				vo.setPark_locx(rs.getDouble("park_locx"));
				vo.setPark_locy(rs.getDouble("park_locy"));
				vo.setPark_bike(rs.getInt("park_bike"));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public int parkBikeCnt() {
		int cnt = 0;
		String query = "select count(*) as cnt from bike_info where bike_state='parking' and ";
		List<Parking_InfoVO> list = new ArrayList();
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Parking_InfoVO vo = new Parking_InfoVO();
				vo.setPark_bike(rs.getInt("cnt"));
				cnt = vo.getPark_bike();
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return cnt;
	}

	public List<Parking_InfoVO> parkingList(int startpage, int endpage) {

		String query = "select X.* from (select rownum as rnum, A.* from (select * from parking_info order by park_code desc) A where rownum <= ?) X where X.rnum >= ?";
		List<Parking_InfoVO> list = new ArrayList();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Parking_InfoVO vo = new Parking_InfoVO();
				vo.setPark_name(rs.getString("park_name"));
				vo.setPark_area(rs.getString("park_area"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setPark_pay(rs.getInt("park_pay"));
				vo.setPark_locx(rs.getDouble("park_locx"));
				vo.setPark_locy(rs.getDouble("park_locy"));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	public List<Parking_InfoVO> parkingList(String s_query, int startpage, int endpage) {

		String query = "select X.* from (select rownum as rnum, A.* from "
				+ "(select * from parking_info order by park_code desc) A " + "where " + s_query
				+ " and rownum <= ?) X where " + s_query + " and X.rnum >= ?";
		List<Parking_InfoVO> list = new ArrayList();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Parking_InfoVO vo = new Parking_InfoVO();
				vo.setPark_name(rs.getString("park_name"));
				vo.setPark_area(rs.getString("park_area"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setPark_pay(rs.getInt("park_pay"));
				vo.setPark_locx(rs.getDouble("park_locx"));
				vo.setPark_locy(rs.getDouble("park_locy"));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	public int parkingCount() {

		String query = "select count(*) from parking_info";
		int row = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	public int parkingCount(String s_query) {

		String query = "select count(*) from parking_info where " + s_query;
		int row = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	public int ParkingDelete(String park_code) {
		Parking_InfoVO vo = new Parking_InfoVO();

		String query = "delete from parking_info where park_code=?";
		int row = 0;
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, park_code);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public Parking_InfoVO parkingSelect(String park_code) {

		String query = "select * from parking_info where park_code=?";
		Parking_InfoVO vo = new Parking_InfoVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, park_code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo.setPark_name(rs.getString("park_name"));
				vo.setPark_area(rs.getString("park_area"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setPark_pay(rs.getInt("park_pay"));
				vo.setPark_locx(rs.getDouble("park_locx"));
				vo.setPark_locy(rs.getDouble("park_locy"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}

	public int parkCheck(String park_name) {

		int row = 0;
		String query = "select count(*) from parking_info where park_name=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, park_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

}
