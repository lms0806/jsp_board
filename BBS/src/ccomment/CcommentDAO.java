package ccomment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import comment.Comment;

public class CcommentDAO {

	private Connection conn;
	private ResultSet rs;
	
	public CcommentDAO() {
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
	
	public int write(int webID, int commentID, String userID, String CcommentContent) {
		String SQL = "insert into ccomment values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setInt(2, webID);
			pstmt.setInt(3, commentID);
			pstmt.setString(4, userID);
			pstmt.setString(5, CcommentContent);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public Ccomment getComment(int webID) {
		String SQL = "select * from Ccomment where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);//클릭한 게시글 읽기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Ccomment ccomment = new Ccomment();
				ccomment.setCcommentid(rs.getInt(1));
				ccomment.setWebid(rs.getInt(2));
				ccomment.setUserid(rs.getString(3));
				ccomment.setCcommentcontent(rs.getString(4));
				return ccomment;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNext() {
		String SQL = "select ccommentID from ccomment order by ccommentID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}//게시판 순서 번호
	
	public ArrayList<Ccomment> getList(int pageNumber, int commentID){
		String SQL = "select * from ccomment where ccommentID < ? and commentID = ? order by webID desc limit 10";
		ArrayList<Ccomment> list = new ArrayList<Ccomment>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//게시글 갯수
			pstmt.setInt(2, commentID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Ccomment ccomment = new Ccomment();
				ccomment.setCcommentid(rs.getInt(1));
				ccomment.setWebid(rs.getInt(2));
				ccomment.setCommentid(rs.getInt(3));
				ccomment.setUserid(rs.getString(4));
				ccomment.setCcommentcontent(rs.getString(5));
				list.add(ccomment);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}//게시글 불러오기
	
	public int delete(int ccommentID, String userID, String ccomment) {
		String SQL = "delete from ccomment where ccommentID = ? and userID = ? and ccommentcontent = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ccommentID);
			pstmt.setString(2, userID);
			pstmt.setString(3, ccomment);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
}
