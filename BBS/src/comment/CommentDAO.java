package comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bbs.Web;

public class CommentDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public CommentDAO() {
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
	
	public Comment getComment(int webID) {
		String SQL = "select * from comment where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);//클릭한 게시글 읽기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Comment comment = new Comment();
				comment.setwebID(rs.getInt(1));
				comment.setuserID(rs.getString(2));
				comment.setCommentCont(rs.getString(3));
				return comment;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNext() {
		String SQL = "select webID from comment order by webID desc";
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
	
	public int write(String userID, String CommentContent) {
		String SQL = "insert into comment values (?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - 1);
			pstmt.setString(2, userID);
			pstmt.setString(3, CommentContent);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public ArrayList<Comment> getList(int pageNumber, int webID){
		String SQL = "select * from comment where webID < ? and webID = ? order by webID desc limit 10";
		ArrayList<Comment> list = new ArrayList<Comment>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//게시글 갯수
			pstmt.setInt(2, webID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setwebID(rs.getInt(1));
				comment.setuserID(rs.getString(2));
				comment.setCommentCont(rs.getString(3));
				list.add(comment);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}//게시글 불러오기
}
