package com.bike.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.domain.parking.Parking_InfoVO;
import com.bike.util.DBManager;

public class UserDAO {
	private UserDAO() {
	}

	private static UserDAO Instance = new UserDAO();

	public static UserDAO getInstance() {
		return Instance;
	}

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public int userInsert(UserVO vo) {

		String query = "insert into user_info(user_id, user_passwd, user_name, user_regdate, user_lasttime, user_tel, user_cost, user_email, user_birth)  \r\n"
				+ "VALUES(?,?,?,sysdate,'9999-01-01',?,?,?,?)";

		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getUser_id());
			pstmt.setString(2, vo.getUser_passwd());
			pstmt.setString(3, vo.getUser_name());
			pstmt.setString(4, vo.getUser_tel());
			pstmt.setString(5, vo.getUser_cost());
			pstmt.setString(6, vo.getUser_email());
			pstmt.setString(7, vo.getUser_birth());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	// id 중복검사
	public int idCheck(String userid) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;

		int row = 0;

		String query = "select count(*) from user_info where user_id=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
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

	//
	public int userLogin(String userid, String passwd) {

		int row = 0;
		String query = "select user_passwd from user_info where user_id=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String dbpass = rs.getString("user_passwd");
				if (dbpass.equals(passwd)) {
					query = "update user_info set user_lasttime=sysdate where user_id=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
					row = 1;
				} else { // 비번오류
					row = 0;
				}
			} else {
				row = -1; // 아이디가 존재하지 않을 경우
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	// 아이디에해당하는 vo 받아오기 로그인 세션
	public UserVO userSelect(String userid) {
		UserVO vo = new UserVO();
		String query = "select * from user_info where user_id=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setUser_name(rs.getString("user_name"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_email(rs.getString("user_email"));
				vo.setUser_regdate(rs.getString("user_regdate"));
				vo.setUser_lasttime(rs.getString("user_lasttime"));
				vo.setUser_birth(rs.getString("user_birth"));
				vo.setUser_tel(rs.getString("user_tel"));
				vo.setUser_cost(rs.getString("user_cost"));
				vo.setUser_roll(rs.getString("user_roll"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}

	// 회원 전체 목록 list 불러오기

	// update, Modify 수정
	public int userUpdate(UserVO vo) {

		String query = "UPDATE user_info SET user_passwd=?, user_name=?, user_tel=?, user_cost=?, user_email=?, user_birth=? where user_id=?";

		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getUser_passwd());
			pstmt.setString(2, vo.getUser_name());
			pstmt.setString(3, vo.getUser_tel());
			pstmt.setString(4, vo.getUser_cost());
			pstmt.setString(5, vo.getUser_email());
			pstmt.setString(6, vo.getUser_birth());
			pstmt.setString(7, vo.getUser_id());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	// update, 요금 정보 cost 변경.

	// delete
	public int userDel(String userid) {

		String query = "delete from user_info where user_id=?";

		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
	public int userCount() {

		String query = "select count(*) from user_info";
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

	public int userCount(String s_query) {

		String query = "select count(*) from user_info where " + s_query;
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
	
	public List<UserVO> userList(int startpage, int endpage) {

		String query = "select X.* from (select rownum as rnum, A.* from (select * from user_info order by user_regdate desc) A where rownum <= ?) X where X.rnum >= ?";
		List<UserVO> list = new ArrayList();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserVO vo = new UserVO();
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_name(rs.getString("user_name"));
				vo.setUser_email(rs.getString("user_email"));
				vo.setUser_tel(rs.getString("user_tel"));
				vo.setUser_cost(rs.getString("user_cost"));
				vo.setUser_roll(rs.getString("user_roll"));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	public List<UserVO> userList(String s_query, int startpage, int endpage) {

		String query = "select X.* from (select rownum as rnum, A.* from "
				+ "(select * from user_info order by user_regdate desc) A " + "where " + s_query
				+ " and rownum <= ?) X where " + s_query + " and X.rnum >= ?";
		List<UserVO> list = new ArrayList();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserVO vo = new UserVO();
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_name(rs.getString("user_name"));
				vo.setUser_email(rs.getString("user_email"));
				vo.setUser_tel(rs.getString("user_tel"));
				vo.setUser_cost(rs.getString("user_cost"));
				vo.setUser_roll(rs.getString("user_roll"));

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
