package comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import web.Web;

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
				comment.setCommentID(rs.getInt(1));
				comment.setWebID(rs.getInt(2));
				comment.setUserID(rs.getString(3));
				comment.setCommentContent(rs.getString(4));
				return comment;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNext() {
		String SQL = "select commentID from comment order by commentID desc";
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
	
	public int write(int webID, String userID, String CommentContent) {
		String SQL = "insert into comment values (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setInt(2, webID);
			pstmt.setString(3, userID);
			pstmt.setString(4, CommentContent);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public ArrayList<Comment> getList(int pageNumber, int webID){
		String SQL = "select * from comment where commentID < ? and webID = ? order by webID desc limit 10";
		ArrayList<Comment> list = new ArrayList<Comment>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//게시글 갯수
			pstmt.setInt(2, webID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentID(rs.getInt(1));
				comment.setWebID(rs.getInt(2));
				comment.setUserID(rs.getString(3));
				comment.setCommentContent(rs.getString(4));
				list.add(comment);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}//게시글 불러오기
	
	public int delete(int webID, String userID, String comment) {
		String SQL = "delete from comment where webID = ? and userID = ? and commentcontent = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			pstmt.setString(2, userID);
			pstmt.setString(3, comment);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public Comment showComment(int CommentID) {
		String SQL = "select * from comment where CommentID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, CommentID);//클릭한 게시글 읽기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentID(rs.getInt(1));
				comment.setWebID(rs.getInt(2));
				comment.setUserID(rs.getString(3));
				comment.setCommentContent(rs.getString(4));
				return comment;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
