package com.bike.domain.cor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.util.DBManager;
import com.bike.util.UserSHA256;

public class CorDAO {
	private CorDAO() {}
	private static CorDAO instance = new CorDAO();
	public static CorDAO getInstance() {
		return instance;
	}
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int corInsert(CorVO vo) {
		int row = 0;
		String sql = "insert into cor_info(cor_code,cor_name,cor_tel,cor_adr) values ('COR'||COR_CODE_SEQ.nextval,?,?,?)";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getCor_name());
			pstmt.setString(2, vo.getCor_tel());
			pstmt.setString(3, vo.getCor_adr());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
	public int userLogin(String userid, String passwd) {
		int row=0;
		String sql = "select u_passwd from web_user where u_userid=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String dbpass = rs.getString("u_passwd");
				if(dbpass.equals(passwd)) {
					sql="update web_user set u_lasttime=sysdate where u_userid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
					row = 1;
				}else {
					row = 0;
				}
			}else {
				row = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	public CorVO corSelect() {
		CorVO vo = new CorVO();
		String sql = "select * from cor_info";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setCor_code(rs.getString("cor_code"));
				vo.setCor_name(rs.getString("cor_name"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	public int corModify(CorVO vo) {
		int row = 0;
		String sql = "update cor_info set cor_name=?, cor_tel=?, cor_adr=? where cor_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getCor_name());
			pstmt.setString(2, vo.getCor_tel());
			pstmt.setString(3, vo.getCor_adr());
			pstmt.setString(4, vo.getCor_code());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public int corDelete(String cor_code) {
		int row = 0;
		String sql = "delete from cor_info where cor_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cor_code);
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
	public List<CorVO> corList() {
		String sql = "select * from cor_info order by cor_code desc";
		List<CorVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CorVO vo = new CorVO();
				vo.setCor_code(rs.getString("cor_code"));
				vo.setCor_name(rs.getString("cor_name"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<CorVO> corList(int startpage,int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from cor_info order by cor_code desc) A where rownum<=?) X where X.rnum >=?";
		List<CorVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CorVO vo = new CorVO();
				vo.setCor_code(rs.getString("cor_code"));
				vo.setCor_name(rs.getString("cor_name"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<CorVO> corList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from cor_info where "+ s_sql
				+ " order by cor_code desc) A where rownum<=?) X where X.rnum >=?";
		List<CorVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CorVO vo = new CorVO();
				vo.setCor_code(rs.getString("cor_code"));
				vo.setCor_name(rs.getString("cor_name"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public int corCnt() {
		String sql = "select count(*) from cor_info";
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
	
	public int corCnt(String s_sql) {
		String sql = "select count(*) from cor_info where " + s_sql;
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
	
	public int PasswdSearch(String email, String passwd ,String userid) {
		int row = 0;
		String sql = "update web_user set u_passwd=? where u_email=? and u_userid=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, UserSHA256.getSHA256(passwd));
			pstmt.setString(2, email);
			pstmt.setString(3, userid);
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
}
