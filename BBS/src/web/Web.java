package web;

public class Web {
	private int webID;
	private String webTitle;
	private String userID;
	private String webDate;
	private String webContent;
	private int webviews;
	private int webbest;
	private int webAvailable;
	
	public int getBbsID() {
		return webID;
	}
	public void setBbsID(int webID) {
		this.webID = webID;
	}
	public String getBbsTitle() {
		return webTitle;
	}
	public void setBbsTitle(String webTitle) {
		this.webTitle = webTitle;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBbsDate() {
		return webDate;
	}
	public void setBbsDate(String webDate) {
		this.webDate = webDate;
	}
	public String getBbsContent() {
		return webContent;
	}
	public void setBbsContent(String webContent) {
		this.webContent = webContent;
	}
	public int getWebViews() {
		return webviews;
	}
	public void setWebViews(int webviews) {
		this.webviews = webviews;
	}
	public int getWebBest() {
		return webbest;
	}
	public void setWebBest(int webBest) {
		this.webbest = webBest;
	}
	public int getBbsAvailable() {
		return webAvailable;
	}
	public void setBbsAvailable(int webAvailable) {
		this.webAvailable = webAvailable;
	}
	
}
