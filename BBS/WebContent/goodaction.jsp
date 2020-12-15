<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "good.Good" %>
<%@ page import = "good.GoodDAO" %>
<%@ page import = "web.WebDAO" %>
<%@ page import = "java.io.PrintWriter" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
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
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		}
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않는 글입니다.')");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
		}
		else{
			GoodDAO goodDAO = new GoodDAO();
			WebDAO webDAO = new WebDAO();
			int searchresult = goodDAO.getGood(bbsID, userID);
			if(searchresult == 1){
				webDAO.downwebbest(bbsID);
				int result = goodDAO.gooddown(bbsID, userID);
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('추천 삭제에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}//겹치는 경우
				else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("location.href = 'bbs.jsp'");
					script.println("</script>");
				}//회원가입 완료
			}
			else{
				webDAO.upwebbest(bbsID);
				int result = goodDAO.goodup(bbsID, userID);
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('추천 등록에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}//겹치는 경우
				else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("location.href = 'bbs.jsp'");
					script.println("</script>");
				}//회원가입 완료
			}
		}
	%>
</body>
</html>