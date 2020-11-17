<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
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
			<a class = "navbar-brand" herf="main.jsp">서원대 컴공</a>
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
			<form method = "post" action="writeAction.jsp">
				<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd"><%-- 테이블이 더욱 잘보이도록 해줌 --%>
					<thead>
						<tr>
							<th colspan = "2" style = "background-color : #eeeeee; text-align : center;">게시판 글쓰기 양식</th>
						</tr><%-- 1행 --%>
					</thead><%-- 속성을 알려줌 --%>
					<tbody>
						<tr>
							<td><input type = "text" class = "form-control" placeholder = "글 제목" name = "bbsTitle" maxlength = "50"></td>
						</tr>
						<tr>
							<td><textarea class = "form-control" placeholder = "글 내용" name = "bbsContent" maxlength = "2048" style = "height : 350px;"></textarea></td>
						</tr>
					</tbody>
				</table>
				<input type = "submit" class = "btn btn-primary pull-right" value = "글쓰기">
			</form>
			
			<form method="post" enctype="multipart/form-data" action="imgupload.jsp">
				<input type="file" name="filename1" size=40> 
				<input type="submit" value="업로드">
			</form>
		</div>
	</div>
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src = "js/bootstrap.js"></script>
</body>
</html>