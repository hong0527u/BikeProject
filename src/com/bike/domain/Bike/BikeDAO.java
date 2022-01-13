package com.bike.domain.Bike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bike.util.DBManager;
import com.bike.util.UserSHA256;

public class BikeDAO {
	private BikeDAO() {}
	private static BikeDAO instance = new BikeDAO();
	public static BikeDAO getInstance() {
		return instance;
	}
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int bikeInsert(BikeVO vo, int each) {
		int row = 0;
		String sql = "insert into bike_info(bike_code,bike_type,park_code) values ('bike'||bike_CODE_SEQ.nextval,?,?)";
		String selectsql = "select * from (select bike_code,rownum as rnum from bike_info order by bike_code desc) where rnum = 1";
		try {
			conn = DBManager.getConnection();
			for(int x=0; x<each; x++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getBike_type());
				pstmt.setString(2, vo.getPark_code());
				
				row = pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement(selectsql);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					vo.setBike_code(rs.getString("bike_code"));
					vo.setBuy_bikecode(vo.getBuy_bikecode()+"/"+vo.getBike_code());
				}
			}
			if(row==0) {
				row=0;
			}else {
				sql="insert into buy_info(buy_code,cor_code,buy_pay,buy_bikecode) values ('buy'||buy_CODE_SEQ.nextval,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getCor_code());
				pstmt.setInt(2, each*100000);
				pstmt.setString(3, vo.getBuy_bikecode().substring(5));
				
				row = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
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

	public BikeVO corSelect() {
		BikeVO vo = new BikeVO();
		String sql = "select * from cor_info";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setCor_code(rs.getString("cor_code"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	public int buyBike(String code, String bikecode, String parkcode) {
		int row = 0;
		String sql = "update buy_info set buy_state=100 where buy_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);
			
			row = pstmt.executeUpdate();
			if(row==1) {
				sql = "update bike_info set bike_state='parking', bike_regdate=sysdate where bike_code=?";
				String bike[] = bikecode.split("/");
				for(int x=0; x<bike.length; x++) {
					conn = DBManager.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, bike[x]);
					
					row = pstmt.executeUpdate();
					parkcode = bikeParkingCheck(bike[x]);
					
					parkBikePlus(parkcode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
	public int bikeParking(String bikecode, String parkcode, String rentalcode,int pay) {
		int row = 0;
		String sql = "update bike_info set park_code=?, bike_state='parking' where bike_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parkcode);
			pstmt.setString(2, bikecode);
			
			row = pstmt.executeUpdate();
			if(row==1) {
				sql = "update rental_info set rental_lasttime=sysdate, parking_code=?, rental_pay=? where rental_code=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, parkcode);
				pstmt.setInt(2, pay);
				pstmt.setString(3, rentalcode);
				
				row = pstmt.executeUpdate();
				
				parkBikePlus(parkcode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
	public String rentBike(String userid, String bikecode, BikeVO vo) {
		int row = 0;
		String sql = "update bike_info set bike_state='rent' where bike_code=?";
		String code = "";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bikecode);
			
			row = pstmt.executeUpdate();
			if(row==1) {
				sql = "update user_info set user_cost=user_cost-1500 where user_id=?";
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				
				row = pstmt.executeUpdate();
				if(row==1) {
					sql = "insert into rental_info(rental_code,user_id,bike_code,park_code) values ('rental'||rental_CODE_SEQ.nextval,?,?,?)";
					conn = DBManager.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userid);
					pstmt.setString(2, bikecode);
					pstmt.setString(3, vo.getPark_code());
					
					row= pstmt.executeUpdate();
					parkBikeMinus(vo.getPark_code());
					if(row==1) {
						sql = "select rental_code_seq.nextVAL FROM DUAL";
						conn = DBManager.getConnection();
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							int seq = rs.getInt("nextval")-1;
							code = "rental"+seq;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return code;
	}
	
	public void parkBikePlus(String parkcode) {
		int row = 0;
		String sql = "update parking_info set park_bike=park_bike+1 where park_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parkcode);
			
			row=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
	}
	
	public void parkBikeMinus(String parkcode) {
		int row = 0;
		String sql = "update parking_info set park_bike=park_bike-1 where park_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parkcode);
			
			row=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
	}
	
	public int bikeDelete(String code, String parkcode) {
		int row = 0;
		String sql = "delete from bike_info where bike_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);
			
			row = pstmt.executeUpdate();
			
			parkBikeMinus(parkcode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	
	public List<BikeVO> corList() {
		String sql = "select * from cor_info order by cor_code desc";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBuy_code(rs.getString("buy_code"));
				vo.setBuy_date(rs.getString("buy_date"));
				vo.setBuy_pay(rs.getInt("buy_pay"));
				vo.setBuy_bikecode(rs.getString("buy_bikecode"));
				vo.setBuy_state(rs.getString("buy_state"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> buyList(int startpage,int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from buy_info order by buy_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBuy_code(rs.getString("buy_code"));
				vo.setBuy_date(rs.getString("buy_date"));
				vo.setBuy_pay(rs.getInt("buy_pay"));
				vo.setBuy_bikecode(rs.getString("buy_bikecode"));
				if(vo.getBuy_bikecode().length()>35) {
					vo.setBuy_bikecode(vo.getBuy_bikecode().substring(0,35)+"...");
				}
				vo.setBike_code(rs.getString("buy_bikecode"));
				vo.setBuy_state(rs.getString("buy_state"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> buyList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from buy_info where "+ s_sql
				+ " order by buy_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBuy_code(rs.getString("buy_code"));
				vo.setBuy_date(rs.getString("buy_date"));
				vo.setBuy_pay(rs.getInt("buy_pay"));
				vo.setBuy_bikecode(rs.getString("buy_bikecode"));
				if(vo.getBuy_bikecode().length()>35) {
					vo.setBuy_bikecode(vo.getBuy_bikecode().substring(0,35)+"...");
				}
				vo.setBike_code(rs.getString("buy_bikecode"));
				vo.setBuy_state(rs.getString("buy_state"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> bikeList(int startpage,int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select bike_code,bike_type,park_name,bike_state,bike_fix,bike_regdate from bike_info b, parking_info p where b.park_code=p.park_code order by bike_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_type(rs.getString("bike_type"));
				vo.setPark_code(rs.getString("park_name"));
				vo.setBike_state(rs.getString("bike_state"));
				vo.setBike_fix(rs.getString("bike_fix"));
				vo.setBike_regdate(rs.getString("bike_regdate"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> bikeList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select bike_code,bike_type,park_name,bike_state,bike_fix,bike_regdate from bike_info b, parking_info p where b.park_code=p.park_code and "+ s_sql
				+ " order by bike_code desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_type(rs.getString("bike_type"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setBike_state(rs.getString("bike_state"));
				vo.setBike_fix(rs.getString("bike_fix"));
				vo.setBike_regdate(rs.getString("bike_regdate"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public int buyCnt() {
		String sql = "select count(*) from buy_info";
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
	
	public int buyCnt(String s_sql) {
		String sql = "select count(*) from buy_info where " + s_sql;
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
	
	public int bikeCnt() {
		String sql = "select count(*) from bike_info";
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
	
	public int bikeCnt(String s_sql) {
		String sql = "select count(*) from bike_info where " + s_sql;
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
	
	public int rentalCnt(String userid) {
		String sql = "select count(*) from rental_info where user_id=?";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
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
	
	public int rentalCnt(String s_sql, String userid) {
		String sql = "select count(*) from rental_info where userid=? and " + s_sql;
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt.setString(1, userid);
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
	
	public int RentalCnt() {
		String sql = "select count(*) from rental_info";
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
	
	public int RentalCnt(String s_sql) {
		String sql = "select count(*) from rental_info where " + s_sql;
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

	public int bikeCodeCheck(String bikecode) {
		String sql = "select count(*) from bike_info where bike_code=? and bike_state='parking'";
		int row = 0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bikecode);
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
	
	public String bikeParkingCheck(String lat, String lng) {
		String sql = "select park_code from parking_info where park_locx like ? and park_locy like ?";
		String park_name = "";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lat.substring(0,6)+"%");
			pstmt.setString(2, lng.substring(0,7)+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				park_name = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return park_name;
	}
	
	public String bikeParkingCheck(String bike_code) {
		String sql = "select park_code from bike_info where bike_code=?";
		String park_code = "";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				park_code = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return park_code;
	}
	
	public BikeVO SelectRentalInfo(String rental_code) {
		String sql = "select * from rental_info where rental_code=?";
		BikeVO vo = new BikeVO();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rental_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setRental_code(rental_code);
				vo.setUser_id(rs.getString("user_id"));
				vo.setRental_type(rs.getString("rental_type"));
				vo.setRental_pay(rs.getString("rental_pay"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setRental_firsttime(rs.getString("rental_firsttime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	public List<BikeVO> SelectRentalList(String userid, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from rental_info where user_id=? order by rental_firsttime desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, endpage);
			pstmt.setInt(3, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setRental_code(rs.getString("rental_code"));
				vo.setRental_type(rs.getString("rental_type"));
				vo.setRental_pay(rs.getString("rental_pay"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setParking_code(rs.getString("parking_code"));
				vo.setRental_firsttime(rs.getString("rental_firsttime"));
				vo.setRental_lasttime(rs.getString("rental_lasttime"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> SelectRentalList(String userid, String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from rental_info where user_id=? and"+ s_sql
				+ " order by rental_firsttime desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, endpage);
			pstmt.setInt(3, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setRental_code(rs.getString("rental_code"));
				vo.setRental_type(rs.getString("rental_type"));
				vo.setRental_pay(rs.getString("rental_pay"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setParking_code(rs.getString("parking_code"));
				vo.setRental_firsttime(rs.getString("rental_firsttime"));
				vo.setRental_lasttime(rs.getString("rental_lasttime"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> RentalList(int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from rental_info order by rental_firsttime desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setRental_code(rs.getString("rental_code"));
				vo.setRental_type(rs.getString("rental_type"));
				vo.setRental_pay(rs.getString("rental_pay"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setParking_code(rs.getString("parking_code"));
				vo.setRental_firsttime(rs.getString("rental_firsttime"));
				vo.setRental_lasttime(rs.getString("rental_lasttime"));
				vo.setUser_id(rs.getString("user_id"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BikeVO> RentalList(String s_sql, int startpage, int endpage) {
		String sql = "select X.* from (select rownum as rnum, A.* from(select * from rental_info where "+ s_sql
				+ " order by rental_firsttime desc) A where rownum<=?) X where X.rnum >=?";
		List<BikeVO> list = new ArrayList();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, startpage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BikeVO vo = new BikeVO();
				vo.setRental_code(rs.getString("rental_code"));
				vo.setRental_type(rs.getString("rental_type"));
				vo.setRental_pay(rs.getString("rental_pay"));
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setParking_code(rs.getString("parking_code"));
				vo.setRental_firsttime(rs.getString("rental_firsttime"));
				vo.setRental_lasttime(rs.getString("rental_lasttime"));
				vo.setUser_id(rs.getString("user_id"));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public BikeVO bikeView(String bikecode) {
		String sql = "select * from cor_info c, buy_info b where c.cor_code = b.cor_code and b.buy_bikecode like ?";
		BikeVO vo = new BikeVO();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+bikecode+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setCor_code(rs.getString("cor_code"));
				vo.setCor_name(rs.getString("cor_name"));
				vo.setBuy_code(rs.getString("buy_code"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
				vo.setBuy_date(rs.getString("buy_date"));
			}
			
			sql = "select * from bike_info b, parking_info p where b.park_code = p.park_code and b.bike_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bikecode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setBike_code(rs.getString("bike_code"));
				vo.setPark_code(rs.getString("park_code"));
				vo.setBike_state(rs.getString("bike_state"));
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
	
	public BikeVO bikeView2(String bikecode) {
		String sql = "select * from bike_info b, parking_info p where b.park_code = p.park_code and b.bike_code=?";
		BikeVO vo = new BikeVO();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bikecode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_state(rs.getString("bike_state"));
				vo.setCor_tel(rs.getString("cor_tel"));
				vo.setCor_adr(rs.getString("cor_adr"));
				vo.setBuy_date(rs.getString("buy_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}

	public int bikeModify(BikeVO vo) {
		int row = 0;
		String sql = "update bike_info set park_code=?, bike_state=? where bike_code=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPark_code());
			pstmt.setString(2, vo.getBike_state());
			pstmt.setString(3, vo.getBike_code());
			
			row = pstmt.executeUpdate();
			
			parkBikeMinus(vo.getCor_code());
			parkBikePlus(vo.getPark_code());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	public BikeVO bikeSelect(String bike_code) {
		BikeVO vo = new BikeVO();
		String sql = "select * from bike_info b, parking_info p where b.park_code=p.park_code and bike_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bike_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setBike_code(rs.getString("bike_code"));
				vo.setBike_type(rs.getString("bike_type"));
				vo.setBike_state(rs.getString("bike_state"));
				vo.setPark_name(rs.getString("park_name"));
				vo.setPark_code(rs.getString("park_code"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return vo;
	}

	public double rentalTimeCheck(String rentalcode) {
		double time = 0;
		String sql = "SELECT round((sysdate-rental_firsttime)*2400)/100 as time FROM rental_info where rental_code=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rentalcode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				time = rs.getDouble("time");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return time;
	}

	public void PlusCredit(String userid, String rentalcode, int pay) {
		String sql = "update user_info set user_cost=user_cost-? where user_id=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pay);
			pstmt.setString(2, userid);
			
			int row = pstmt.executeUpdate();
			
			sql = "update rental_info set rental_pay=rental_pay+? where rental_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pay);
			pstmt.setString(1, rentalcode);
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
	}
	
}
