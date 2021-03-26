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
			
			String sql = " insert" + "   into board" + " values (null, ?, ?, ?, ?, ?, now(),?,?)";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getG_no());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getGorder());
			pstmt.setLong(7, vo.getParent());

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
	public boolean before(long gno,long gorder) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String fsql="update board set g_order=g_order+1 where g_no=? AND g_order>=?";
			pstmt=conn.prepareStatement(fsql);
			pstmt.setLong(1, gno);
			pstmt.setLong(2, gorder);
			int cnt=pstmt.executeUpdate();
			// 6. 결과
			result = cnt >=0;
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
		
			this.before(vo.getG_no(), vo.getGorder());
			String sql = " insert" + "   into board" + " values (null, ?, ?, ?, ?, ?, now(),?,?)";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getG_no());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getGorder());
			pstmt.setLong(7, vo.getParent());
			
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
			String sql ="select title,contents,g_no,depth,g_order from board where no=? ";
		
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
				vo.setNo(no);
				vo.setGorder(rs.getLong(5));
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
	public boolean deleteV1(long no) {
		int count=0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = " delete" + "   from board" + " where no=?";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setLong(1, no);
	

			// 5. SQL문 실행
			count = pstmt.executeUpdate();

			// 6. 결과
			return count == 1;
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

		return false;
		
	}
	public boolean deleteV2(long no) {
		boolean result = false;
		Connection conn = null;
		try {
			conn = this.getConnection();
			PreparedStatement pstmt = null;
			String sql = "update board set  title=?, contents=? where no=?; ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new BoardVo().getDel());
			pstmt.setString(2, "");
			pstmt.setLong(3, no);
			
			
			int cnt = pstmt.executeUpdate();

			return cnt == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error: " + e);
		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
		
	}
	public List<BoardVo> findPage(int page) {
		List<BoardVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			long start=(long)10*page;
			// 3. SQL 준비
			System.out.println(page);
			String sql ="select no, title, writer, contents,g_no,depth,reg_date from board "
					+ "order by g_no desc,"
					+ "g_order asc ,"
					+ "depth asc,"
					+ "no asc "
					+" limit  "+start+",10";
			//System.out.println(sql);
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
	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select count(no) from board";

			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			
			if(rs.next()) {
				return rs.getInt(1);
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

		return 0;
	}
	public long getGorderRe(long parent) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select max(g_order) from board where  parent=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, parent);
			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			
			if(rs.next()) {
				return rs.getLong(1);
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

		return 0l;
	}
	public long getGorderByP(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select max(g_order) from board where parent=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			
			if(rs.next()) {
				return rs.getLong(1);
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

		return 0l;
	}
	
	public long getGorder(long gno) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql ="select max(g_order) from board where g_no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, gno);
			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			
			if(rs.next()) {
				return rs.getLong(1);
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

		return 0l;
	}


}
