package comment;

public class Comment {
	private int webID;
	private String userID;
	private String commentCont;
	
	public int getwebID() {
		return webID;
	}
	public String getUserID() {
		return userID;
	}
	public String getCommentCont() {
		return commentCont;
	}
	
	public void setwebID(int webID) {
		this.webID = webID;
	}
	public void setuserID(String userID) {
		this.userID = userID;
	}
	public void setCommentCont(String commentCont) {
		this.commentCont = commentCont;
	}
}
