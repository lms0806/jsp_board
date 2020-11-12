<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "bbs.Web" %>
<%@ page import = "bbs.WebDAO" %>
<%@ page import = "comment.Comment" %>
<%@ page import = "comment.CommentDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name = "viewport" content = "width=device-width", initial-scale = "1">
<link rel = "stylesheet" href = "css/bootstrap.min.css">
<link rel = "stylesheet" href = "css/custom.css">
<title>서원대학교 컴퓨터공학과 게시판</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}//로그인한사람은 userID에 값 추가
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		int webVIEWS = 0;
		if(request.getParameter("webViews") != null){
			webVIEWS = Integer.parseInt(request.getParameter("webViews"));
		}
		if(bbsID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않는 글입니다.')");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
		}
		Web bbs = new WebDAO().getBbs(bbsID);//유효한 정보라면 정보 담기
		Comment comment = new CommentDAO().getComment(bbsID);
		
		WebDAO bbsDAO = new WebDAO();
		bbsDAO.updateview(bbsID);
	%>
	<nav class = "navbar navbar-default">
		<div class = "navbar-header">
			<button type = "button" class = "navbar-toggle collapsed"
				data-toggle = "collapse" data-target = "#bs-example-navbar-collapse-1"
				aria-expended = "false">
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
			</button>
			<a class = "navbar-brand" href="main.jsp">서원대 컴공</a>
		</div>
		<div class = "collapse navbar-collapse" id = "bs-example-navbar-collapse-1">
			<ul class = "nav navbar-nav">
				<li><a href = "main.jsp">메인</a><li>
				<li class = "active"><a href = "bbs.jsp">게시판</a><li>
				<li><a href="MyWrite.jsp">내가 쓴 글</a><li>
			</ul>
			<%
				if(userID == null){
					
			%>
			<ul class = "nav navbar-nav navbar-right">
				<li class = "dropdown">
					<a href = "#" class = "dropdown-toggle"
						data-toggle = "dropdown" role = "button" aria-haspopup = "true"
						aria-expended = "false">접속하기 <span class = "caret"></span></a>
					<ul class = "dropdown-menu">
						<li><a href = "login.jsp">로그인</a></li>
						<li><a href = "searchinfo.jsp">아이디/비밀번호 찾기</a></li>
						<li><a href = "join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			<%
				} else{
			%>
			<ul class = "nav navbar-nav navbar-right">
				<li class = "dropdown">
					<a href = "#" class = "dropdown-toggle"
						data-toggle = "dropdown" role = "button" aria-haspopup = "true"
						aria-expended = "false">회원관리 <span class = "caret"></span></a>
					<ul class = "dropdown-menu">
						<li><a href = "logout.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<%
				}//로그아웃
			%>
		</div>
	</nav>
	<div class = "container">
		<div class = "row">
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd"><%-- 테이블이 더욱 잘보이도록 해줌 --%>
				<thead>
					<tr>
						<th colspan = "3" style = "background-color : #eeeeee; text-align : center;">게시판 글보기</th>
					</tr><%-- 1행 --%>
				</thead><%-- 속성을 알려줌 --%>
				<tbody>
					<tr>
						<td style = "width: 20%;">글 제목</td>
						<td colspan = "2"><%= bbs.getBbsTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan = "2"><%= bbs.getUserID() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan = "2"><%= bbs.getBbsDate().substring(0, 11) + bbs.getBbsDate().substring(11, 13) + "시" + bbs.getBbsDate().substring(14, 16) + "분" %></td>
					</tr>
					<tr>
						<td>추천수</td>
						<td colspan = "2"><%=bbs.getWebBest() %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan = "2" style = "min-height: 200px; text-align: left;"><%= bbs.getBbsContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
				</tbody>
			</table>
			<a href = "bbs.jsp" class = "btn btn-primary">목록</a>
			<a href = "bestup.jsp" class = "btn btn-primary">★추천★</a>
			<%
				if(userID != null && userID.equals(bbs.getUserID())){
			%>
					<a href="update.jsp?bbsID=<%= bbsID %>" class="btn btn-primary">수정</a>
					<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="deleteAction.jsp?bbsID=<%= bbsID %>" class = "btn btn-primary">삭제</a>
			<%
				}//작성자와 읽는자가 동일하면 수정가능 기능
			%>
			<br>
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan = "3" style = "background-color : #eeeeee; text-align : center;">댓글 작성</th>
					</tr><%-- 1행 --%>
				</thead><%-- 속성을 알려줌 --%>
				<tbody>
					<tr>
						<td style = "width: 95%;">
							<input type = "text" class = "form-control" placeholder = "댓글" name = "bbsTitle" maxlength = "50">
						</td>
						<td>
							<a href = "writecomment.jsp" class = "btn btn-primary">작성</a>
						</td>
					</tr>
				</tbody>
			</table>
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan = "3" style = "background-color : #eeeeee; text-align : center;">댓글</th>
					</tr><%-- 1행 --%>
				</thead><%-- 속성을 알려줌 --%>
				<tbody>
					<tr>
						<td style = "width: 20%;"><%= comment.getUserID() %></td>
						<td colspan = "2" style = "min-height: 200px; text-align: left;"><%= comment.getCommentCont().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src = "js/bootstrap.js"></script>
</body>
</html>