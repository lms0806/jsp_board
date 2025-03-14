<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import = "comment.Comment" %>
<%@ page import = "comment.CommentDAO" %>
<%@ page import = "ccomment.Ccomment" %>
<%@ page import = "ccomment.CcommentDAO" %>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width initial-scale=1">
<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.css">
<link rel = "stylesheet" href = "css/custom.css">
<title>서원대학교 컴퓨터공학과 게시판</title>
<style type = "text/css">
	a, a:hover{
		color = #000000;
		text-decoration : none;<!-- 밑줄삭제 -->
	}
</style>
</head>
<body>
	<%
		//로긴한사람이라면	 userID라는 변수에 해당 아이디가 담기고 그렇지 않으면 null값
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		int webID = 0;
		if(request.getParameter("webID") != null){
			webID = Integer.parseInt(request.getParameter("webID"));
		}
		int commentID = 0;
		if(request.getParameter("commentID") != null){
			commentID = Integer.parseInt(request.getParameter("commentID"));
		}
		int pageNumber = 1;
		if(request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
	%>
	<!-- 네비게이션  -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="bs-example-navbar-collapse-1" aria-expaned="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">베스트글</a>
		</div>
		<div class="collapse navbar-collapse" id="#bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
				<li><a href="MyWrite.jsp">내가 쓴 글</a><li>
				<li><a href="bestlist.jsp">베스트글</a><li>
			</ul>
			<%
				//로그인안된경우
						if (userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href = "searchinfo.jsp">아이디/비밀번호 찾기</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			<%
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	
	<!-- 게시판 -->
	<div class = "container">
		<div class = "row">
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan = "3" style = "background-color : #eeeeee; text-align : center;">댓글</th>
					</tr><%-- 1행 --%>
				</thead><%-- 속성을 알려줌 --%>
				<%
					Comment comment = new CommentDAO().showComment(commentID);
					CcommentDAO ccommentDAO = new CcommentDAO();
					ArrayList<Ccomment> list = ccommentDAO.getList(pageNumber, commentID);
				%>
				<tr>
					<td><%= comment.getUserID() %></td>
					<td><%= comment.getCommentContent() %></td>
					<td></td>
				</tr>
				<%
					if(list.size() == 0){
				%>
				<tr>
					<td>작성된 답글이 없습니다.</td>
					<td></td>
					<td></td>
				</tr>
				<%
					} else{
						for(int i = 0; i < list.size(); i++){
				%>
				<tr>
					<td><%= list.get(i).getUserid() %></td>
					<td><%= list.get(i).getCcommentcontent()%></td>
				<%
							String comments = list.get(i).getCcommentcontent();
				%>
					<td><a onclick="return confirm('정말로 삭제하시겠습니까?')" href="deleteCcommentAction.jsp?commentID=<%= commentID %>&ccommentID=<%= list.get(i).getCcommentid() %>&ccomment=<%= list.get(i).getCcommentcontent() %>">삭제</a></td>
				<tr>
				<%
						}
					}
				%>
			</table>
		</div>
	</div>
	<div class = "container">
		<div class = "row">	
			<form method = "post" action="WriteCcommentAction.jsp">
				<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan = "2" style = "background-color : #eeeeee; text-align : center;">답글 작성</th>
						</tr><%-- 1행 --%>
					</thead><%-- 속성을 알려줌 --%>
					<tbody>
						<tr>
							<td style = "width: 95%;">
								<input type = "hidden" class = "form-control" name = "webid" value=<%= webID %>>
								<input type = "hidden" class = "form-control" name = "commentID" value=<%= commentID %>>
								<input type = "text" class = "form-control" placeholder = "답글" name = "ccommentcontent" maxlength = "50">
							</td>
							<td>
								<input type = "submit" class = "btn btn-primary pull-right" value = "작성">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>
</body>
</html>