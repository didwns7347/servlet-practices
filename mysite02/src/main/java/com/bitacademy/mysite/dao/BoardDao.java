package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.mysite.vo.UserVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mysql://localhost :3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("error" + e);
		}

		return conn;
	}
	public long findByParent(long target) {
		long res=-1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select g_no from board where no=?;";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			if (rs.next()) {
				res = rs.getLong(1);
			}

			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}
	public long getAuto() {
		
		long res=-1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'board' AND table_schema = DATABASE( )";
			String sql2="SELECT max(no) FROM board";
			pstmt = conn.prepareStatement(sql2);

			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			if (rs.next()) {
				res=rs.getLong(1);
			}

			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}
		
	
	public boolean newinsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			
			String sql = " insert" + "   into board" + " values (null, ?, ?, ?, ?, ?, now())";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getG_no());
			pstmt.setInt(5, vo.getDepth());

			// 5. SQL문 실행
			int count = pstmt.executeUpdate();

			// 6. 결과
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	public boolean reinsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
		
			String sql = " insert" + "   into board" + " values (null, ?, ?, ?, ?, ?, now())";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getG_no());
			pstmt.setInt(5, vo.getDepth());

			// 5. SQL문 실행
			int count = pstmt.executeUpdate();

			// 6. 결과
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	public List<BoardVo> findAll() {
		List<BoardVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select no, title, writer, contents,g_no,depth,reg_date from board order by g_no desc ,depth asc , no desc;";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String writer = rs.getString(3);
				String contents = rs.getString(4);
				String gno=rs.getString(5);
				String depth = rs.getString(6);
				String date = rs.getString(7);

				BoardVo vo = new BoardVo();
				vo.setTitle(title);
				vo.setWriter(writer);
				vo.setNo(no);
				vo.setContents(contents);
				vo.setDate(date);
				vo.setDepth(Integer.parseInt(depth));
				vo.setG_no(Long.parseLong(gno));

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	public BoardVo findByNo(int no) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select title,contents,g_no,depth from board where no=? ";
		
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setLong(1, no);
			// 5. SQL문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			if (rs.next()) {
				vo.setTitle(rs.getString(1));
				vo.setContents(rs.getString(2));
				vo.setG_no(rs.getLong(3));
				vo.setDepth(rs.getInt(4));
			}

			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	
		return vo;
	}

}
