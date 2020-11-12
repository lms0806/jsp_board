<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO" %>
<%@ page import = "java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id = "user" class = "user.User" scope = "page"/>
<jsp:setProperty name = "user" property = "userID" />
<jsp:setProperty name = "user" property = "userPassword" />
<jsp:setProperty name = "user" property = "userName" />
<jsp:setProperty name = "user" property = "userGender" />
<jsp:setProperty name = "user" property = "userEmail" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>서원대학교 컴퓨터공학과 게시판</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID != null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다')");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
		if(user.getUserName() == null || user.getUserEmail() == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}//입력이 안된 경우
		else{
			UserDAO userDAO = new UserDAO();
			String result = userDAO.searchID(user);
			if(result == null){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('존재하지 않는 아이디입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}//겹치는 경우
			else {
				session.setAttribute("userID", user.getUserID());
				PrintWriter script = response.getWriter();
				result = "아이디는 " + result + "입니다.";
				script.println("<script>");
				script.println("alert('" + result + "')");
				script.println("history.back()");
				script.println("</script>");
			}//회원가입 완료
		}
	%>
</body>
</html>