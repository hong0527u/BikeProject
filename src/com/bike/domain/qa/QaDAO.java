package com.bike.domain.qa;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.util.DBManager;

public class QaDAO {
	private QaDAO() {}
	private static QaDAO instance = new QaDAO();
	public static QaDAO getInstance() {
		return instance;
	}
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int qaCnt() {
		String sql = "select count(*) from question";
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
	
	public int qaCnt(String s_sql) {
		String sql = "select count(*) from question where " + s_sql;
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
	
	public int qaWrite(QaVO vo) {
		String sql = "insert into question(idx, userid, subject, contents) values(question_idx_seq.nextval,?,?,?)";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getSubject());
			pstmt.setString(3, vo.getContents());
			
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public int qaReplyModify(QaVO vo) {
		String sql = "update question set q_answer=? where idx=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getQ_answer());
			pstmt.setInt(2, vo.getIdx());
			
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public List<QaVO> qaList() {
		String sql = "select * from question order by idx desc";
		List<QaVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QaVO vo = new QaVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setUserid(rs.getString("userid"));
				vo.setSubject(rs.getString("subject"));
				vo.setQ_answer(rs.getString("q_answer"));
				vo.setRegdate(rs.getString("regdate"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<QaVO> qaList(int startpage,int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from question order by idx desc) A where rownum<=?) X where X.rnum >=?";
		List<QaVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QaVO vo = new QaVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setUserid(rs.getString("userid"));
				vo.setSubject(rs.getString("subject"));
				vo.setQ_answer(rs.getString("q_answer"));
				vo.setRegdate(rs.getString("regdate"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
		
	}
	
	public List<QaVO> qaList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from question where "+ s_sql
				+ " order by idx desc) A where rownum<=?) X where X.rnum >=?";
		List<QaVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QaVO vo = new QaVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setUserid(rs.getString("userid"));
				vo.setSubject(rs.getString("subject"));
				vo.setQ_answer(rs.getString("q_answer"));
				vo.setRegdate(rs.getString("regdate"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public QaVO qaSelect(int idx) {
		String sql = "select * from question where idx=?";
		QaVO vo = new QaVO();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setUserid(rs.getString("userid"));
				vo.setSubject(rs.getString("subject"));
				vo.setContents(rs.getString("contents"));
				vo.setQ_answer(rs.getString("q_answer"));
				vo.setRegdate(rs.getString("regdate"));

				if(rs.getString("q_answer")!=null) {
					vo.setQ_answer(rs.getString("q_answer").replace("\n", "<br>"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	public QaVO qaSelect2(int idx) {
		String sql = "select * from question where idx=?";
		QaVO vo = new QaVO();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setUserid(rs.getString("userid"));
				vo.setSubject(rs.getString("subject"));
				vo.setContents(rs.getString("contents"));
				vo.setQ_answer(rs.getString("q_answer"));
				vo.setRegdate(rs.getString("regdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	public int qaDelete(int idx, String userid) {
		String sql = "delete from question where idx=? and userid=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, userid);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public int qaModify(QaVO vo) {
		String sql = "update question set subject=?, contents=? where idx=? and userid=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getSubject());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getIdx());
			pstmt.setString(4, vo.getUserid());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return row;
	}
	public void ReadCnt(int idx) {
		String sql = "update question set readcnt=readcnt+1 where idx=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
}
