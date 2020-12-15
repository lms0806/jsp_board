package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class WebDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public WebDAO() {
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
	
	public String getDate() {
		String SQL = "select now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";//데이터베이스 오류
	}//현재시간 가져오기
	
	public int getNext() {
		String SQL = "select webID from web order by webID desc";
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
	
	public int write(String webTitle, String userID, String webContent) {
		String SQL = "insert into web values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, webTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, webContent);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 1);//글이 작성되엇으므로 1
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public ArrayList<Web> getList(int pageNumber){
		String SQL = "select * from web where webID < ? and webAvailable = 1 order by webID desc limit 10";
		ArrayList<Web> list = new ArrayList<Web>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//게시글 갯수
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Web bbs = new Web();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setWebViews(rs.getInt(6));
				bbs.setWebBest(rs.getInt(7));
				bbs.setBbsAvailable(rs.getInt(8));
				list.add(bbs);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}//게시글 불러오기
	
	public boolean nextPage(int pageNumber) {
		String SQL = "select * from web where webID < ? and webAvailable = 1";// order by bbsID desc limit 10";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//게시글 갯수
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}//다음 게시글이 없으면 안넘어감
	
	public Web getBbs(int webID) {
		String SQL = "select * from web where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);//클릭한 게시글 읽기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Web bbs = new Web();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setWebViews(rs.getInt(6));
				bbs.setWebBest(rs.getInt(7));
				bbs.setBbsAvailable(rs.getInt(8));
				return bbs;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int webID, String webTitle, String webContent) {
		String SQL = "update web set webTitle = ?, webContent = ? where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, webTitle);
			pstmt.setString(2, webContent);
			pstmt.setInt(3, webID);//글이 작성되엇으므로 1
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public int updateview(int webID) {
		String SQL = "update web set webViews = webViews + 1 where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
	
	public int delete(int webID) {
		String SQL = "update web set webAvailable = 0 where webID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, webID);
			return pstmt.executeUpdate(); //첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
}
