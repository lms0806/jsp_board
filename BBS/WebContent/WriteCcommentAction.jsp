<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ccomment.CcommentDAO" %>
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="ccomment" class="ccomment.Ccomment" scope="page" />
<jsp:setProperty name="ccomment" property="ccommentcontent" />
<jsp:setProperty name="ccomment" property="webid" />
<jsp:useBean id="comment" class="comment.Comment" scope="page" />
<jsp:setProperty name="comment" property="commentID" />
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
		else{
			if(ccomment.getCcommentcontent() == null){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}//입력이 안된 경우
			else{
				CcommentDAO ccommentDAO = new CcommentDAO();
				int result = ccommentDAO.write(ccomment.getWebid(), comment.getCommentID(), userID, ccomment.getCcommentcontent());
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('댓글작성에 실패했습니다.')");
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