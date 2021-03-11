package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bitacademy.mysite.vo.UserVo;

public class UserDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mysql://localhost :3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "1234");
		} catch (ClassNotFoundException e) {
			System.out.println("error" + e);
		}

		return conn;
	}
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		try {
			conn=this.getConnection();
			PreparedStatement pstmt = null;
			String sql="insert into user values (null,?,?,?,?,now())";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			int cnt = pstmt.executeUpdate();
			
			return cnt==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error: "+e);
		}finally {
			try {
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
