package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;//보안을 위해 사용
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "";
			String dbID = "";
			String dbPassword = "";
			Class.forName("");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "select userPassword from user where userID = ?";//한번 꼬아서 사용하기 위해 "?" 사용
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				}
				else {
					return 0;//비밀번호 불일치
				}
			}
			return -1; //아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2;//데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "insert into user values(?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public String searchID(User user) {
		String SQL = "select userID from user where userName = ? and userEmail = ?";//한번 꼬아서 사용하기 위해 "?" 사용
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserName());
			pstmt.setString(2, user.getUserEmail());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(!rs.getString(1).equals(null)) {
					return rs.getString(1); //로그인 성공
				}
			}
			return null; //아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;//데이터베이스 오류
	}
	
	public String searchPassword(User user) {
		String SQL = "select userPassword from user where userName = ? and userID = ? and userEmail = ?";//한번 꼬아서 사용하기 위해 "?" 사용
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserName());
			pstmt.setString(2, user.getUserID());
			pstmt.setString(3, user.getUserEmail());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(!rs.getString(1).equals(null)) {
					return rs.getString(1); //로그인 성공
				}
			}
			return null; //아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;//데이터베이스 오류
	}
}
