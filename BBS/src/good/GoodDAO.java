package good;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import user.User;

public class GoodDAO {
	private Connection conn;
	private PreparedStatement pstmt;//보안을 위해 사용
	private ResultSet rs;
	
	public GoodDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/web?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "3510";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getGood(int webID, String userID) {
		String SQL = "select * from goodup where webID = ? and userID = ?";//한번 꼬아서 사용하기 위해 "?" 사용
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(!rs.getString(2).equals(null)) {
					return 1; //로그인 성공
				}
			}
			return -1; //아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public int goodcount(int webID) {
		String SQL = "select count(*) from goodup where webID = ?";//한번 꼬아서 사용하기 위해 "?" 사용
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int result = rs.getInt(1);
				return result; //아이디가 없음
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public int goodup(int webID, String userID) {
		String SQL = "insert into goodup values (?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public int gooddown(int webID, String userID) {
		String SQL = "delete from goodup where webID = ? and userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
}
