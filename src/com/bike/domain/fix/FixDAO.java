package com.bike.domain.fix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.domain.Bike.BikeDAO;
import com.bike.domain.Bike.BikeVO;
import com.bike.domain.parking.Parking_InfoVO;
import com.bike.util.DBManager;

public class FixDAO {
	private FixDAO() {}
	private static FixDAO instance = new FixDAO();
	public static FixDAO getInstance() {
		return instance;
	}
	Connection conn = null;
	PreparedStatement pstmt =null;
	ResultSet rs =null;
	
	public int fixCnt() {
		String sql = "select count(*) from fix_info";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public int fixCnt(String s_sql) {
		String sql = "select count(*) from fix_info where " + s_sql;
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public int FixCnt(String bike_code) {
		String sql = "select count(*) from fix_info where bike_code=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public int FixCnt(String s_sql, String bike_code) {
		String sql = "select count(*) from fix_info where bike_code=? and " + s_sql;
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public List<BikeVO> fixList(int startpage,int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from fix_info order by fix_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_code(rs.getString("fix_code"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_pay(rs.getInt("fix_pay"));
				vo.setFix_end(rs.getString("fix_end"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public List<BikeVO> FixList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from fix_info where "+ s_sql
				+ " order by fix_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_code(rs.getString("bike_code"));
				vo.setBike_code(rs.getString("fix_code"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_pay(rs.getInt("fix_pay"));
				vo.setFix_end(rs.getString("fix_end"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public List<BikeVO> fixList(int startpage,int endpage, String bike_code) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from fix_info where bike_code=? order by fix_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			pstmt.setInt(2, endpage);
			pstmt.setInt(3, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_code(rs.getString("fix_code"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_pay(rs.getInt("fix_pay"));
				vo.setFix_end(rs.getString("fix_end"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public List<BikeVO> FixList(String s_sql, int startpage, int endpage, String bike_code) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from fix_info where bike_code=? and"+ s_sql
				+ " order by fix_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			pstmt.setInt(2, endpage);
			pstmt.setInt(3, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setFix_code(rs.getString("bike_code"));
				vo.setBike_code(rs.getString("fix_code"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_pay(rs.getInt("fix_pay"));
				vo.setFix_end(rs.getString("fix_end"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public BikeVO bikeSelect(String bike_code) {

		String query = "select * from bike_info where bike_code=?";
		BikeVO vo = new BikeVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_type(rs.getString("bike_type"));
				vo.setBike_regdate(rs.getString("bike_regdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
}
	public BikeVO fixSelect(String bike_code) {

		String query = "select * from fix_info where bike_code=?";
		BikeVO vo = new BikeVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo.setFix_code(rs.getString("fix_code"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_pay(rs.getInt("fix_pay"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	public int fixInsert(BikeVO vo) {

		String query = "insert into fix_info(fix_code,bike_code,fix_type,fix_date,fix_pay,fix_detail) values ('fix'||fix_code_seq.nextval,?,?,?,?,?)";

		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getBike_code());
			pstmt.setString(2, vo.getFix_type());
			pstmt.setString(3, vo.getFix_date());
			pstmt.setInt(4, vo.getFix_pay());
			pstmt.setString(5, vo.getFix_detail());
			
			row = pstmt.executeUpdate();
			
			if(row==1) {
				query = "update bike_info set bike_state='fix' where bike_code=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, vo.getBike_code());
				
				row = pstmt.executeUpdate();
				
				BikeDAO dao = BikeDAO.getInstance();
				String parkcode = dao.bikeSelect(vo.getBike_code()).getPark_code();
				dao.parkBikeMinus(parkcode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	public BikeVO fixviewSelect(String fix_code) {

		String query = "select * from bike_info b, fix_info f where b.bike_code=f.bike_code and fix_code=?";
		BikeVO vo = new BikeVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fix_code);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_type(rs.getString("bike_type"));
				vo.setBike_regdate(rs.getString("bike_regdate"));
				vo.setFix_code(rs.getString("fix_code"));
				vo.setFix_date(rs.getString("fix_date"));
				vo.setFix_type(rs.getString("fix_type"));
				vo.setFix_detail(rs.getString("fix_detail"));
				vo.setFix_end(rs.getString("fix_end"));
				vo.setFix_pay(rs.getInt("fix_pay"));
				vo.setPark_code(rs.getString("park_code"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	public int fixEndPro(BikeVO vo, String fix_code, String bike_code) {
		int row = 0;
		String sql = "update fix_info set fix_pay=?, fix_end=sysdate where fix_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getFix_pay());
			pstmt.setString(2, fix_code);
			
			row = pstmt.executeUpdate();
			if(row==1) {
				sql = "update bike_info set park_code=?, bike_state='parking' where bike_code=?";
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getPark_code());
				pstmt.setString(2, bike_code);
				
				row = pstmt.executeUpdate();
				
				BikeDAO dao = BikeDAO.getInstance();
				dao.parkBikePlus(vo.getPark_code());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return row;
	}

	public int fixDelete(String code) {
		int row = 0;
		String sql = "delete from fix_info where fix_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return 0;
	}

	public String selectFixcode(String bike_code) {
		String fix_code = "";
		String sql = "select fix_code from fix_info where bike_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fix_code = rs.getString("fix_code");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return fix_code;
	}
}
