package comment;

public class Comment {
	private int commentID;
	private int webID;
	private String userID;
	private String commentContent;
	
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getWebID() {
		return webID;
	}
	public void setWebID(int webID) {
		this.webID = webID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
}
